package org.example.y1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.y1.model.User;
import org.example.y1.repository.UserDictRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserDictRepository userDictRepository;

    public ResponseEntity<Map<String, Object>> getSession(HttpServletRequest request)  {
        String session=request.getHeader("sessionID");
        Map<String,Object> response = new HashMap<>();

        if(!getSessionStatus(session)){
            response.put("success", false);
            response.put("message","未找到会话，请登录");
            response.put("_is",false);
            return ResponseEntity.ok(response);
        }

        String id = session;

        String userJson = (String) redisTemplate.opsForValue().get("session:" + id);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        try {
            user = objectMapper.readValue(userJson, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Long userid=user.getId();

        User user1=userDictRepository.findByID(userid);

        objectMapper = new ObjectMapper();

        String userJson1 = null;
        try {
            userJson1 = objectMapper.writeValueAsString(user1);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        redisTemplate.opsForValue().set("session:" + session, userJson1, 24, TimeUnit.HOURS);

        restoreSession(session, request);
        response.put("success",true);
        response.put("session",session);
        response.put("_is",true);
        response.put("user",user1);

        return  ResponseEntity.ok(response);
    }

    public String createSession(User user, HttpServletRequest request) throws JsonProcessingException {
        String session =request.getHeader("sessionID");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);


        redisTemplate.opsForValue().set("session:" + session, userJson, 24, TimeUnit.HOURS);

        getCurrentSession().setAttribute("user", user);
        return session;
    }

    public boolean getSessionStatus(String sessionId) {
        return redisTemplate.hasKey("session:" + sessionId);
    }

    public void invalidateCurrentSession(HttpServletRequest request) {
        try {

            String sessionId=getSessionIdFromCookies(request);

            redisTemplate.delete("session:" + sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSessionIdFromCookies(HttpServletRequest request) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        String sessionId = "";
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if ("sessionID".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;
    }
    public String getUsername(String sessionId) {
        return (String) redisTemplate.opsForValue().get("session:" + sessionId);
    }

    public HttpSession getCurrentSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    public void restoreSession(String sessionId, HttpServletRequest request) {
        String email = getUsername(sessionId);
        if (email != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", email);
        }
    }

    public Map<String,Object> get_Session(HttpServletRequest request) {
        String sessionid = getSessionIdFromCookies(request);
        Map<String, Object> response = new HashMap<>();
        if (!getSessionStatus(sessionid)) {
            response.put("success", false);
            response.put("message", "未找到会话，请登录");
            response.put("_is", false);
            return response;
        }
        response.put("success", true);
        response.put("_is", true);
        response.put("message", "已登录");

        return response;
    }

    public User getCurrentUser(String sessionId) {
        String userJson = (String) redisTemplate.opsForValue().get("session:" + sessionId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(userJson, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
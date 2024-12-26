// const SERVER_URL = `${window.location.protocol}//${window.location.hostname}:8080/api`;
const SERVER_URL = `https://1.shiguang.online`;
async function checkSession() {
    setSessionCookie();
    const response = await fetch(`${SERVER_URL}/get_session`, {
        method: 'GET',
        credentials: 'include',
        headers: {
            "sessionID": document.cookie.split('=')[1],
            'Content-Type': 'application/json',
        },
    });

    const data = await response.json();
    const arr=['/','/html/login.jsp','/html/register.jsp','/login','/register'];
    if (data.success) {
        sessionStorage.setItem('user', JSON.stringify(data.user));
        if (arr.includes(window.location.pathname)) {
            window.location.href = '/index';
        }

        return {success: true};
    } else {
        if (!arr.includes(window.location.pathname)) {
            window.location.href = '/login';
        }
        return {success: false};
    }
}

async function fetchPublicKey() {
    try {
        const response = await fetch(`${SERVER_URL}/get_public_key`);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('获取公钥失败:', error);
    }
}

function generateSessionID() {
    // 使用 crypto.randomUUID() 生成唯一 ID（推荐）
    if (window.crypto && window.crypto.randomUUID) {
        return crypto.randomUUID();
    }
    // 如果 crypto.randomUUID 不可用，则使用随机数生成器
    return 'sess-' + Math.random().toString(36).substring(2) + Date.now().toString(36);
}

function setSessionCookie() {
    if (!document.cookie.split('; ').find(row => row.startsWith('sessionID='))) {
        const sessionID = generateSessionID();
        const expirationDays = 1; // 过期时间（单位：天）
        const date = new Date();
        date.setTime(date.getTime() + expirationDays * 24 * 60 * 60 * 1000); // 计算过期时间
        const expires = `expires=${date.toUTCString()}`;
        document.cookie = `sessionID=${sessionID}; ${expires}; path=/`; // path=/ 表示全站有效
    }
}

// 获取当前用户信息
async function fetchCurrentUser() {
    function getRoleName(role) {
        role=Math.abs(role);
        const roleNames = {
            '1': '超级管理员',
            '2': '管理员',
            '3': '普通用户'
        };
        return roleNames[role] || '未知';
    }
    try {
        const response = await fetch(`${SERVER_URL}/current_user`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        const data = await response.json();
        const role=data.user.role;

        const currentUserElement = document.getElementById('currentUser');

        if (currentUserElement) {
            currentUserElement.textContent = `当前用户: ${data.user.username},身份为: ${getRoleName(role)}`;
        }
        return data.user;

    } catch (error) {
        console.error('获取当前用户失败:', error);
    }
}
// 通用的 fetch 函数
async function fetchAPI(endpoint, options = {}) {
    const defaultOptions = {
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    };

    try {
        const response = await fetch(`${SERVER_URL}${endpoint}`, {
            ...defaultOptions,
            ...options,
        });
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('API请求失败:', error);
        throw error;
    }
}
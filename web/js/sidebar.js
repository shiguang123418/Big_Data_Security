document.addEventListener('DOMContentLoaded', async function () {
    const contentFrame = document.getElementById('contentFrame');
    const profileButton = document.getElementById('profileButton');
    const logoutButton = document.getElementById('logoutButton');
    const navLinks = document.querySelectorAll('.nav-link');

    // 导航链接点击处理
    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const pageName = link.getAttribute('href').split('/').pop().replace('.html', ''); // 提取页面名称
            window.history.pushState({ page: pageName }, '', `?page=${pageName}`); // 更新 URL 参数
            navigateToPage(pageName);
        });
    });

    // 个人主页按钮点击处理
    profileButton.addEventListener('click', () => {
        navLinks.forEach(l => l.classList.remove('active'));
        window.history.pushState({ page: 'profile' }, '', '?page=profile'); // 更新 URL 参数
        checkSession();
        navigateToPage('profile');
    });

    // 退出登录处理
    logoutButton.addEventListener('click', async () => {
        try {
            const data = await fetchAPI('/logout', { method: 'POST' });
            console.log('退出登录:', data);
            if (data.success) {
                window.location.href = '/login';
            }
        } catch (error) {
            console.error('退出失败:', error);
        }
    });

    // 解析 URL 参数，判断是否需要跳转到特定页面
    const urlParams = new URLSearchParams(window.location.search);
    const page = urlParams.get('page'); // 获取参数值，例如 "profile" 或 "dashboard"
    console.log('当前页面:', page);
    if (page) {
        navigateToPage(page); // 根据参数值导航到指定页面
    } else {
        navigateToPage('users'); // 参数为空时，加载默认页面
    }

    checkSession();

    // 获取并显示当前用户
    fetchCurrentUser();
});

function navigateToPage(page) {
    const contentFrame = document.getElementById('contentFrame');
    const navLinks = document.querySelectorAll('.nav-link');

    // 清除所有导航链接的 active 状态
    navLinks.forEach(l => l.classList.remove('active'));
    contentFrame.src = 'pages/'+page+'.html'; // 显示加载中页面

    
    // 如果有对应的导航链接，设置为 active
    const activeLink = Array.from(navLinks).find(link => link.href.endsWith(page + '.html'));
    if (activeLink) {
        activeLink.classList.add('active');
    }
}

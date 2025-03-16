// 检查登录状态
function checkLoginStatus() {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    
    if (token && username) {
        // 用户已登录
        if (document.getElementById('userInfo')) {
            document.getElementById('userInfo').innerHTML = `欢迎您，<strong>${username}</strong>`;
        }
        
        if (document.getElementById('loggedInContent')) {
            document.getElementById('loggedInContent').style.display = 'block';
        }
    }
}

// 退出登录
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    
    // 刷新页面
    window.location.reload();
}

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    checkLoginStatus();
    
    // 退出登录
    document.getElementById('logoutBtn').addEventListener('click', function() {
        logout();
    });
});
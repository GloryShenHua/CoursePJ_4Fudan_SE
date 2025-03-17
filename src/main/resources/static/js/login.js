// 显示消息
function showMessage(message, isError = false) {
    const messageElement = document.getElementById('message');
    if (messageElement) {
        messageElement.textContent = message;
        messageElement.className = isError ? 'message error' : 'message success';
        messageElement.style.display = 'block';
        
        // 5秒后自动隐藏消息
        setTimeout(() => {
            messageElement.style.display = 'none';
        }, 5000);
    }
}

// 登录函数
function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    if (!username || !password) {
        showMessage('请填写用户名和密码', true);
        return;
    }
    
    // 构建表单数据
    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);
    
    // 发送登录请求
    fetch('/api/auth/login', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text || '登录失败，请检查用户名和密码');
            });
        }
        return response.json();
    })
    .then(data => {
        // 保存token到localStorage
        localStorage.setItem('token', data.token);
        localStorage.setItem('username', username);
        
        showMessage('登录成功！即将跳转到首页...');
        
        // 3秒后跳转到首页
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 3000);
    })
    .catch(error => {
        showMessage(error.message, true);
    });
}

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 登录表单提交
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        e.preventDefault();
        login();
    });
});
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

// 注册函数
function register() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const captcha = document.getElementById('captcha').value;
    
    if (!username || !password || !captcha) {
        showMessage('请填写所有字段', true);
        return;
    }
    
    // 构建表单数据
    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);
    formData.append('captcha', captcha);
    
    // 发送注册请求
    fetch('/api/auth/register', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text || '注册失败，请稍后再试');
            });
        }
        return response.json();
    })
    .then(data => {
        showMessage('注册成功！即将跳转到登录页面...');
        
        // 3秒后跳转到登录页面
        setTimeout(() => {
            window.location.href = 'login.html';
        }, 3000);
    })
    .catch(error => {
        showMessage(error.message, true);
        // 刷新验证码
        document.getElementById('captchaImage').src = '/api/captcha?' + new Date().getTime();
    });
}

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 刷新验证码
    document.getElementById('captchaImage').addEventListener('click', function() {
        this.src = '/api/captcha?' + new Date().getTime();
    });
    
    // 注册表单提交
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        e.preventDefault();
        register();
    });
});
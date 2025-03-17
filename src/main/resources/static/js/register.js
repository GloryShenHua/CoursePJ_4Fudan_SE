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

// 检查密码强度
function checkPasswordStrength(password) {
    const strengthElement = document.getElementById('passwordStrength');
    
    // 如果密码长度小于6
    if (password.length < 6) {
        strengthElement.className = 'password-strength strength-invalid';
        strengthElement.textContent = '密码长度至少6位';
        return;
    }

    // 计算密码强度
    let strength = 0;
    
    // 检查是否包含数字
    if (/\d/.test(password)) {
        strength += 1;
    }
    
    // 检查是否包含小写字母
    if (/[a-z]/.test(password)) {
        strength += 1;
    }
    
    // 检查是否包含大写字母
    if (/[A-Z]/.test(password)) {
        strength += 1;
    }
    
    // 检查是否包含特殊字符
    if (/[!@#$%^&*(),.?":{}|<>]/.test(password)) {
        strength += 1;
    }
    
    // 根据强度显示不同的提示
    strengthElement.className = 'password-strength';
    switch (strength) {
        case 1:
            strengthElement.className += ' strength-weak';
            strengthElement.textContent = '密码强度：弱';
            break;
        case 2:
            strengthElement.className += ' strength-medium';
            strengthElement.textContent = '密码强度：中';
            break;
        case 3:
        case 4:
            strengthElement.className += ' strength-strong';
            strengthElement.textContent = '密码强度：强';
            break;
        default:
            strengthElement.className += ' strength-invalid';
            strengthElement.textContent = '密码需包含字母和数字';
    }
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
    
    // 添加密码输入监听
    document.getElementById('password').addEventListener('input', function(e) {
        checkPasswordStrength(e.target.value);
    });
});
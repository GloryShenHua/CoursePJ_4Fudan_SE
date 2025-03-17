# 13th_group

## 代码框架

### 前端部分

以下为代码结构：

- src/
  - main/
    - resources/
      -  static/	(静态资源根目录)
        - css/ 	(样式文件目录）
          - style.css 	(全局样式）
        -  js/       (JavaScript文件目录)
          - index.js	(首页相关功能)
          - login.js 	(登录相关功能）
          - register.js     (注册相关功能)
        - index.html
        - login.html
        - register.html

### 后端部分

以下是代码结构：  

- src/
    - main/
        - java/
            - com.example.springboot101/
                - config/（配置类）
                    - KaptchaConfig
                    - SecurityConfig
                    - WebSecurityConfig
                - controller/（定义接口，处理HTTP请求和响应）
                    - AuthController
                    - CaptchaController
                    - Response
                - entity/（定义数据模型，对应数据库中的表结构）
                    - User
                - exception/（自定义异常和全局异常处理器，统一处理业务逻辑中的错误）
                    - CaptchaincorrectException
                    - GlobalExceptionHandler
                    - InvalidPasswordException
                    - InvalidUsernameException
                    - PasswordIncorrectException
                    - UsernameAlreadyExistsException
                    - UserNotFoundException
                - repository/（数据访问层（DAO），直接操作数据库）
                    - UserRepository
                - service/（业务逻辑层，处理核心业务规则）
                    - impl/
                        - UserServiceImpl
                    - UserService
                - util/（存放工具类）
                    - JWTUtil
                - Springboot101Application 
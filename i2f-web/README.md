# i2f-web

## 初始化项目
```
npm install
```

### 运行项目
- 以开发模式运行
```
npm run dev
```
- 以测试模式运行
```
npm run test
```
- 以生成模式运行
```
npm run prod
```

### 打包项目
- 打包生成项目
```
npm run build:prod
```
- 打包测试项目
```
npm run build:test
```
- 打包开发项目
```
npm run build:dev
```

### 执行lint检查
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

## 框架介绍
```
i2f-ui
+---public  // 公共路径，一般不做修改
|       favicon.ico
|       index.html
\---src     // 源码路径
|    |   main.js    // 入口文件
|    +---assets // 静态资源目录
|    |       logo.png   
|    +---components // 组件目录
|    |       HelloWorld.vue
|    \---framework  // 本框架的目录
|    |    +---app   // app相关的配置，主要是整合路由和原型挂载等操作
|    |    |       App.vue
|    |    |       index.js
|    |    +---auth  // 授权相关的，主要是登录控制和权限控制，还有路由的访问控制
|    |    |       index.js
|    |    +---config    // 配置相关的，主要是存放框架中的一些公共配置
|    |    |       index.js
|    |    +---error // 错误相关的，主要是存放错误页，这里应该是你要修改的地方
|    |    |       Error404.vue
|    |    +---message   // 消息通知相关的，主要用来呈现弹出式提醒用户的消息，这里应该是你要修改的地方
|    |    |       index.js
|    |    +---request   // 网络请求相关的，主要是进行网络交互，此部分一般不修改
|    |    |   |   index.js
|    |    |   +---download  // 网络的下载方面
|    |    |   |       index.js
|    |    |   +---exception // 处理网络请求异常提示和跳转
|    |    |   |       index.js
|    |    |   \---multipart // 处理文件上传
|    |    |           index.js
|    |    +---router    // 处理路由的核心，实现路由和路由守卫，这个一般不修改
|    |    |       index.js
|    |    +---routes    // 这里是定义路由映射的，一般修改这里添加路由
|    |    |       index.js
|    |    +---store    // 这里是定义状态的，需要使用状态在这里使用
|    |    |       index.js
|    |    \---utils // 这里定义了一些工具类
|    |            ase-util.js
|    |            base64-util.js
|    |            date-util.js
|    |            json-util.js
|    |            math-util.js
|    |            md5-util.js
|    |            vue-util.js
|    \---views  // 一般写页面的路径
|           AboutView.vue
|           Hello.vue
|           HomeView.vue
|           Login.vue
\---.env.dev    // dev开发环境配置
\---.env.test   // test测试环境配置
\---.env.prod   // prod生成环境配置
\---package.json    // 依赖包配置
\---vue.config.js   // vue项目配置
\---babel.config.js // babel配置
\---jsconfig.json   // js配置
\---.gitignore  // git提交配置
\---README.md   // 本文档
```

## 框架的使用
- 根据自己的需要配置环境变量
```
.env.dev
.env.test
.env.prod
```
- 编辑框架配置
```
src/framework/config/index.js
```
- 编辑路由
```
src/framework/routes/index.js
```
- 添加登录页面
- 登录成功自后获取资源信息到Auth中管理
- 可参考src/views/Login.vue实现
```
src/framwork/auth/index.js
```
- 改写通知消息的显示方式
```
src/framework/message/index.js
```


## 原始项目的构建
- 检查vue-cli版本
```
vue -V
```
- 如果是2.x版本
- 则进行升级
```
npm uninstall vue-cli -g
npm install -g @vue/cli
```
- 新建项目
```
vue create i2f-ui
```
- 直接使用
- 选择自定义
```
Vue CLI v5.0.8
? Please pick a preset: (Use arrow keys)
? Please pick a preset:
  Default ([Vue 3] babel, eslint)
  Default ([Vue 2] babel, eslint)
> Manually select features
```
- 勾选上router和vuex
```
Vue CLI v5.0.8
? Please pick a preset: (Use arrow keys)
? Please pick a preset: Manually select features
? Check the features needed for your project: (Press <space> to select, <a> to toggle all, <i> to invert selection, and <enter> to proceed)
 (*) Babel
 ( ) TypeScript
 ( ) Progressive Web App (PWA) Support
 (*) Router
 (*) Vuex
 ( ) CSS Pre-processors
>(*) Linter / Formatter
 ( ) Unit Testing
 ( ) E2E Testing
```
- 选择vue3
```
Vue CLI v5.0.8
? Please pick a preset: (Use arrow keys)
? Please pick a preset: Manually select features
? Check the features needed for your project: Babel, Router, Vuex, Linter
? Choose a version of Vue.js that you want to start the project with (Use arrow keys)
> 3.x
  2.x
```
- 不使用history模式，也就是使用hash模式
```
Vue CLI v5.0.8
? Please pick a preset: (Use arrow keys)
? Please pick a preset: Manually select features
? Check the features needed for your project: Babel, Router, Vuex, Linter
? Choose a version of Vue.js that you want to start the project with 3.x
? Use history mode for router? (Requires proper server setup for index fallback in production) (Y/n) n
```
- 使用标准的eslint
```
Vue CLI v5.0.8
? Please pick a preset: (Use arrow keys)
? Please pick a preset: Manually select features
? Check the features needed for your project: Babel, Router, Vuex, Linter
? Choose a version of Vue.js that you want to start the project with 3.x
? Use history mode for router? (Requires proper server setup for index fallback in production) No
? Pick a linter / formatter config:
  ESLint with error prevention only
  ESLint + Airbnb config
> ESLint + Standard config
  ESLint + Prettier
```
- 指定在lint在保存和提交的时候生效
```
Vue CLI v5.0.8
? Please pick a preset: (Use arrow keys)
? Please pick a preset: Manually select features
? Check the features needed for your project: Babel, Router, Vuex, Linter
? Choose a version of Vue.js that you want to start the project with 3.x
? Use history mode for router? (Requires proper server setup for index fallback in production) No
? Pick a linter / formatter config: Standard
? Pick additional lint features: (Press <space> to select, <a> to toggle all, <i> to invert selection, and <enter> to proceed)
 (*) Lint on save
>(*) Lint and fix on commit
```
- 使用package.json进行控制
```
Vue CLI v5.0.8
? Please pick a preset: (Use arrow keys)
? Please pick a preset: Manually select features
? Check the features needed for your project: Babel, Router, Vuex, Linter
? Choose a version of Vue.js that you want to start the project with 3.x
? Use history mode for router? (Requires proper server setup for index fallback in production) No
? Pick a linter / formatter config: Standard
? Pick additional lint features: Lint on save, Lint and fix on commit
? Where do you prefer placing config for Babel, ESLint, etc.?
  In dedicated config files
> In package.json
```
- 不保存预设，如果以后都想这样创建，可以选择是
```
Vue CLI v5.0.8
? Please pick a preset: (Use arrow keys)
? Please pick a preset: Manually select features
? Check the features needed for your project: Babel, Router, Vuex, Linter
? Choose a version of Vue.js that you want to start the project with 3.x
? Use history mode for router? (Requires proper server setup for index fallback in production) No
? Pick a linter / formatter config: Standard
? Pick additional lint features: Lint on save, Lint and fix on commit
? Where do you prefer placing config for Babel, ESLint, etc.? In package.json
? Save this as a preset for future projects? (y/N) n
```
- 等待下载完毕即可
- 这样，你就拥有基本的vue3的项目了


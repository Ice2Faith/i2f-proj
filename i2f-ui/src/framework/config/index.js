// 定义系统配置
const Config={
    // 是否适用HASH模式
    ROUTER_HASH_MODE: true,
    // 指定router的base路径
    ROUTER_BASE_PATH: '/',
    // token登录令牌的请求头名称
    HEADER_TOKEN_NAME: 'token',
    // 代理的请求的baseUrl
    REQUEST_PROXY_URL: '/proxy-api',
    // 登录的路由
    LOGIN_ROUTE: '/login',
    // 请求类型
    REQUEST_DEFAULT_CONTENT_TYPE: 'application/json;charset=utf-8',
    // 请求超时
    REQUEST_DEFAULT_TIMEOUT: 30*60*1000
}

export default Config

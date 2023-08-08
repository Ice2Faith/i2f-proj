const {defineConfig} = require('@vue/cli-service')
// 压缩
const CompressionPlugin = require('compression-webpack-plugin')
const productionGzipExtensions = /\.(js|css|json|txt|html|ico|svg)(\?.*)?$/i
// webpack
const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

// JS压缩
const UglifyJsPlugin = require('uglifyjs-webpack-plugin')

module.exports = defineConfig({
  transpileDependencies: true,
  // 资源路径
  publicPath: './',
  // 输出路径
  outputDir: 'dist',
  // 静态资源路径
  assetsDir: "static",
  // html的输出路径
  indexPath: "index.html",
  // 是否开启文件名hash，开启之后可以避免缓存无法刷新
  filenameHashing: true,
  // 是否在保存时使用eslint检查
  lintOnSave: true,
  // 是否启用运行时编译版本，允许在组件中使用template
  runtimeCompiler: true,
  // 是否保留代码定位信息
  productionSourceMap: process.env.NODE_ENV == 'prod' ? false : true,
  // 开发服务配置
  devServer: {
    // 主机，绑定任意地址
    host: '0.0.0.0',
    // 使用环境的端口
    port: process.env.VUE_APP_PORT,
    // 是否自动打开浏览器
    open: process.env.NODE_ENV == 'dev' ? true : false,
    // 设置是否https
    https: false,
    // 是否热加载
    hot: true,
    // 是否启用压缩
    compress: process.env.NODE_ENV != 'dev' ? true : false,
    // 客户端配置
    client: {
      // 当出现编译错误或警告时，在浏览器中是否显示全屏覆盖。
      // 示例为只显示错误信息
      overlay: {
        errors: false,
        warnings: false,
        runtimeErrors: false
      },
    },
    // 代理配置
    proxy: {
      // 代理的接口路径
      "/proxy-api": {
        // 重写路径
        pathRewrite: {
          "^/proxy-api": ""
        },
        // 代理到环境的baseURL
        target: process.env.VUE_APP_BASE_URL,
        // 是否代理websocket
        ws: true,
        // 是否进行origin改变，支持跨域
        changeOrigin: true,
        // 是否热更改
        hot: true
      }
    },

  },
  // webpack配置
  configureWebpack: {
    // 优化
    optimization: {
      nodeEnv: false,
      minimizer: [
        new UglifyJsPlugin({
          uglifyOptions: {
            output: {
              // 删除注释
              comments: process.env.NODE_ENV == 'prod' ? true : false
            },
            //生产环境自动删除console
            compress: {
              warnings: process.env.NODE_ENV == 'prod' ? false : true, // 若打包错误，则注释这行
              drop_debugger: process.env.NODE_ENV == 'prod' ? false : true,  //清除 debugger 语句
              drop_console: process.env.NODE_ENV == 'prod' ? false : true,   //清除console语句
              pure_funcs: process.env.NODE_ENV == 'prod' ? ['console.log'] : []
            }
          },
          sourceMap: false,
          parallel: true
        })
      ]
    },
  },
  // 打包配置
  chainWebpack: config => {
    // 设置组件别名
    config.resolve.alias
      .set('@', resolve('src'))

    if (process.env.NODE_ENV != 'dev') {
      // 不是开发环境时，启用gzip压缩
      // 需要使用nginx添加gzip_static on; 配置进行支持
      // 这个配置需要编译时启动插件：--with-http_gzip_static_module
      // 因此，自行决定是否启用gzip打包
      // config.plugin('compressionPlugin')
      //   .use(new CompressionPlugin({
      //     filename: '[path].gz[query]',
      //     algorithm: 'gzip', // 使用gzip压缩
      //     test: productionGzipExtensions, // 匹配文件名
      //     threshold: 10240, // 对超过10k的数据压缩
      //     minRatio: 0.8, // 压缩率小于0.8才会压缩
      //     deleteOriginalAssets: true // 是否删除未压缩的源文件，谨慎设置，如果希望提供非gzip的资源，可不设置或者设置为false（比如删除打包后的gz后还可以加载到原始资源文件）
      //   }))
      //   .end()

    }

    // 启用文件名hash，避免重新部署之后的缓存问题
    config.output.filename('js/[name].[hash].js').end()
  }
})

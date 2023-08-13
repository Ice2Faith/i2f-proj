const { defineConfig } = require('@vue/cli-service')
// 压缩
const CompressionPlugin = require('compression-webpack-plugin')
const productionGzipExtensions = /\.(js|css|json|txt|html|ico|svg)(\?.*)?$/i
// webpack
const path = require('path')

function resolve (dir) {
  return path.join(__dirname, dir)
}

// 打包分析
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin
const WebpackObfuscatorPlugin = require('webpack-obfuscator')

module.exports = defineConfig({
  transpileDependencies: true,
  // 资源路径
  publicPath: './',
  // 输出路径
  outputDir: 'dist',
  // 静态资源路径
  assetsDir: 'static',
  // html的输出路径
  indexPath: 'index.html',
  // 是否开启文件名hash，开启之后可以避免缓存无法刷新
  filenameHashing: true,
  // 是否在保存时使用eslint检查
  lintOnSave: true,
  // 是否启用运行时编译版本，允许在组件中使用template
  runtimeCompiler: true,
  // 是否保留代码定位信息
  productionSourceMap: process.env.NODE_ENV != 'prod',
  // 开发服务配置
  devServer: {
    // 主机，绑定任意地址
    host: '0.0.0.0',
    // 使用环境的端口
    port: process.env.VUE_APP_PORT,
    // 是否自动打开浏览器
    open: process.env.NODE_ENV == 'dev',
    // 设置是否https
    https: false,
    // 是否热加载
    hot: true,
    // 是否启用压缩
    compress: process.env.NODE_ENV != 'dev',
    // 客户端配置
    client: {
      // 当出现编译错误或警告时，在浏览器中是否显示全屏覆盖。
      // 示例为只显示错误信息
      overlay: {
        errors: false,
        warnings: false,
        runtimeErrors: false
      }
    },
    // 代理配置
    proxy: {
      // 代理的接口路径
      '/proxy-api': {
        // 重写路径
        pathRewrite: {
          '^/proxy-api': ''
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
    }

  },
  // webpack配置
  configureWebpack: {

  },
  // 打包配置
  chainWebpack: config => {
    // 设置组件别名
    config.resolve.alias
      .set('@', resolve('src'))

    if (process.argv.includes('--report')) {
      config.plugin('webpack-bundle-analyzer')
        .use(
          new BundleAnalyzerPlugin({
            analyzerPort: 'auto', // server端口，默认8888, auto任意可用端口 / 指定端口
            analyzerMode: 'server' // 模式，默认server，server服务模式 / static只生成静态文件 json文件模式，disabled 禁用
          })
        )
        .end()
    }

    if (process.env.NODE_ENV == 'prod') {
      /**
       * 配置代碼混淆
       * 参数：options配置对象,排除的结果js数组
       * 其他使用说明：
       * 针对混淆来说，有时候部分代码不能进行混淆，或导致功能不正常或者页面无法打开
       * 这种情况可以使用对应的注释语句进行控制
       * 注释语句如下：
       * javascript-obfuscator:disable
       * 作用是在注释语句的下一个代码节点上，禁用混淆，后续的代码不影响
       * 也即是说，只作用于紧接着的后一个代码语句
       * 因为是注释语句，所以用js支持的注释方式对此注释语句都是可以的
       * 比如：
       * // javascript-obfuscator:disable
       */
      config.plugin('webpack-obfuscator')
        .use(
          new WebpackObfuscatorPlugin({
            // 压缩,无换行
            compact: true,
            // 是否启用控制流扁平化(降低1.5倍的运行速度)
            controlFlowFlattening: false,
            // 应用概率;在较大的代码库中，建议降低此值，因为大量的控制流转换可能会增加代码的大小并降低代码的速度。
            controlFlowFlatteningThreshold: 0.75,
            // 随机的死代码块(增加了混淆代码的大小)
            deadCodeInjection: true,
            // 死代码块的影响概率
            deadCodeInjectionThreshold: 0.75,
            // 此选项几乎不可能使用开发者工具的控制台选项卡
            debugProtection: true,
            // 如果选中，则会在“控制台”选项卡上使用间隔强制调试模式，从而更难使用“开发人员工具”的其他功能。
            debugProtectionInterval: 5000,
            // 通过用空函数替换它们来禁用console.log，console.info，console.error和console.warn。这使得调试器的使用更加困难。
            disableConsoleOutput: true,
            // 锁定混淆的源代码，使其仅在特定域和/或子域上运行。这使得某人只需复制并粘贴您的源代码并在其他地方运行就变得非常困难。
            domainLock: [],
            // 标识符的混淆方式 hexadecimal(十六进制) mangled(短标识符)
            identifierNamesGenerator: 'hexadecimal',
            // 全局标识符添加特定前缀,在混淆同一页面上加载的多个文件时使用此选项。此选项有助于避免这些文件的全局标识符之间发生冲突。为每个文件使用不同的前缀
            identifiersPrefix: '',
            inputFileName: '',
            // 允许将信息记录到控制台。
            log: false,
            // 是否启用全局变量和函数名称的混淆
            renameGlobals: false,
            // 禁用模糊处理和生成标识符
            reservedNames: [],
            // 禁用字符串文字的转换，这里配置不对空白符号进行转义
            // 否则会导致出现 \x20 或者其他的符号，导致样式或者显示文字不正常
            // 更严重的可能导致函数调用不正常
            // 注意，这里就是 '\s*' , 并没有写错，不要改成 '\\s*'
            reservedStrings: ['\s*'],
            // 通过固定和随机（在代码混淆时生成）的位置移动数组。这使得将删除的字符串的顺序与其原始位置相匹配变得更加困难。如果原始源代码不小，建议使用此选项，因为辅助函数可以引起注意。
            rotateStringArray: true,
            seed: 0,
            // 混淆后的代码,不能使用代码美化,同时需要配置 compat:true;
            selfDefending: true,
            sourceMap: false,
            sourceMapBaseUrl: '',
            sourceMapFileName: '',
            sourceMapMode: 'separate',
            // 删除字符串文字并将它们放在一个特殊的数组中
            stringArray: true,
            // 编码的所有字符串文字stringArray使用base64或rc4并插入即用其解码回在运行时的特殊代码。true（boolean）：stringArray使用编码值base64;false（boolean）：不编码stringArray值;'base64'（string）：stringArray使用编码值base64;'rc4'（string）：stringArray使用编码值rc4。大约慢30-50％base64，但更难获得初始值。建议禁用unicodeEscapeSequence带rc4编码的选项以防止非常大的混淆代码。
            stringArrayEncoding: ['base64'],
            // 调整字符串文字将插入stringArray的概率
            stringArrayThreshold: 0.75,
            // 您可以将混淆代码的目标环境设置为以下之一：Browser;Browser No Eval;Node
            target: 'browser',
            // 是否启用混淆对象键
            transformObjectKeys: false,
            // 允许启用/禁用字符串转换为unicode转义序列。Unicode转义序列大大增加了代码大小，并且可以轻松地将字符串恢复为原始视图。建议仅对小型源代码启用此选项。
            unicodeEscapeSequence: false
          }, ['js/chunk-vendors*.**.js'])
        )
        .end()
    }

    if (process.env.NODE_ENV != 'dev') {
      if (process.argv.includes('--gzip')) {
        // 不是开发环境时，启用gzip压缩
        // 需要使用nginx添加gzip_static on; 配置进行支持
        // 这个配置需要编译时启动插件：--with-http_gzip_static_module
        // 因此，自行决定是否启用gzip打包
        config.plugin('compressionPlugin')
          .use(new CompressionPlugin({
            filename: '[path].gz[query]',
            algorithm: 'gzip', // 使用gzip压缩
            test: productionGzipExtensions, // 匹配文件名
            threshold: 10240, // 对超过10k的数据压缩
            minRatio: 0.8, // 压缩率小于0.8才会压缩
            deleteOriginalAssets: true // 是否删除未压缩的源文件，谨慎设置，如果希望提供非gzip的资源，可不设置或者设置为false（比如删除打包后的gz后还可以加载到原始资源文件）
          }))
          .end()
      }

      // 开启分包，便于初次快速加载显示页面
      config.optimization.splitChunks({
        automaticNameDelimiter: '-',
        cacheGroups: {
          basic: {
            name: 'chunk-basic', // 打包后的文件名
            chunks: 'initial', // all：所有代码有效 ，async 代码分割时对异步代码生效，initial 同步代码有效
            minSize: 128 * 1024, // 代码分割最小的模块大小，引入的模块大于多少字节才做代码分割
            maxSize: 2 * 1024 * 1024, // 代码分割最大的模块大小，大于多少要进行代码分割，一般使用默认值
            minChunks: 2, // 引入的次数大于等于此值时分离打包
            maxAsyncRequests: 10, // 最大的异步请求数量,也就是同时加载的模块最大模块数量
            maxInitialRequests: 5, // 入口文件做代码分割最多分成多少个 js 文件
            priority: 0, // 分包的优先级，越大越优先分包
            reuseExistingChunk: true // 如果模块已经被打包了，不再重复打包
          },
          // 定义本项目的公共部分，也就是最后的被其他分包打包的部分
          common: {
            name: 'chunk-common', // 打包后的文件名
            chunks: 'initial', // all：所有代码有效 ，async 代码分割时对异步代码生效，initial 同步代码有效
            minSize: 512 * 1024, // 代码分割最小的模块大小，引入的模块大于多少字节才做代码分割
            maxSize: 2 * 1024 * 1024, // 代码分割最大的模块大小，大于多少要进行代码分割，一般使用默认值
            minChunks: 1, // 引入的次数大于等于此值时分离打包
            maxAsyncRequests: 10, // 最大的异步请求数量,也就是同时加载的模块最大模块数量
            maxInitialRequests: 5, // 入口文件做代码分割最多分成多少个 js 文件
            priority: 10, // 分包的优先级，越大越优先分包
            reuseExistingChunk: true // 如果模块已经被打包了，不再重复打包
            // enforce: true // 为true时，忽略minSize，minChunks，maxAsyncRequests和maxInitialRequests选项
          },
          commonAsync: {
            name: 'chunk-common-async', // 打包后的文件名
            chunks: 'async', // all：所有代码有效 ，async 代码分割时对异步代码生效，initial 同步代码有效
            minSize: 512 * 1024, // 代码分割最小的模块大小，引入的模块大于多少字节才做代码分割
            maxSize: 2 * 1024 * 1024, // 代码分割最大的模块大小，大于多少要进行代码分割，一般使用默认值
            minChunks: 1, // 引入的次数大于等于此值时分离打包
            maxAsyncRequests: 10, // 最大的异步请求数量,也就是同时加载的模块最大模块数量
            maxInitialRequests: 5, // 入口文件做代码分割最多分成多少个 js 文件
            priority: 11, // 分包的优先级，越大越优先分包
            reuseExistingChunk: true // 如果模块已经被打包了，不再重复打包
            // enforce: true // 为true时，忽略minSize，minChunks，maxAsyncRequests和maxInitialRequests选项
          },
          src: {
            name: 'chunk-src', // 打包后的文件名
            test: resolve('/src'),
            chunks: 'all', // all：所有代码有效 ，async 代码分割时对异步代码生效，initial 同步代码有效
            minSize: 512 * 1024, // 代码分割最小的模块大小，引入的模块大于多少字节才做代码分割
            maxSize: 2 * 1024 * 1024, // 代码分割最大的模块大小，大于多少要进行代码分割，一般使用默认值
            minChunks: 1, // 引入的次数大于等于此值时分离打包
            maxAsyncRequests: 10, // 最大的异步请求数量,也就是同时加载的模块最大模块数量
            maxInitialRequests: 5, // 入口文件做代码分割最多分成多少个 js 文件
            priority: 12, // 分包的优先级，越大越优先分包
            reuseExistingChunk: true // 如果模块已经被打包了，不再重复打包
            // enforce: true // 为true时，忽略minSize，minChunks，maxAsyncRequests和maxInitialRequests选项
          },
          // 定义本项目引用的第三方库的部分
          vendors: {
            name: 'chunk-vendors',
            test: /[\\/]node_modules[\\/]/,
            chunks: 'initial',
            minSize: 512 * 1024, // 代码分割最小的模块大小，引入的模块大于多少字节才做代码分割
            maxSize: 2 * 1024 * 1024, // 代码分割最大的模块大小，大于多少要进行代码分割，一般使用默认值
            minChunks: 1, // 引入的次数大于等于此值时分离打包
            maxAsyncRequests: 10, // 最大的异步请求数量,也就是同时加载的模块最大模块数量
            maxInitialRequests: 5, // 入口文件做代码分割最多分成多少个 js 文件
            priority: 20,
            reuseExistingChunk: true
            // enforce: true // 为true时，忽略minSize，minChunks，maxAsyncRequests和maxInitialRequests选项
          },
          // 定义本项目引用的第三方库的部分
          vendorsAsync: {
            name: 'chunk-vendors-async',
            test: /[\\/]node_modules[\\/]/,
            chunks: 'async',
            minSize: 512 * 1024, // 代码分割最小的模块大小，引入的模块大于多少字节才做代码分割
            maxSize: 2 * 1024 * 1024, // 代码分割最大的模块大小，大于多少要进行代码分割，一般使用默认值
            minChunks: 1, // 引入的次数大于等于此值时分离打包
            maxAsyncRequests: 10, // 最大的异步请求数量,也就是同时加载的模块最大模块数量
            maxInitialRequests: 5, // 入口文件做代码分割最多分成多少个 js 文件
            priority: 21,
            reuseExistingChunk: true
            // enforce: true // 为true时，忽略minSize，minChunks，maxAsyncRequests和maxInitialRequests选项
          }
        }
      })
    }

    // 启用文件名hash，避免重新部署之后的缓存问题
    config.output.filename('js/[name].[hash].js').end()

    config.output.chunkFilename('js/[name].[hash].js').end()
  }
})

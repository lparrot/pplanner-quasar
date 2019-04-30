// Configuration for your app

const appName = 'PPlanner'

module.exports = function (ctx) {
  ctx.config = {
    auth: {
      storage: {
        token: 'token',
        redirect: 'redirect',
      },
      api: {
        login: '/api/auth/login',
        me: '/api/auth/user',
      },
    },
  }

  return {
    preFetch: true,
    // app boot file (/src/boot)
    // --> boot files are part of "main.js"
    boot: [
      'app',
      'axios',
      'auth',
      'moment',
      'vee-validate',
    ],

    css: [
      'app.scss',
    ],

    extras: [
      'roboto-font',
      'material-icons', // optional, you are not bound to it
      // 'ionicons-v4',
      // 'mdi-v3',
      // 'fontawesome-v5',
      // 'eva-icons'
    ],

    framework: {
      all: true,
      lang: 'fr',
    },

    supportIE: true,

    build: {
      distDir: 'target/dist',
      scopeHoisting: true,
      vueRouterMode: 'history',
      // vueCompiler: true,
      // gzip: true,
      // analyze: true,
      // extractCSS: false,
      extendWebpack (cfg) {
        cfg.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /node_modules/,
        })
      },
      env: {
        APP_NAME: JSON.stringify(appName),
        CONFIG: JSON.stringify(ctx.config),
      },
    },

    devServer: {
      // https: true,
      port: 3000,
      open: false,
      proxy: {
        // proxy all requests starting with /api to jsonplaceholder
        '/api': {
          target: 'http://localhost:8080',
          changeOrigin: true,
        },
      },
    },

    // animations: 'all', // --- includes all animations
    animations: 'all',

    ssr: {
      pwa: false,
    },

    pwa: {
      // workboxPluginMode: 'InjectManifest',
      // workboxOptions: {}, // only for NON InjectManifest
      manifest: {
        // name: 'Quasar App',
        // short_name: 'Quasar-PWA',
        // description: 'Best PWA App in town!',
        display: 'standalone',
        orientation: 'portrait',
        background_color: '#ffffff',
        theme_color: '#027be3',
        icons: [
          {
            'src': 'statics/icons/icon-128x128.png',
            'sizes': '128x128',
            'type': 'image/png',
          },
          {
            'src': 'statics/icons/icon-192x192.png',
            'sizes': '192x192',
            'type': 'image/png',
          },
          {
            'src': 'statics/icons/icon-256x256.png',
            'sizes': '256x256',
            'type': 'image/png',
          },
          {
            'src': 'statics/icons/icon-384x384.png',
            'sizes': '384x384',
            'type': 'image/png',
          },
          {
            'src': 'statics/icons/icon-512x512.png',
            'sizes': '512x512',
            'type': 'image/png',
          },
        ],
      },
    },

    cordova: {
      // id: 'org.cordova.quasar.app'
      // noIosLegacyBuildFlag: true // uncomment only if you know what you are doing
    },

    electron: {
      // bundler: 'builder', // or 'packager'

      extendWebpack (cfg) {
        // do something with Electron main process Webpack cfg
        // chainWebpack also available besides this extendWebpack
      },

      packager: {
        // https://github.com/electron-userland/electron-packager/blob/master/docs/api.md#options

        // OS X / Mac App Store
        // appBundleId: '',
        // appCategoryType: '',
        // osxSign: '',
        // protocol: 'myapp://path',

        // Window only
        // win32metadata: { ... }
      },

      builder: {
        // https://www.electron.build/configuration/configuration

        // appId: 'quasar-app'
      },
    },
  }
}

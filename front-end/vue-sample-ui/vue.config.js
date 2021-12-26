module.exports = {
  devServer: {
    port: 8080,
    host: 'localhost',
    https: false,
    open: true,    
    proxy: {
      '/api': {
        target: 'http://localhost:8000/',
        secure: false,
        changOrigin: true,
      },
      '/test': {
        target: 'http://localhost:8000/',
        secure: false,
        changOrigin: true,
      },
    }
  },
  css: {
    loaderOptions: {
      less: {
        modifyVars: {
          'primary-color': '#1890FF',
          'layout-color': '#9867f7'
        },
        javascriptEnabled: true
      }
    }
  },
}
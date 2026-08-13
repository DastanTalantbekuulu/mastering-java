const path = require('path');

module.exports = {
  devServer: {
    host: "0.0.0.0", 
    port: 5555,      
    allowedHosts: ["all"],
  },

  configureWebpack: {
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src'),
      },
    },
  }
}
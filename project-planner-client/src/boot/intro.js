export default function ({ Vue }) {
  require('intro.js/minified/introjs.min.css')
  Vue.prototype.$intro = require('intro.js')
}

module.exports = {
  root: true,
  env: {
    browser: true,
    node: true,
  },
  parserOptions: {
    parser: 'babel-eslint',
    ecmaFeatures: {
      legacyDecorators: true,
    },
  },
  extends: [
    '@nuxtjs',
    'plugin:nuxt/recommended',
  ],
  // add your custom rules here
  rules: {
    'comma-dangle': 'off',
    'vue/html-indent': 'off',
    'semi': 'off',
    'vue/html-closing-bracket-spacing': 'off',
    'vue/attributes-order': 'off',
    'vue/max-attributes-per-line': 'off',
    'vue/mustache-interpolation-spacing': 'off',
    'no-console': 'off'
  },
};

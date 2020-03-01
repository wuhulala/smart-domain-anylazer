module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    'plugin:vue/essential',
  ],
  rules: {

    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',

    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',

    'no-mixed-spaces-and-tabs': [0, false],

    'no-tabs': 1,

    'eol-last': 0,

    'space-before-function-paren': 0

  },

  parserOptions: {

    parser: 'babel-eslint'

  }

}

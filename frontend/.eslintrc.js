module.exports = {
    root: true,
    env: {
        node: true
    },
    extends: ["plugin:vue/essential", "eslint:recommended"],
    parserOptions: {
        parser: "@babel/eslint-parser"
    },
    rules: {
        "comma-dangle": ["error", "never"],
        "vue/multi-word-component-names": 0
    }
};

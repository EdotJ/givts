module.exports = {
  future: {
    // removeDeprecatedGapUtilities: true,
    // purgeLayersByDefault: true,
  },
  purge: [
    './public/**/*.html',
    './src/**/*.vue'
  ],
  theme: {
    fontFamily: {
      'button': ['']
    },
    extend: {
        'auth-bg': "url('./src/assets/background.jpg')",
    },
  },
  variants: {},
  plugins: [],
}

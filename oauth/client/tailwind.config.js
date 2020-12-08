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
        backgroundImage: theme => ({
          'auth-bg': "url('/background.jpg')",
        })
    },
  },
  variants: {},
  plugins: [],
}

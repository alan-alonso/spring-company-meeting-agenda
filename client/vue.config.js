module.exports = {
  transpileDependencies: ['vuetify'],
  assetsDir:
    process.env.NODE_ENV === 'production'
      ? process.env.BASE_URL || './assets'
      : './assets'
};

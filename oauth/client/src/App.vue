<template>
  <div
    id="app"
    class="bg-blue-900 h-screen"
  >
    <router-view />
  </div>
</template>

<script>
import store from './store';

export default {
  name: 'App',
  data() {
    return {
      store: store,
    }
  },
  watch: {
    '$route'(to, ) {
      document.title = to.meta.title || 'Authorizer'
    }
  }
}

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  overflow-y: hidden;
}
</style>

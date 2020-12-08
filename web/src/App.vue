<template>
  <div id="app" class="background">
    <Template />
  </div>
</template>

<script>
import Template from "./components/Layout";

export default {
  name: "App",
  components: {
    Template,
  },
  mounted() {
    document.title = "Givts";
  }
};
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
  background-color: #fff5f3;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #201b22;
  background-color: #fff5f3;
  width: 100vw;
  height: 100vh;
  font-size: 1.25rem;
  overflow-x: hidden;
}

.background {
  background-color: #fff5f3;
}

.accent-main {
  background-color: #c44748;
}

.accent-main-darker {
  background-color: #a93737;
}

.accent-main-lighter {
  background-color: #cd6565;
}

.foreground {
  color: #201b22;
}

.foreground-accent {
  color: #fbfaf8;
}

@media only screen and (max-width: 414px) {
}

/* Tablet Styles */
@media only screen and (min-width: 415px) and (max-width: 960px) {
}

/* Desktop Styles */
@media only screen and (min-width: 961px) {
  #app {
    font-size: 1.5rem;
  }
}
</style>

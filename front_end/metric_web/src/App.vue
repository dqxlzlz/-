<template>
  <div id="app">
      <router-view></router-view>
  </div>
</template>

<script>
import Metrics from "@/components/Metrics";
export default {
  name: 'App',
  components: {
    Metrics
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
  margin-top: 0px;
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;
}
</style>

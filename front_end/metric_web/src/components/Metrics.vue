<template>
    <el-container class="contain">
      <el-header style="height: 65px;" class="myel2">
        <el-menu
        style="background-color: #fafafa"
        mode="horizontal"
            :default-active="activeIndex"
            router
        >
            <el-menu-item style="background-color: #fafafa" index="/CK_XML">CK-XML Metric</el-menu-item>
            <el-menu-item style="background-color: #fafafa" index="/CK_JAVA">CK-java Metric</el-menu-item>
            <el-menu-item style="background-color: #fafafa" index="/TRADITION">Tradition Metric</el-menu-item>
            <el-menu-item style="background-color: #fafafa" index="/LK">LK Metric</el-menu-item>
        </el-menu>
      </el-header>
      <el-main class="myel">
        <router-view>
        </router-view>
      </el-main>
    </el-container>
</template>

<script>

import axios from "axios";

export default {
  name: "Metrics",
  data:()=>({
    activeIndex:""
  }),
  methods:{
    getMetrics(){
      axios.get("http://localhost:9000")
          .then(function(result){
            console.log(result)
          })
    }
  },
}
</script>

<style scoped>
.contain{
  display: flex;
  justify-content: space-around;
  float: right;
  background-color:#fafafa;
  height:600px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 20px;
  padding: 20px;
}
</style>
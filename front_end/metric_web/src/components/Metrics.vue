<template>
  <div>
      <h1 class="hh">Metrics</h1>
<!--    <button @click="getMetrics">Get Metrics</button>-->
    <el-container style="height:600px">
      <el-aside width="200px" class="myel2">
        <el-menu
            :default-active="activeIndex"
            router
        >
          <el-submenu index="" style="background-color: #fafafa">
            <template slot="title">
              <i class="el-icon-circle-plus"></i>
              <span>方法选择</span>
            </template>
            <el-menu-item style="background-color: #fafafa" index="/CK_XML">CK-XML Metric</el-menu-item>
            <el-menu-item style="background-color: #fafafa" index="/CK_JAVA">CK-java Metric</el-menu-item>
            <el-menu-item style="background-color: #fafafa" index="/TRADITION">Tradition Metric</el-menu-item>
            <el-menu-item style="background-color: #fafafa" index="/LK">LK Metric</el-menu-item>
            <el-menu-item style="background-color: #fafafa" index="/UCP">UCP用例点度量</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main class="myel">
        <router-view>
        </router-view>
      </el-main>
    </el-container>
  </div>
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
.hh{
  padding-left:190px;
}

.myel{
  border-radius: 30px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  margin-right: 50px;
}

.myel2{
  background-color: #e7e3e3;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 30px;
  margin-right: 20px;
  margin-left: 50px
}

</style>
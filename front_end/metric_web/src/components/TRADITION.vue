<template>
  <div>
    <h1>TRADITION</h1>
    <!--    上传模块 -->
    <el-upload
        drag
        action="/api/upload"
        accept=".java"
        :on-success="handleSuccess">

      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        将文件拖到此处，或<em>点击上传</em>
      </div>
      <div class="el-upload__tip" slot="tip">
        只能上传Java文件
      </div>
    </el-upload>

    <!--结果表格-->
    <el-table
        v-if="data_isShow"
        :data="data"
        style="width:100%"
        align="center">
      <!--Line of Code(LOC)  Cyclomatic Complexity(CC)  Comment Percentage(CP)    -->
      <el-table-column
          prop="loc"
          label="Line of Code(LOC)"
          width="200">
      </el-table-column>
      <el-table-column
          prop="cc"
          label="Cyclomatic Complexity(CC)"
          width="220">
      </el-table-column>
      <el-table-column
          prop="cp"
          label="Comment Percentage(CP)"
          width="200">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "TRADITION",
  data() {
    return {
      data_isShow: false,
      data: [],
    }
  },
  methods:{
    handleSuccess(response){
      console.log(response);
      if(response.success){
        console.log("success")
        this.success(response.msg)
        this.analyse(response.data)
      }
      else{
        this.warn(response.msg);
        console.log("fail")

      }
    },
    analyse(path){
      axios.get("/api/TraJava",{
        params:{
          path:path}
      })
          .then(
              response=>{
                console.log(response)
                if(response.data.success){
                  this.success(response.data.msg)
                  this.data.push(response.data.data)
                  console.log(this.data)
                  console.log("显示前")
                  this.data_isShow=true
                  console.log(this.data_isShow)
                }else{
                  this.warn("文件格式无效，请修改后重试")
                }
              }
          )
    },
    success(message){
      this.$message({
        message:message,
        type:'success'
      });
    },
    warn(message){
      this.$message({
        message:message,
        type:'warning'
      });
    }
  }
}
</script>

<style scoped>

</style>
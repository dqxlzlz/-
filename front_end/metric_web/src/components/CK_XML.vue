<template>
  <div>
    <h1>CK-XML</h1>
    <el-upload
        drag
        action="/api/upload"
        accept=".xml"
        :on-success="handleSuccess"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传.xml文件</div>
    </el-upload>

    <el-image
        v-if="img_show"
        style="width: 300px; height: 300px"
        :src="true_url[0]"
        :preview-src-list="true_url">
    </el-image>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CK_XML",
  data () {
    return {
      img_url: [],
      true_url:[],
      img_show: false,
    }
  },
  methods: {
    handleSuccess (response) {
      console.log(response)
      if(response.success){
        this.success(response.msg)
        this.analyse(response.data)
      }else{
        this.warn(response.msg)
      }
    },
    analyse(path){
      axios.get('/api/CKXml', {
        params: {
          path:path
        }
      }).then(response=>{
        console.log(response)
        if(response.data.success){
          this.success(response.data.msg)
          this.img_url = response.data.data
          this.true_url = []
          for(let i=0;i<this.img_url.length;i++){
            this.getImage(this.img_url[i])
          }
          this.img_show = true
        }else{
          this.warn("文件格式无效，请修改后重试")
          this.img_show = false
        }
      })
    },
    getImage(path){
      axios.get('/api/getImage', {
        params: {
          path: path
        },
        responseType: 'blob'
      }).then(response=>{
        let url = window.URL.createObjectURL(response.data)
        this.true_url.push(url);
        console.log(url)
      })
    },
    success(message) {
      this.$message({
        message: message,
        type: 'success'
      });
    },
    warn(message) {
      this.$message({
        message: message,
        type: 'warning'
      });
    },
  },
}
</script>

<style scoped>

</style>
<template>
  <div>
    <h1>UCP用例点度量</h1>
    <el-upload
        drag
        action=""
        :http-request="show"
        accept=".xml,.java"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传.xml.java文件</div>
    </el-upload>


    <el-row type="flex" class="row-bg" justify="space-around">

      <el-col :span="6"><div class="grid-content">
        <el-input
            prefix-icon="el-icon-search"
            value="请输入参数"
            :disabled="true">
          <template slot="prepend">参数</template>
        </el-input>
      </div></el-col>

      <el-col :span="6"><div class="grid-content">
        <el-input
            placeholder="请输入TCF"
            prefix-icon="el-icon-search"
            v-model="TCF">
          <template slot="prepend">TCF</template>
        </el-input>
      </div></el-col>

      <el-col :span="6"><div class="grid-content">
        <el-input
            placeholder="请输入EF"
            prefix-icon="el-icon-search"
            v-model="EF">
          <template slot="prepend">EF</template>
        </el-input>
      </div></el-col>
    </el-row>

    <el-table
        v-if="data_isshow"
        :data="data"
        style="width: 100%">
      <el-table-column
          prop="UUCW"
          label="UUCW"
          width="180">
      </el-table-column>
      <el-table-column
          prop="UPC"
          label="UPC"
          width="180">
      </el-table-column>
      <el-table-column
          prop="UAW"
          label="UAW"
          width="180">
      </el-table-column>
      <el-table-column
          prop="UUCP"
          label="UUCP"
          width="180">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
// import axios from "axios";

import axios from "axios";

export default {
  name: "UCP",
  data () {
    return {
      data_isshow: false,
      data: [],
      TCF: "0.9",
      EF: "1.1"
    }
  },
  methods: {
    show(){
      this.success("文件上传成功");
      axios.get('/api/UCPMetrics').then(res => {
        let array=[];
        array.push(res.data.data);
        this.data=array;
        console.log(this.data);
      })
      this.data_isshow = true;
      this.success("文件度量成功");
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
.el-row {
  margin-bottom: 20px;
&:last-child {
   margin-bottom: 0;
 }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
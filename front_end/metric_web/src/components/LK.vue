<template>
  <div>
    <h1>LK</h1>
    <el-upload
        drag
        action=""
        :http-request="readAsText"
        accept=".xml"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传.xml文件</div>
    </el-upload>

    <el-table
        v-if="data_isshow"
        :data="resdata_json"
        style="width: 100%">
      <el-table-column
          prop="name"
          label="name"
          width="180">
      </el-table-column>
      <el-table-column
          prop="noa"
          label="noa"
          width="180">
      </el-table-column>
      <el-table-column
          prop="noo"
          label="noo"
          width="180">
      </el-table-column>
      <el-table-column
          prop="si"
          label="si"
          width="180">
      </el-table-column>
      <el-table-column
          prop="totalNumberOfAttr"
          label="totalNumberOfAttr"
          width="180">
      </el-table-column>
      <el-table-column
          prop="totalNumberOfMethod"
          label="totalNumberOfMethod"
          width="180">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
// import axios from "axios";

import axios from "axios";
import x2js from "x2js";

export default {
  name: "LK",
  data () {
    return {
      data_isshow: false,
      data: "",
      resdata_json: [],
    }
  },
  methods: {
    readAsText(params) {
      let file = params.file;
      let reader = new FileReader();
      reader.readAsText(file);
      reader.onload = function() {
        let text = reader.result;
        this.data=text;
        console.log(this.data);
        this.success("文件读取成功")
        axios.post(
            '/api//LKMetrics',
            this.data,
            {
                headers: {
                    'Content-Type': 'application/xml'
                }
            }
        ).then(response=>{
          console.log(response);

          var xml_str = response.data.toString();
          console.log(xml_str);
          const myx2json=new x2js(); //实例
          const json = myx2json.xml2js(xml_str)  //解析
          console.log(json);
          let that = this;
          this.resdata_json = [];
          json.message.lkResultList.result.forEach(function(item){
            console.log('this', this);
            console.log('that', that);
            that.resdata_json.push(item);
          })
          this.data_isshow=true;
        }).catch(error=>{
              console.log(error);
              this.warn("文件内容不支持")
              this.data_isshow = false
            });
      }.bind(this);
    },
    xmlToJson(xml) {
      // Create the return object
      var obj = {};

      if (xml.nodeType == 1) { // element
        // do attributes
        if (xml.attributes.length > 0) {
          obj["@attributes"] = {};
          for (var j = 0; j < xml.attributes.length; j++) {
            var attribute = xml.attributes.item(j);
            obj["@attributes"][attribute.nodeName] = attribute.nodeValue;
          }
        }
      } else if (xml.nodeType == 3) { // text
        obj = xml.nodeValue;
      }

      // do children
      if (xml.hasChildNodes()) {
        for(var i = 0; i < xml.childNodes.length; i++) {
          var item = xml.childNodes.item(i);
          var nodeName = item.nodeName;
          if (typeof(obj[nodeName]) == "undefined") {
            obj[nodeName] = this.xmlToJson(item);
          } else {
            if (typeof(obj[nodeName].push) == "undefined") {
              var old = obj[nodeName];
              obj[nodeName] = [];
              obj[nodeName].push(old);
            }
            obj[nodeName].push(this.xmlToJson(item));
          }
        }
      }
      return obj;
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
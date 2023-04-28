package com.sqrrow.controller;

import com.sqrrow.entity.CK;
import com.sqrrow.entity.CKNumber;
import com.sqrrow.entity.CKReport;
import com.sqrrow.entity.domain.*;
import com.sqrrow.metric.MetricCompute;
import com.sqrrow.util.LKUtil;
import com.sqrrow.util.cyComplexity.antlr4.CyclomaticComplexityVisitor;
import com.sqrrow.util.cyComplexity.grammar.*;
import com.sqrrow.util.cyComplexity.visulization.ChartPaint;
import io.swagger.annotations.Api;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;


import static com.sqrrow.util.WebUtil.falseReturn;
import static com.sqrrow.util.WebUtil.trueReturn;

/**
 * @ClassName UserController
 * @Description 关于用户的所有请求关系
 * @Author luojiarui
 * @Date 2022/6/4 9:38 上午
 * @Version 1.0
 **/
@Api(tags = "用户模块")
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/")
public class MetricController {

    @GetMapping("/CKJava")
    String calcCKJava(@RequestParam String path) {
        String filePath = "src/main/resources/codeToBeMetric/" + path;

        File file = new File(filePath);
        if (!file.exists()) return falseReturn("文件不存在");
        CKReport report = new CK().calculate(filePath);
        ArrayList<Map<String, Object>> ckNumbers = new ArrayList<>();
        for (CKNumber ckNumber: report.all()) {
            if (ckNumber.isError()) continue;
            ckNumbers.add(ckNumber.toMap());
        }
        return trueReturn("度量成功", ckNumbers);
    }

    @GetMapping("/CKXml")
    String calcCKXml(@RequestParam String path) {
        String filePath = "src/main/resources/codeToBeMetric/" + path;
        File file = new File(filePath);
        if (!file.exists()) return falseReturn("文件不存在");
        MetricCompute metricCompute = new MetricCompute();
        try {
            System.out.println(file.getAbsolutePath());
            List<XMLElement> classes = metricCompute.metricAnalyze(
                    new URL("file:" + file.getAbsolutePath())
            );
            ChartPaint.paint(classes, "类图的CK度量");
        } catch (Exception e) {
            System.out.println("解析出错");
            e.printStackTrace();
            return falseReturn(e.getMessage());
        }
        ArrayList<String> result = new ArrayList<>();
        result.add("software_metric_cbo.jpeg");
        result.add("software_metric_dit.jpeg");
        result.add("software_metric_noc.jpeg");
        result.add("software_metric_wmc.jpeg");
        return trueReturn("度量成功", result);
    }

    @PostMapping(value = "/LKMetrics", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public LKResponse LKMetrics(@RequestBody UMLXML model){
        //1. 存储映射<类名,类>,<接口名，接口>
        LKUtil.statClassAndInterface(model);

        //2. 记录一个类有多少直接的方法，即不包含父类的方法数
        LKUtil.countOwnedOperations(model);

        //3. 记录一个类有多少直接的属性，即不包含父类的属性
        LKUtil.statAttribute(model);

        //4. 进行深度优先搜索，得到CS
        LKUtil.countCS(model);

        //5. 计算继承的深度
        LKUtil.computeDepth(model);

        //6. 计算特征化指数
        LKUtil.computeSi(model);

        LKResponse lkResponse = new LKResponse();
        List<LKResult> lkResults = new ArrayList<LKResult>();
        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型
            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){
                PackagedElement packagedElement = model.getPackagedElements().get(i);
                LKResult lkResult = new LKResult();
                lkResult.setName(packagedElement.getName());
                lkResult.setTotalNumberOfMethod(packagedElement.getTotalNumberOfMethod());
                lkResult.setTotalNumberOfAttr(packagedElement.getTotalNumberOfAttr());
                lkResult.setNoo(packagedElement.getNoo());
                lkResult.setNoa(packagedElement.getNoa());
                lkResult.setSi(packagedElement.getSi());
                lkResults.add(lkResult);
            }
        }
        lkResponse.setLkResultList(lkResults);
        return lkResponse;
    }

    @GetMapping("/TraJava")
    String calcTraJava(@RequestParam String path) throws IOException {
        String filePath = "src/main/resources/codeToBeMetric/" + path;
        File file = new File(filePath);
        if (!file.exists()) return falseReturn("文件不存在");

        //代码行（LOC）度量
        long fileLength = file.length();
//        System.out.println("原长度为："+fileLength);
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        long numSkip = lnr.skip(fileLength);
//        System.out.println("skip长度为："+numSkip);
        int lines = lnr.getLineNumber();
//        System.out.println("line:"+lines);
        lnr.close();

        //圈复杂度（CC）度量
        JavaLexer lexer = new JavaLexer(new ANTLRFileStream(filePath));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParserRuleContext tree = parser.compilationUnit();
        CyclomaticComplexityVisitor mv = new CyclomaticComplexityVisitor();
        mv.visit(tree);

        //注释百分比（CP）
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("//")) {
                count++;
            } else if (line.startsWith("/**")) {
                count++;
                while (!br.readLine().startsWith("*/")) {
                    count++;
                }
                count++;
            }
        }
        //处理返回值
        Map<String, Object> result = new HashMap<>();
        result.put("loc", lines);
        result.put("cc", mv.getAvgCC());
        result.put("cp", (((float) count / (float) lines) * 100) + "%");
        return trueReturn("Traditional 度量成功",result);
    }

    @PostMapping("/upload")
    public @ResponseBody
    String upload(@RequestParam MultipartFile file, HttpServletRequest request){
        System.out.println("上传文件到目录");
        if(!file.isEmpty()){

            String path = "./src/main/resources/codeToBeMetric";
            String destPath = new File(path).getAbsolutePath();
            // 如果目录不存在则创建
            File uploadDir = new File(destPath);
            boolean isDirectoryCreated = uploadDir.exists();
            if (!isDirectoryCreated) {
                isDirectoryCreated = uploadDir.mkdir();
            }
            String OriginalFilename = file.getOriginalFilename();//获取原文件名
            //根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String fileName = System.currentTimeMillis()+OriginalFilename;
            File localFile = new File(destPath+"/"+fileName);
            try {
                file.transferTo(localFile); //把上传的文件保存至本地
                /*
                 * 这里应该把filename保存到数据库,供前端访问时使用
                 */
                return trueReturn("上传成功",fileName);
            }catch (IOException e){
                e.printStackTrace();
                return falseReturn("上传失败");
            }
        }else{
            System.out.println("文件为空");
            return falseReturn("文件为空");
        }
    }

    @GetMapping("/getImage")
    ResponseEntity<byte[]> getImage(@RequestParam String path) {
        String filePath = "src/main/resources/metriced_imgs/" + path;
        File file = new File(filePath);
        byte[] imageContent;
        if (file.exists()) {
            imageContent = fileToByte("src/main/resources/metriced_imgs/" + path);
        } else {
            imageContent = fileToByte("src/main/resources/images/FileNotFound.png");
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    byte[] fileToByte(String filePath) {
        File file = new File(filePath);
        byte[] data = null;
        try {
            InputStream inputStream = Files.newInputStream(file.toPath());
            data = new byte[(int) file.length()];
            int i = inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
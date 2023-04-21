package org.csu.metrics.web;

import org.csu.metrics.domain.XMLElement;
import org.csu.metrics.metric.MetricCompute;
import org.csu.metrics.util.cyComplexity.visulization.ChartPaint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.csu.metrics.web.WebUtil.falseReturn;
import static org.csu.metrics.web.WebUtil.trueReturn;

@RestController
public class CKXml {
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
            ChartPaint.paint(classes, "software_metric");
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

//    public static void main(String[] args) {
//        String result = new CKXml().calcCKXml("ck.xml");
//        System.out.println(result);
//    }
}

package com.sqrrow.util.cyComplexity.visulization;

import com.sqrrow.entity.domain.XMLElement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartPaint {

    private static void createBarChart(Map<String, Integer> data, String projectName, String index, String root) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.setValue(entry.getValue(), index, entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(projectName, index, "number", dataset, PlotOrientation.VERTICAL, true, true, false);
        //可以重新设置标题，替换“hi”标题
        chart.setTitle(new TextTitle(projectName, new Font("宋体", Font.BOLD + Font.ITALIC, 20)));
        //获得图标中间部分，即plot
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        //获得横坐标
        CategoryAxis categoryAxis = plot.getDomainAxis();
        //设置横坐标字体
        categoryAxis.setLabelFont(new Font("微软雅黑", Font.BOLD, 12));
        int count = dataset.getColumnCount();
        try {
            //图片是文件格式的，故要用到FileOutputStream用来输出。
            OutputStream os = new FileOutputStream(root + projectName + "_" + index + ".jpeg");
            ChartUtils.writeChartAsJPEG(os, chart, count * 100, 400);
            //关闭输出流
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void paint(List<XMLElement> classes, String projectName){
        Map<String, Integer> wmc = new HashMap<>();
        Map<String, Integer> dit = new HashMap<>();
        Map<String, Integer> noc = new HashMap<>();
        Map<String, Integer> cbo = new HashMap<>();

        for (XMLElement clazz: classes) {
            wmc.put(clazz.getName(), clazz.getWmc());
            dit.put(clazz.getName(), clazz.getDit());
            noc.put(clazz.getName(), clazz.getNoc());
            cbo.put(clazz.getName(), clazz.getCbo());
        }

        String savePath = "src/main/resources/metriced_imgs/";
        File dir = new File(savePath);
        if (!dir.exists()) {
            boolean success = dir.mkdir();
        }
        createBarChart(wmc, projectName, "wmc", savePath);
        createBarChart(dit, projectName, "dit", savePath);
        createBarChart(noc, projectName, "noc", savePath);
        createBarChart(cbo, projectName, "cbo", savePath);
    }
}

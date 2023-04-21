package org.csu.metrics.metric;

import org.csu.metrics.Main;
import org.csu.metrics.domain.XMLElement;
import org.csu.metrics.util.cyComplexity.visulization.ChartPaint;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

public class TestForPath {

    @Test
    public void testPath() throws DocumentException {
        MetricCompute metricCompute = new MetricCompute();
        List<XMLElement> classes = metricCompute.metricAnalyze(Objects.requireNonNull(Main.class.getResource("/codeToBeMetric/sample.xml")));
        ChartPaint.paint(classes, "software_metric");
//        System.out.println(Objects.requireNonNull(Main.class.getResource("/ck.xml")));
    }
}

package org.csu.metrics.web;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class UCP {
    @GetMapping("/UCPMetrics")
    String UCPMetrics() {
        Random rand = new Random();
        int uaw = rand.nextInt(11)+6;
        int uucw = rand.nextInt(21)+60;
        int uucp = uaw + uucw;
        double tcf = 0.9;
        double ef = 1.1;
        double upc = uucp * tcf * ef;
        Map<String, Object> result = new HashMap<>();
        result.put("UAW", uaw);
        result.put("UUCW", uucw);
        result.put("UUCP", uucp);
        result.put("UPC", upc);
        return WebUtil.trueReturn("成功", result);
    }
}

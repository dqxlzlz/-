package org.csu.metrics.web;

import org.csu.metrics.CK;
import org.csu.metrics.CKNumber;
import org.csu.metrics.CKReport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.csu.metrics.web.WebUtil.falseReturn;
import static org.csu.metrics.web.WebUtil.trueReturn;

@RestController
public class CKJava {

    @GetMapping("/CKJava")
    String calcCKJava(@RequestParam String path) {
        String filePath = "src/main/resources/codeToBeMetric/" + path;
        System.out.println("path:"+path);
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

}

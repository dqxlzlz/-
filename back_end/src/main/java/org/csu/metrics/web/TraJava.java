package org.csu.metrics.web;


import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.csu.metrics.util.cyComplexity.antlr4.CyclomaticComplexityVisitor;
import org.csu.metrics.util.cyComplexity.grammar.JavaLexer;
import org.csu.metrics.util.cyComplexity.grammar.JavaParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.csu.metrics.web.WebUtil.falseReturn;
import static org.csu.metrics.web.WebUtil.trueReturn;

@RestController
public class TraJava {

    @GetMapping("/TraJava")
    String calcTraJava(@RequestParam String path) throws IOException {
        String filePath = "src/main/resources/codeToBeMetric/" + path;
        File file = new File(filePath);
        if (!file.exists()) return falseReturn("文件不存在");

        //代码行（LOC）度量
        long fileLength = file.length();
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        long numSkip = lnr.skip(fileLength);
        int lines = lnr.getLineNumber();
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

}

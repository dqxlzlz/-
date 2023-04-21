package cn.edu.csu.smproject.Service;

import cn.edu.csu.smproject.domain.Code;

import java.io.*;
import java.util.ArrayList;

/**
 * 代码行数统计
 */

public class CodeCounterUtil {

    private long files = 0;
    private long codeLines = 0;
    private long commentLines = 0;
    private long blankLines = 0;
    private ArrayList<File> fileArray = new ArrayList<File>();
    private  ArrayList<Code> codes = new ArrayList<Code>();
    /**
     * 代码行数统计
     */
    public  ArrayList<Code> countCode(String path) {
//                String file = CodeCounter.class.getResource("/").getFile();
//                System.out.println("fileName："+file);
//                String path = file.replace("testcode", "src");
//                System.out.println("pathName："+path);
        codes.clear();
        System.out.println("path："+path);

        ArrayList<File> al = getFile(new File(path));
        for (File f : al) {
            if (f.getName().matches(".*\\.java$")){ // 匹配java格式的文件
                count(f);
                System.out.println(f);
            }
        }
        System.out.println("统计文件：" + files);
        System.out.println("代码行数：" + codeLines);
        System.out.println("注释行数：" + commentLines);
        System.out.println("空白行数：" + blankLines);

        return codes;
    }

    /**
     * 获得目录下的文件和子目录下的文件
     * @param f
     * @return
     */
    public  ArrayList<File> getFile(File f) {
        File[] ff = f.listFiles();
        for (File child : ff) {
            if (child.isDirectory()) {
                getFile(child);
            } else
                fileArray.add(child);
        }
        return fileArray;

    }

    /**
     * 统计方法
     * @param f
     */
    private  void count(File f) {
        //设置一个code
        Code code = new Code();
        code.setFileName(f.getName());

        BufferedReader br = null;
        boolean flag = false;
        try {
            br = new BufferedReader(new FileReader(f));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.trim(); // 除去注释前的空格
                if (line.matches("^[ ]*$")) { // 匹配空行
                    blankLines++;
                    code.setBlankLines(code.getBlankLines()+1);
                } else if (line.startsWith("//")) {
                    commentLines++;
                    code.setCommentLines(code.getCommentLines()+1);
                } else if (line.startsWith("/*") && !line.endsWith("*/")) {
                    commentLines++;
                    code.setCommentLines(code.getCommentLines()+1);
                    flag = true;
                } else if (line.startsWith("/*") && line.endsWith("*/")) {
                    commentLines++;
                    code.setCommentLines(code.getCommentLines()+1);
                } else if (flag == true) {
                    commentLines++;
                    code.setCommentLines(code.getCommentLines()+1);
                    if (line.endsWith("*/")) {
                        flag = false;
                    }
                } else {
                    codeLines++;
                    code.setCodeLines(code.getCodeLines()+1);
                }
            }
            files++;

            codes.add(code);
            System.out.println(code);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

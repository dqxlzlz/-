package org.csu.metrics.web;

import org.jfree.data.json.impl.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
public class WebUtil {
    public static String trueReturn(String msg, Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("msg", msg);
        result.put("data", data);
        return JSONObject.toJSONString(result);
    }

    public static String falseReturn(String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("msg", msg);
        return JSONObject.toJSONString(result);
    }

    /**
     * 1.文件保存在服务器，url地址保存在数据库
     * 上传成功之后返回成功保存的url地址
     */
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

    public static void main(String[] args) {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "csu");
        result.put("age", 18);
        String test = WebUtil.trueReturn("成功", result);
        System.out.println(test);

        ArrayList<String> list = new ArrayList<>();
        list.add("a"); list.add("b"); list.add("c");
        test = WebUtil.trueReturn("成功", list);
        System.out.println(test);
    }
}

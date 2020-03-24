package cn.gson.oasys.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/")
public class ComController {

    @Value("${attachment.processpath}")
    private String propath;



    public  boolean uploadFilecom(byte[] file,String filePath,String fileName){
        //默认文件上传成功
        boolean flag = true;
        //new一个文件对象实例
        File targetFile = new File(propath,filePath);
        //如果当前文件目录不存在就自动创建该文件或者目录
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        try{
            //通过文件流实现文件上传
            FileOutputStream fileOutputStream = new FileOutputStream( propath+"/"+filePath+"/"+ fileName);
            fileOutputStream.write(file);
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch(FileNotFoundException e){
            System.out.println("文件不存在异常");
            flag = false;
        }catch(IOException ioException){
            System.out.println("javaIO流异常");
            flag = false;
        }
        return flag;
    }

    @RequestMapping("downfilecom")
    public  boolean downfilecom (String filepath, @RequestParam(value = "Src")String src,
                                       HttpServletResponse response, HttpServletRequest request)  {
        //默认文件上传成功
        boolean flag = true;
       /* if (src!=null && !src.equals("")) {
            String[] split = src.split(";");
            for (int i = 0; i < split.length; i++) {
                String src1 =split[i];
                HttpServletResponse response =responses;*/

        try {
            filepath =src;
            //new一个文件对象实例
            File targetFile = new File(propath,filepath);
            //取文件名
            String filename = targetFile.getName();
            int falg = 0;
            if(!targetFile.exists()){
                System.out.println("获取序列文件出错，请检查！");
            }
            else{
                System.out.println("没有问题");
            }

            if (falg==0){
//yuan

                // 清空response
                response.reset();

                String userAgent = request.getHeader("User-agent");
			/*if(agent.indexOf("Firefox")!=-1) {
				response.addHeader("content-Disposition", "attachment;fileName==?UTF-8?B?"+new String(Base64.encodeBase64(filename.getBytes("utf-8")))+"?=");
			}else if(agent.indexOf("Edge")!=-1) {
				response.addHeader("content-Disposition", "attachment;fileName="+URLEncoder.encode(filename, "utf-8"));
			}*/

                // 针对IE或者以IE为内核的浏览器：
                if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                    filename = java.net.URLEncoder.encode(filename, "UTF-8");
                } else {
                    // 非IE浏览器的处理：
                    filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
                }
                // 设置response的Header
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
                response.addHeader("Content-Length", "" + targetFile.length());
                //xian
// 以流的形式下载文件。
                FileInputStream fileInputStream = new FileInputStream(propath+"/"+filepath);
                InputStream fis = new BufferedInputStream(fileInputStream);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();

                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
                flag = true;
                fileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }
          /*  }
        }*/

        return  flag;
    }
}

package com.e3mall.controller;

import com.e3mall.commom.utils.FastDFSClient;
import com.e3mall.commom.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaomeng
 * @Description:图片上传的controller
 * @date 2018/10/7 11:01
 */
@Controller
public class PictureController {
    @Value("${IMAGE_SERVER_URL}")
    private  String IMAGE_SERVER_URL;

    @RequestMapping(value="/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        //把图片上传到图片服务器
        try {
            FastDFSClient client=new FastDFSClient("classpath:/conf/client.conf");
            //获得一个图片的地址的名字,首先获得扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extString = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            String fileUrl = client.uploadFile(uploadFile.getBytes(), extString);
            //补充成完整的url
           String url=IMAGE_SERVER_URL+fileUrl;
           Map map=new HashMap<>();
           map.put("error",0);
           map.put("url",url);
           return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            Map map=new HashMap<>();
            map.put("error",1);
            map.put("message","图片上传失败");
            return JsonUtils.objectToJson(map);
        }
    }
}

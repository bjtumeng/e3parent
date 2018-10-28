package com.e3mall.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaomeng
 * @Description:生成静态页面测试controller
 * ResponseBody:对象的话返回json数据,字符串的话返回字符串
 * @date 2018/10/24 23:37
 */
@Controller
public class HtmlGenController {
    @Autowired
    private FreeMarkerConfigurer config;
    @RequestMapping("/genHtml")
    @ResponseBody
    public String genHtml() throws Exception {
        Configuration configuration = config.getConfiguration();
        //加载模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //创建一个数据集
        Map data =new HashMap();
        data.put("hello","hello freemarker!");
        //指定文件输出路径及文件名
        Writer out =new FileWriter(new File("E:\\JavaEE-32\\freemarker\\hello2.html"));
        //输出文件
        template.process(data,out);
        //关闭流
        out.close();
        return "ok";
    }
}

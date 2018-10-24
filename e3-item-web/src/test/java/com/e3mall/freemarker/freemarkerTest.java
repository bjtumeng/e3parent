package com.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/22 23:32
 */
public class freemarkerTest {
    @Test
    public void testFreeMarker() throws Exception {
        //1.创建一个模板文件
        //2.创建一个Configuration对象
        Configuration configuration=new Configuration(Configuration.getVersion());
        //3.设置模板文件保存目录
        configuration.setDirectoryForTemplateLoading(new File("E:\\e3parent\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //4.模板文件的编码格式,一般是UTF-8
        configuration.setDefaultEncoding("UTF-8");
        //5.加载一个模板文件,创建一个模板对象
//        Template template = configuration.getTemplate("hello.ftl");
          Template template = configuration.getTemplate("student.ftl");
        //6.创建一个数据集,可以是pojo也可以是map,推荐map
        Map data=new HashMap();
        data.put("hello","hello freemarker");
        //创建POJO对象
        Student student=new Student(1,"张三",12,"回龙观");
        data.put("student",student);
        List<Student> studentList=new ArrayList<>();
        studentList.add(new Student(1,"张三",12,"回龙观"));
        studentList.add(new Student(2,"张二",13,"回龙观1"));
        studentList.add(new Student(3,"张四",14,"回龙观2"));
        //list类型测试
        data.put("studentList",studentList);
        //date类型测试
        data.put("date",new Date());
        //null类型测试
        data.put("val","3");
        //7.创建一个Writer对象,指定输出文件的路径和文件名
//        Writer out=new FileWriter(new File("E:\\JavaEE-32\\freemarker\\hello.txt")) ;
        Writer out=new FileWriter(new File("E:\\JavaEE-32\\freemarker\\student.html")) ;
        //8.生成静态页面
        template.process(data,out);
        //9.关闭流
        out.close();
    }
}

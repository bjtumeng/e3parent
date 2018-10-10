package com.e3mall;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/9 21:18
 */
public class PublicTest {
    @Test
    public void publicService() throws Exception{
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-*.xml");
        while(true){
            Thread.sleep(1000);
        }
    }
}

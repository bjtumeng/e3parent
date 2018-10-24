package com.e3mall.solrJ.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/21 21:13
 */
public class MessageConsumer {

    @Test
    public void msgConsumerTest() throws Exception{
        //初始化Spring容器
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext
                ("classpath:/spring/applicationContext-activemq.xml");
        //等待
        System.in.read();
    }
}

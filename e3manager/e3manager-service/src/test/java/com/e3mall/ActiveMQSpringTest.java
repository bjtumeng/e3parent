package com.e3mall;

import org.apache.log4j.net.JMSAppender;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


/**activemq与spring整合开发
 * @author zhaomeng
 * @Description:
 * @date 2018/10/21 14:42
 */
public class ActiveMQSpringTest {
    @Test
    public void sendMessage() throws  Exception{
        //初始化Spring容器
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext
                ("classpath:spring/applicationContext-activemq.xml");
        //从容器中获得JmsTemplate对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //从容器中获得Destination对象
        Destination queueDestination = (Destination) applicationContext.getBean("queueDestination");
        //发送消息
        jmsTemplate.send(queueDestination,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("send activemq message");
            }
        });
    }
}

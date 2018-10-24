package com.e3mall;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/20 17:00
 */
public class ActiveMQTopic {
    /**
     * 消息订阅模式
      * @throws Exception
     */
    @Test
    public void activeMqTopicTest() throws  Exception{
        //1.创建一个连接工厂对象,需要指定服务的IP及端口
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://116.85.25.168:61616");
        //2.使用工厂对象创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //3,开启连接
        connection.start();
        //4.创建一个session对象,
        // 第一个参数:是否开启事务,消息发送失败,是否重发,与分布式事务结合使用,一般不开启事务
        // 第二个参数:如果开启事务,第二个参数没意义,应答模式,分为自动应答和手动应答,一般是自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个destination对象,两种形式queue和topic
        Topic topic = session.createTopic("test-topic");
        //6.使用session对象创建一个producer对象
        MessageProducer producer = session.createProducer(topic);
        //7.创建一个Message对象,可以使用textmessage
//        TextMessage message =new ActiveMQTextMessage();
//        message.setText("hello activemq");
        TextMessage message = session.createTextMessage("topic message");
        //8.发送消息
        producer.send(message);
        //9.关闭对象
        producer.close();
        session.close();
        connection.close();
    }
    @Test
    public void activeMqTopicConsumer() throws Exception {
        //创建ConnectionFactory对象连接MQ服务器
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://116.85.25.168:61616");
        //创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用连接对象创建Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个destination对象,queue对象
        Topic topic = session.createTopic("test-topic");
        //使用session对象创建一个消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                String text = null;
                try {
                    text = textMessage.getText();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                //打印结果
                System.out.println(text);
            }
        });
        System.out.println("消费者3已启动.......");
        //等待敲击键盘
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}

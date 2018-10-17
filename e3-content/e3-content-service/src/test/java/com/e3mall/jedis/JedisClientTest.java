package com.e3mall.jedis;

import com.e3mall.commom.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/13 10:21
 */
public class JedisClientTest {
    @Test
    public void testJedisClient(){
        //加载Spring容器
        ApplicationContext  applicationContext=new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-redis.xml");
        //写接口类,可以根据配置文件的不同,自由切换实现类
        JedisClient jedis =(JedisClient) applicationContext.getBean(JedisClient.class);

        jedis.set("test","12345");
        String test = jedis.get("test");
        System.out.println(test);
    }
}

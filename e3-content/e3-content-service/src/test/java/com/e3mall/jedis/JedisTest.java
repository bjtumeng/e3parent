package com.e3mall.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/12 22:33
 */
public class JedisTest {
    @Test
    public void JedisTest(){
        //创建Jedis对象,参数分为是host和port
        Jedis jedis=new Jedis("116.85.25.168",6379);
        //jedis设置密码
        jedis.auth("Aa6818678");
        //直接使用Jedis操作redis
        jedis.set("test123","my first Jedis test");
        String s = jedis.get("test123");
        System.out.println(s);
        //关闭连接
        jedis.close();
    }
    @Test
    public void testJedisPool(){
        //创建连接池对象,两个参数,host和port
        JedisPool jedisPool=new JedisPool("116.85.25.168",6379);
        //从连接池中获得jedis对象
        Jedis jedis = jedisPool.getResource();
        jedis.auth("Aa6818678");
        //操作jedis对象
        String s = jedis.get("test123");
        System.out.println(s);
        //关闭连接
        jedis.close();
        jedisPool.close();
    }
    @Test
    public void  testJedisCluster(){
        Set<HostAndPort> set =new HashSet<>();
        set.add(new HostAndPort("116.85.25.168",7001));
        set.add(new HostAndPort("116.85.25.168",7002));
        set.add(new HostAndPort("116.85.25.168",7003));
        set.add(new HostAndPort("116.85.25.168",7004));
        set.add(new HostAndPort("116.85.25.168",7005));
        set.add(new HostAndPort("116.85.25.168",7006));
        JedisCluster jedisCluster=new JedisCluster(set);
        jedisCluster.set("test","123");
        String s = jedisCluster.get("test");
        System.err.print(s);
        jedisCluster.close();
    }
}

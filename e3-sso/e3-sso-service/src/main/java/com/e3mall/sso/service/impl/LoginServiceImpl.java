package com.e3mall.sso.service.impl;

import com.e3mall.commom.jedis.JedisClient;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.commom.utils.JsonUtils;
import com.e3mall.mapper.TbUserMapper;
import com.e3mall.pojo.TbUser;
import com.e3mall.pojo.TbUserExample;
import com.e3mall.sso.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author zhaomeng
 * @Description:用户登录处理
 * @date 2018/10/27 22:09
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper mapper;
    @Autowired
    private JedisClient client;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    @Override
    public E3Result userLogin(String username, String password) {
        //检验用户名和密码的正确性
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = mapper.selectByExample(example);
        if(list==null||list.size()==0){
            return E3Result.build(400,"用户名或密码错误");
        }
        //取用户信息
        TbUser user = list.get(0);
        //判断密码是否正确
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes())) ){
            return E3Result.build(400,"用户名或密码错误");
        }
        //生成uuid
        String token= UUID.randomUUID().toString();
        //把用户信息写入redis
        user.setPassword(null);
        client.set("SESSION"+token, JsonUtils.objectToJson(user));
        //设置session的过期时间
        client.expire("SESSION"+token,SESSION_EXPIRE);
        return E3Result.ok(token);
    }
}

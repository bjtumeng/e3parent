package com.e3mall.sso.service.impl;

import com.e3mall.commom.jedis.JedisClient;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.commom.utils.JsonUtils;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @author zhaomeng
 * @Description:根据token取用户信息
 * @date 2018/10/28 9:52
 */
@Service
public class TokenServiceImpl implements TokenService {
  @Autowired
  private JedisClient client;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    @Override
    public E3Result getUserInfoByToken(String token) {
        //从redis中查询用户信息
        String userInfo = client.get("SESSION"+token);
        if (StringUtils.isBlank(userInfo)){
            //取不到用户信息,登录过期,返回E3Result
            return E3Result.build(201,"session过期");
        }
        //取到用户信息,更新token的过期时间
        client.expire(token,SESSION_EXPIRE);
        TbUser user = JsonUtils.jsonToPojo(userInfo, TbUser.class);
        return E3Result.ok(user);
    }
}

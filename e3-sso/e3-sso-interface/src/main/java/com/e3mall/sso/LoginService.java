package com.e3mall.sso;

import com.e3mall.commom.utils.E3Result;

/**
 * @author zhaomeng
 * @Description:用户登录接口
 * @date 2018/10/27 22:00
 */
public interface LoginService {
    /**
     * 业务逻辑:
     * 1.判断用户名和密码正确性
     * 2.如果不正确,返回登录失败
     * 3.如果正确,生成token
     * 4.把用户信息写入redis,其中key:token,value:用户信息
     * 5.设置session的过期时间
     * 6.返回token到表现层
     * 返回值是:E3Result,其中包含token信息
     */
    E3Result userLogin(String username,String password);
}

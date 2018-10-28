package com.e3mall.sso;

import com.e3mall.commom.utils.E3Result;

/**
 * @author zhaomeng
 * @Description:根据token查询用户信息
 * @date 2018/10/28 9:50
 */
public interface TokenService {
    E3Result getUserInfoByToken(String token);
}

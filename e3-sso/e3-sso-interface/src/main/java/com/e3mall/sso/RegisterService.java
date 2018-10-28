package com.e3mall.sso;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.pojo.TbUser;

import java.text.ParseException;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/27 19:39
 */
public interface RegisterService {
    E3Result checkData(String param,int type);
    E3Result register(TbUser user) throws Exception;
}

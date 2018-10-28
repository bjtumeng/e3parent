package com.e3mall.sso.controller;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.commom.utils.JsonUtils;
import com.e3mall.sso.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhaomeng
 * @Description:根据token查询用户信息Controller
 * @date 2018/10/28 10:08
 */
@Controller
public class TokenController {
    @Autowired
    private TokenService service;

//    @RequestMapping(value="/user/token/{token}",produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String getUserInfoByToken(@PathVariable String token,String callback){
//        E3Result result = service.getUserInfoByToken(token);
//        if(StringUtils.isNotBlank(callback)){
//            return callback+"("+ JsonUtils.objectToJson(result)+");";
//        }
//        return JsonUtils.objectToJson(result);
//    }
    @RequestMapping(value="/user/token/{token}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getUserInfoByToken(@PathVariable String token,String callback){
        E3Result result = service.getUserInfoByToken(token);
        if(StringUtils.isNotBlank(callback)) {
            MappingJacksonValue value = new MappingJacksonValue(result);
            value.setJsonpFunction(callback);
            return value;
        }
        return result;
    }
}

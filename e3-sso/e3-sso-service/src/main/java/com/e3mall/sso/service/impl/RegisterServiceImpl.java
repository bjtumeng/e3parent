package com.e3mall.sso.service.impl;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.mapper.TbUserMapper;
import com.e3mall.pojo.TbUser;
import com.e3mall.pojo.TbUserExample;
import com.e3mall.sso.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/27 19:41
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private TbUserMapper mapper;

    /**
     * 根据用户名和手机号到数据库查重
     * @param param
     * @param type
     * @return
     */
    @Override
    public E3Result checkData(String param, int type) {
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (type==1){
            criteria.andUsernameEqualTo(param);
        }else if (type==2){
            criteria.andPhoneEqualTo(param);
        }else if (type==3){
            criteria.andEmailEqualTo(param);
        }else{
            return E3Result.build(400,"数据类型错误");
        }
        List<TbUser> list = mapper.selectByExample(example);
        if (list!=null&&list.size()>0){
            return E3Result.ok(false);
        }
        return E3Result.ok(true);
    }

    @Override
    /**
     * 把前台注册数据插入数据库
     */
    public E3Result register(TbUser user) throws Exception {
        //数据有效性校验
        if("".equals(user.getUsername())||"".equals(user.getPhone())||"".equals(user.getPassword())){
            return E3Result.build(400,"用户数据不完整,注册失败");
        }
        E3Result result = checkData(user.getUsername(), 1);
        if (result.getData()==false){
            return E3Result.build(400,"用户名重复,注册失败");
        }
        E3Result resultPhone = checkData(user.getPhone(), 2);
        if (resultPhone.getData()==false){
            return E3Result.build(400,"手机重复,注册失败");
        }
        //补全pojo属性
        Date date =new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        Date parse = simpleDateFormat.parse(format);
        user.setCreated(parse);
        user.setUpdated(parse);
        //对密码进行MD5加密
        String usernameMd5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(usernameMd5);
        int insert = mapper.insert(user);
        return E3Result.ok();
    }
}

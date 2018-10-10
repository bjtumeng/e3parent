package com.e3mall;

import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/5 15:52
 */
public class PageHelperTest {
    @Test
    public void pageHelperTest(){
        //初始化Spring容器
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-dao.xml");
        //从容器中获得mapper的代理对象
        TbItemMapper bean = (TbItemMapper) context.getBean(TbItemMapper.class);
        //执行sql语句之前设置分页信息使用pagehelper的startpage方法
        PageHelper.startPage(1,10);
        //执行查询
        TbItemExample example=new TbItemExample();
        List<TbItem> tbItemList = bean.selectByExample(example);
        //取分页信息,pageinfo
        // 1.总记录数2.总页数
        PageInfo<TbItem> pageInfo=new PageInfo<>(tbItemList);
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getPages());
        System.out.println(tbItemList.size());
    }
}

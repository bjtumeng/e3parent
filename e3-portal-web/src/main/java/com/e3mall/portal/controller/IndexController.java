package com.e3mall.portal.controller;

import com.e3mall.content.service.ContentService;
import com.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 首页展示controller
 * @author zhaomeng
 * @Description:
 * @date 2018/10/9 20:12
 */
@Controller
public class IndexController {
     @Autowired
   private ContentService service;
     @Value("${CONTENT_LUNBO_ID}")
     private Long cid;
     @RequestMapping("/index")
    public String showIndex(Model model){
         List<TbContent> ad1List =  service.getContentListByCid(cid);
         model.addAttribute("ad1List",ad1List);
         System.out.println(model);
         return "index";
     }


}

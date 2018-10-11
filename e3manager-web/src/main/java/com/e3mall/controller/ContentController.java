package com.e3mall.controller;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.content.service.ContentService;
import com.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/10 22:50
 */
@Controller
public class ContentController {
     @Autowired
    private ContentService service;
     @RequestMapping(value = "/content/save",method = RequestMethod.POST)
     @ResponseBody
    public E3Result addContent(TbContent content) throws ParseException {
         E3Result result = service.addContent(content);
         return result;

     }
}

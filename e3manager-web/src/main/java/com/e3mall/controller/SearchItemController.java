package com.e3mall.controller;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/15 8:28
 */
@Controller
public class SearchItemController {
     @Autowired
    private SearchItemService service;
     @RequestMapping("/index/item/import")
     @ResponseBody
    public E3Result importItemList(){
         E3Result result = service.importAllItems();
         return result;
     }
}

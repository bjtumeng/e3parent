package com.e3mall.search.controller;

import com.e3mall.commom.pojo.SearchResult;
import com.e3mall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * @author zhaomeng
 * @Description:商品搜索controller
 * @date 2018/10/18 19:05
 */
@Controller
public class SearchController {
     @Autowired
    private SearchService service;
     @Value("${SEARCH_RESULT_ROWS}")
     private int rows;
     @RequestMapping("/search")
    public String searchItemList(String keyword, @RequestParam(defaultValue = "1") Integer page
                                  ,Model model) throws Exception {
         keyword=new String(keyword.getBytes("iso-8859-1"),"utf-8");
         SearchResult result = service.search(keyword, page, rows);
         model.addAttribute("page",page);
         model.addAttribute("query",keyword);
         model.addAttribute("itemList",result.getItemList());
         model.addAttribute("recourdCount",result.getRecordCount());
         model.addAttribute("totalPages",result.getTotalPages());
         //异常测试
         return "search";
     }




}

package com.e3mall.controller;

import com.e3mall.commom.pojo.EasyUITreeNode;
import com.e3mall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**内容分类管理
 * @author zhaomeng
 * @Description:
 * @date 2018/10/9 23:07
 */
@Controller
public class ContentCatController {
   @Autowired
   private ContentCategoryService categoryService;
   @RequestMapping("/content/category/list")
   @ResponseBody
   public List<EasyUITreeNode> getContentCatList(
           @RequestParam(name="id",defaultValue = "0") Long parentId){
       List<EasyUITreeNode> catList = categoryService.getCatList(parentId);
       return catList;
   }
}

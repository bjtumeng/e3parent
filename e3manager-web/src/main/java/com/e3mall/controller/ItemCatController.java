package com.e3mall.controller;

import com.e3mall.commom.pojo.EasyUITreeNode;
import com.e3mall.service.ItemCatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zhaomeng
 * @Description: 商品分类Controller
 * @date 2018/10/6 11:12
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService service;
    /**
     *  初始化tree控件请求的url
     */
    @RequestMapping(value="/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getNodeList(@RequestParam(name="id",defaultValue = "0")
                                                        Long parentId){
        List<EasyUITreeNode> nodeList = service.getItemCatList(parentId);
        return nodeList;
    }
}

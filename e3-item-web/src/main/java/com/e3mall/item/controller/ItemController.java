package com.e3mall.item.controller;

import com.e3mall.item.pojo.Item;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**商品详情页面展示
 * @author zhaomeng
 * @Description:
 * @date 2018/10/22 20:03
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService service;

    @RequestMapping("/item/{itemId}")
    public String getTbItemAndDesc(@PathVariable Long itemId, Model model){
        TbItem tbItem = service.selectItemById(itemId);
        Item item=new Item(tbItem);
        TbItemDesc itemDesc = service.getTbItemDescById(itemId);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        return  "item";
    }

}

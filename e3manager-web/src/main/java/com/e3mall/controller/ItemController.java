package com.e3mall.controller;

import com.e3mall.commom.pojo.EasyUIDataGridResult;
import com.e3mall.commom.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.pojo.TbItem;
import com.e3mall.service.ItemService;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.selectItemById(itemId);
		ArrayList<String> list = new ArrayList<>();
		return tbItem;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		EasyUIDataGridResult list = itemService.getItemList(page, rows);
		return list;
	}
	@RequestMapping(value="/item/save",method = RequestMethod.POST)
	@ResponseBody
	public E3Result addTbItem(TbItem item, String desc) throws Exception {
		E3Result result = itemService.addTbItem(item, desc);
		return result;
	}

	/**
	 * 删除商品
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/rest/item/delete",method = RequestMethod.POST)
	@ResponseBody
	public E3Result deleteItem(Long[] ids) throws Exception {
		E3Result result = itemService.deleteTbItem(ids);
		return result;
	}

	/**
	 * 下架商品
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/rest/item/instock",method = RequestMethod.POST)
	@ResponseBody
	public E3Result instockItem(Long[] ids) throws Exception {
		E3Result result = itemService.instockTbItem(ids);
		return result;
	}

	/**
	 * 上架商品
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/rest/item/reshelf",method = RequestMethod.POST)
	@ResponseBody
	public E3Result reshelfItem(Long[] ids) throws Exception {
		E3Result result = itemService.reshelfTbItem(ids);
		return result;
	}
}

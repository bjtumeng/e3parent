package com.e3mall.item.pojo;

import com.e3mall.pojo.TbItem;

/**
 * @author zhaomeng
 * @Description:继承TbItem类,直接在TbItem类中增加方法不好,因为TbItem属于其他工程
 * @date 2018/10/22 19:20
 */
public class Item extends TbItem {
    public Item(TbItem tbItem){
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
        this.setId(tbItem.getId());
    }
    public String[] getImages(){
        String image = this.getImage();
        if (image!=null&"".equals(image)){
            String[] strings = image.split(",");
            return strings;
        }else{
            return null;
        }
    }
}

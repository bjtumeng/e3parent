package com.e3mall.cart.service.impl;

import com.e3mall.cart.service.CartService;
import com.e3mall.commom.jedis.JedisClient;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.commom.utils.JsonUtils;
import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**购物车处理服务
 * @author zhaomeng
 * @Description:
 * @date 2018/10/29 19:52
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisClient client;
   @Autowired
   private TbItemMapper mapper;
   @Value("${REDIS_CART_PRE}")
   private String REDIS_CART_PRE;
    @Override
    public E3Result addCart(Long userId, Long itemId, int num) {
        //向redis中添加购物车
        String json = client.hget(REDIS_CART_PRE+":"+userId, itemId+"");
        //判断商品是否存在
        if (json!=null){
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            //如果存在商品数量相加
            Integer num1 = item.getNum();
            item.setNum(num1+num);
            //写回到redis中
            String objectToJson = JsonUtils.objectToJson(item);
            client.hset(REDIS_CART_PRE+":"+userId, itemId+"",objectToJson);
            return E3Result.ok();
        }
        //如果不存在,根据商品id取商品信息
        TbItem tbItem = mapper.selectByPrimaryKey(itemId);
        tbItem.setNum(num);
        String image = tbItem.getImage();
        if (StringUtils.isNotBlank(image)){
            String[] split = image.split(",");
            tbItem.setImage(split[0]);
        }
        String tbItemToJson = JsonUtils.objectToJson(tbItem);
        //添加购物车列表
        client.hset(REDIS_CART_PRE+":"+userId, itemId+"",tbItemToJson);
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(Long userId, List<TbItem> itemList) {
        //遍历商品列表
        //添加到购物车
        //判断购物车是否有此商品,如果有,数量相加,如果没有,添加新的商品
        for (TbItem item:itemList) {
            addCart(userId,item.getId(),item.getNum());
        }
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(Long userId) {
        List<String> list = client.hvals(REDIS_CART_PRE+":"+userId);
        List<TbItem> itemList=new ArrayList<>();
        for (String json:list) {
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public E3Result updateCartNum(Long userId, Long itemId, int num) {
        String json = client.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
        item.setNum(num);
        String toJson = JsonUtils.objectToJson(item);
        client.hset(REDIS_CART_PRE + ":" + userId, itemId + "",toJson);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(Long userId, Long itemId) {
        client.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
        return E3Result.ok();
    }

    @Override
    public E3Result clearCartItem(Long userId) {
        client.del(REDIS_CART_PRE + ":" + userId);
        return E3Result.ok();
    }
}

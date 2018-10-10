package com.e3mall.commom.pojo;

import java.io.Serializable;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/6 10:34
 */
public class EasyUITreeNode implements Serializable {
    private  long id;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

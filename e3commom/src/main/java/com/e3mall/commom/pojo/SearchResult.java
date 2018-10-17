package com.e3mall.commom.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/17 22:38
 */
public class SearchResult implements Serializable {
    private  long recordCount;
    private int totalPages;
    private List<SearchItem> itemList;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}

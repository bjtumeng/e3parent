package com.e3mall.commom.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/5 17:17
 */
public class EasyUIDataGridResult implements Serializable {
      private long total;
      private List rows;

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


}

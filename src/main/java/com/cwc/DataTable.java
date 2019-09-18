package com.cwc;

import java.io.Serializable;

/**
 * @author bwh
 * @date 2019/9/18/018 - 11:06
 * @Description
 */
public class DataTable   {
    private Object data;
    private String msg;
    private int code;

    public DataTable(Object data) {
        this.data = data;
    }
}

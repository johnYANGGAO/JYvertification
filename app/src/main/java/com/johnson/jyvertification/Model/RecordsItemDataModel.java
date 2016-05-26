package com.johnson.jyvertification.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsmac on 4/12/16.
 */
public class RecordsItemDataModel extends BaseBean implements Serializable{

    private int Total;
    private int  Next_id;
    /**
     * JSON.parseArray(jsonString, cls);
     * */
    private List<RecordsItemModel> Data=new ArrayList<>();

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getNext_id() {
        return Next_id;
    }

    public void setNext_id(int next_id) {
        Next_id = next_id;
    }

    public List<RecordsItemModel> getData() {
        return Data;
    }

    public void setData(List<RecordsItemModel> data) {
        Data = data;
    }
}

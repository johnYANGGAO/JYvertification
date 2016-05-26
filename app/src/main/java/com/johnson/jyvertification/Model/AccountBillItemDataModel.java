package com.johnson.jyvertification.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsmac on 4/14/16.
 */
public class AccountBillItemDataModel {
    /** "Data": [
     {

     },
     {
     }
     ],
     "TotalData": {
     },
     "Total": 1,
     "Next_id": 1,
     "Result": 0,
     "Exception": "操作成功"

     */
    private int Total;
    private int  Next_id;
    private AccountBillItemModel TotalData;
    private List<AccountBillItemModel> Data=new ArrayList<>();

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

    public AccountBillItemModel getTotalData() {
        return TotalData;
    }

    public void setTotalData(AccountBillItemModel totalData) {
        TotalData = totalData;
    }

    public List<AccountBillItemModel> getData() {
        return Data;
    }

    public void setData(List<AccountBillItemModel> data) {
        Data = data;
    }
}

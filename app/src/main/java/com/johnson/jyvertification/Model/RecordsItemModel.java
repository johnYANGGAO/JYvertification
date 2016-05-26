package com.johnson.jyvertification.Model;

import java.io.Serializable;

/**
 * Created by johnsmac on 4/11/16.
 */
public class RecordsItemModel extends  BaseBean implements Serializable {

    /*
    * {
  "Data": [
   {
    },
    {

    }
  ],
  "Total": 1,
  "Next_id": 1,
  "Result": 0,
  "Exception": "操作成功"
}
    * */
    private String ID;
    private String CompanyID;
    private String ExpenseUserID;
    private String IP;
    private String Module;
    private String BankCardNo;
    private String IDCardNo;
    private String UserName;
    private String ResultCode;
    private String Result;
    private String InsertDate;
    private int ModuleType;
    private float Fee;

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getExpenseUserID() {
        return ExpenseUserID;
    }

    public void setExpenseUserID(String expenseUserID) {
        ExpenseUserID = expenseUserID;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getModule() {
        return Module;
    }

    public void setModule(String module) {
        Module = module;
    }

    public String getBankCardNo() {
        return BankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        BankCardNo = bankCardNo;
    }

    public String getIDCardNo() {
        return IDCardNo;
    }

    public void setIDCardNo(String IDCardNo) {
        this.IDCardNo = IDCardNo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getInsertDate() {
        return InsertDate;
    }

    public void setInsertDate(String insertDate) {
        InsertDate = insertDate;
    }

    public float getFee() {
        return Fee;
    }

    public void setFee(float fee) {
        Fee = fee;
    }

    public int getModuleType() {
        return ModuleType;
    }

    public void setModuleType(int moduleType) {
        ModuleType = moduleType;
    }


}

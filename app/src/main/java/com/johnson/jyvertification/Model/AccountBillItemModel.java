package com.johnson.jyvertification.Model;

import java.io.Serializable;

/**
 * Created by johnsmac on 4/14/16.
 */
public class AccountBillItemModel extends  BaseBean implements Serializable {


    private String CompanyID,ViewDate;
    private float TotalMoney;
    private int VBankCount,VIDCardCount,VBankSucceed,VIDCardSucceed,VBankCharging,VIDCardCharging,ID;

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getViewDate() {
        return ViewDate;
    }

    public void setViewDate(String viewDate) {
        ViewDate = viewDate;
    }

    public float getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        TotalMoney = totalMoney;
    }

    public int getVBankCount() {
        return VBankCount;
    }

    public void setVBankCount(int VBankCount) {
        this.VBankCount = VBankCount;
    }

    public int getVIDCardCount() {
        return VIDCardCount;
    }

    public void setVIDCardCount(int VIDCardCount) {
        this.VIDCardCount = VIDCardCount;
    }

    public int getVIDCardSucceed() {
        return VIDCardSucceed;
    }

    public void setVIDCardSucceed(int VIDCardSucceed) {
        this.VIDCardSucceed = VIDCardSucceed;
    }

    public int getVBankSucceed() {
        return VBankSucceed;
    }

    public void setVBankSucceed(int VBankSucceed) {
        this.VBankSucceed = VBankSucceed;
    }

    public int getVBankCharging() {
        return VBankCharging;
    }

    public void setVBankCharging(int VBankCharging) {
        this.VBankCharging = VBankCharging;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getVIDCardCharging() {
        return VIDCardCharging;
    }

    public void setVIDCardCharging(int VIDCardCharging) {
        this.VIDCardCharging = VIDCardCharging;
    }
}

package com.johnson.jyvertification.Model.InfoModels;

import java.io.Serializable;

/**
 * Created by johnsmac on 4/17/16.
 */
public class AccountInfo implements Serializable{
    /**
     * "Account": {
     * "Beforehand": 1,
     * "Deposit": 2.1,
     * "Money": 3.1,
     * "TotalExpeditureMoney": 4.1,
     * "WarnLimit": 5.1,
     * "IsOpenWarn": 6,
     * "Phone": [
     * "sample string 1",
     * "sample string 2"
     * ],
     * "Email": [
     * "sample string 1",
     * "sample string 2"
     * ]
     * },
     */

    private int Beforehand, IsOpenWarn;
    private float Deposit, Money, TotalExpeditureMoney, WarnLimit;
    private String [] Phone;
    private String [] Email;


    public AccountInfo() {
        super();
    }

    public String[] getPhone() {
        return Phone;
    }

    public void setPhone(String[] phone) {
        Phone = phone;
    }

    public String[] getEmail() {
        return Email;
    }

    public void setEmail(String[] email) {
        Email = email;
    }

    public int getBeforehand() {
        return Beforehand;
    }

    public void setBeforehand(int beforehand) {
        Beforehand = beforehand;
    }

    public int getIsOpenWarn() {
        return IsOpenWarn;
    }

    public void setIsOpenWarn(int isOpenWarn) {
        IsOpenWarn = isOpenWarn;
    }

    public float getDeposit() {
        return Deposit;
    }

    public void setDeposit(float deposit) {
        Deposit = deposit;
    }

    public float getTotalExpeditureMoney() {
        return TotalExpeditureMoney;
    }

    public void setTotalExpeditureMoney(float totalExpeditureMoney) {
        TotalExpeditureMoney = totalExpeditureMoney;
    }

    public float getMoney() {
        return Money;
    }

    public void setMoney(float money) {
        Money = money;
    }

    public float getWarnLimit() {
        return WarnLimit;
    }

    public void setWarnLimit(float warnLimit) {
        WarnLimit = warnLimit;
    }
}

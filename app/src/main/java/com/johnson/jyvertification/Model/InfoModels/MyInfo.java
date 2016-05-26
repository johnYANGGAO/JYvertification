package com.johnson.jyvertification.Model.InfoModels;

import java.io.Serializable;
import java.util.List;

/**
 * Created by johnsmac on 4/18/16.
 */
public class MyInfo implements Serializable {

    private String ParentID;
    private AccountInfo Account;
    private BasicInfo Basic;
    private List<ModuleInfo> Modules;
    private String ID;


    public MyInfo() {
        super();
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String parentID) {
        ParentID = parentID;
    }

    public AccountInfo getAccount() {
        return Account;
    }

    public void setAccount(AccountInfo account) {
        Account = account;
    }

    public BasicInfo getBasic() {
        return Basic;
    }

    public void setBasic(BasicInfo basic) {
        Basic = basic;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<ModuleInfo> getModules() {
        return Modules;
    }

    public void setModules(List<ModuleInfo> modules) {
        Modules = modules;
    }
}

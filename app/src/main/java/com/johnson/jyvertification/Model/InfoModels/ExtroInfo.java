package com.johnson.jyvertification.Model.InfoModels;

import java.io.Serializable;

/**
 * Created by johnsmac on 4/17/16.
 */
public class ExtroInfo implements Serializable {
    /**
     *
     *"APIKey": {
     "RSAPriKey": "sample string 1",
     "RSAPubKey": "sample string 2"
     },

     * */
    private String RSAPriKey,RSAPubKey;


    public ExtroInfo() {
        super();
    }

    public String getRSAPriKey() {
        return RSAPriKey;
    }

    public void setRSAPriKey(String RSAPriKey) {
        this.RSAPriKey = RSAPriKey;
    }

    public String getRSAPubKey() {
        return RSAPubKey;
    }

    public void setRSAPubKey(String RSAPubKey) {
        this.RSAPubKey = RSAPubKey;
    }
}

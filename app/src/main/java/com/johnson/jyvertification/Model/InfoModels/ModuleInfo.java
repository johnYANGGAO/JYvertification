package com.johnson.jyvertification.Model.InfoModels;

import java.io.Serializable;

/**
 * Created by johnsmac on 4/17/16.
 */
public class ModuleInfo implements Serializable {

    /**
     * "Modules": [
     * {
     * "Type": 1,
     * "Name": "sample string 2",
     * "MinFee": 3.1
     * },
     * {
     * "Type": 1,
     * "Name": "sample string 2",
     * "MinFee": 3.1
     * }
     * ],
     */

    private int Type;
    private float MinFee;
    private String Name;

    public ModuleInfo() {
        super();
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public float getMinFee() {
        return MinFee;
    }

    public void setMinFee(float minFee) {
        MinFee = minFee;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

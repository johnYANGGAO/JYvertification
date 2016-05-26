package com.johnson.jyvertification.Model.TransferData;

import java.io.Serializable;

/**
 * Created by johnsmac on 4/25/16.
 */
public class CreateUserDataModel implements Serializable{

    String name,password,roler,status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoler() {
        return roler;
    }

    public void setRoler(String roler) {
        this.roler = roler;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

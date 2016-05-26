package com.johnson.jyvertification.Model;

import java.io.Serializable;

/**
 * Created by johnsmac on 4/11/16.
 */
public class CateGoryItme extends  BaseBean implements Serializable{
    private String content;
    private int id;

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

package com.johnson.jyvertification.Controller.Listeners;

import com.johnson.jyvertification.Model.BaseBean;

import org.json.JSONObject;

/**
 * Created by johnsmac on 4/12/16.
 */
public interface OnParseJsonObjectCallBack {
    void paseJsonToBean(JSONObject obj);
}

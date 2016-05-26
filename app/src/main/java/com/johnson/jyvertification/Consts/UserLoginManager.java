package com.johnson.jyvertification.Consts;

import android.content.Context;
import android.content.SharedPreferences;

import com.johnson.jyvertification.R;



/**
 * Created by johnsmac on 4/12/16.
 */
public class UserLoginManager {

    static UserLoginManager userLoginManager;

    static SharedPreferences sharedPreferences;

    public UserLoginManager(){}

    public static UserLoginManager getInstance(){
        if (userLoginManager==null){

            userLoginManager=new UserLoginManager();
        }

        return userLoginManager;
    }
    //设置 专项 SharedPreferences

    public  SharedPreferences getSharedPreferences(Context mContext, int ID){
        sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(ID), Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public  void setStringForKey(SharedPreferences spf,String value,String key){
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public void setBooleanForKey(SharedPreferences spf,boolean value,String key){
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public  void setIntForKey(SharedPreferences spf,int value,String key){
        SharedPreferences.Editor editor = spf.edit();
        editor.putInt(key, value);
        editor.commit();
    }

}

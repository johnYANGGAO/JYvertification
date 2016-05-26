package com.johnson.jyvertification.NetWorking.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Consts.UserLoginManager;
import com.johnson.jyvertification.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by johnsmac on 4/17/16.
 */
public class HandleFile {
    private String TAG = "HandleFile";
    private Context mContext;
    private static HandleFile handleFile ;

    private HandleFile(Context context) {
        super();
        this.mContext=context;
    }

    public static HandleFile getInstance(Context context) {
        if(handleFile==null){
            handleFile=new HandleFile(context);
        }
        return handleFile;

    }



    public void flagStoreFile(String fileName) {

        UserLoginManager userLoginManager = UserLoginManager.getInstance();
        SharedPreferences sharedPreferences = userLoginManager.getSharedPreferences(mContext, R.string.com_johnson_jyvertification);
        userLoginManager.setStringForKey(sharedPreferences, fileName, PublicUtil.hasFile);

    }

    public boolean flagHasFile(String fileName) {

        SharedPreferences spf = UserLoginManager.getInstance().getSharedPreferences(mContext, R.string.com_johnson_jyvertification);
        String storedFilename = spf.getString(PublicUtil.hasFile, "");

        if (fileName.equals(storedFilename)) {
            return true;
        }

        return false;
    }

    public String serialize(Object person) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(person);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        return serStr;
    }

    /**
     * 反序列化对象
     *

     */
    public Object deSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Object person = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return person;
    }

    public void saveObject(String strObject) {
        SharedPreferences sp = mContext.getSharedPreferences("person", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("person", strObject);
        edit.commit();
    }

    public String getObject() {
        SharedPreferences sp = mContext.getSharedPreferences("person", 0);
        return sp.getString("person", null);
    }



}

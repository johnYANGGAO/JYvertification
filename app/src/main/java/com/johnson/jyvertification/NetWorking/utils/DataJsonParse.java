package com.johnson.jyvertification.NetWorking.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Controller.Listeners.OnParseJsonObjectCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by johnsmac on 4/12/16.
 */
public class DataJsonParse {

    private Context mContext;

    private OnParseJsonObjectCallBack jsonObjectCallBack;


    public DataJsonParse(Context context) {
        mContext=context;
    }
    public void setOnParseJsonObjectCallBack(OnParseJsonObjectCallBack mCallBack){

        jsonObjectCallBack=mCallBack;

    }
    /**
     * 成功响应获得的 数据处理 适合 Data 里的是json_object数据
     * */
    public void parseBaseContent(JSONObject object,Handler handler){
        try {
            if (Integer.parseInt(object.get("Result").toString())==0){

                JSONObject jsonObject=object.getJSONObject("Data");
                if (jsonObjectCallBack==null){
                    throw new UnsupportedOperationException("OnParseJsonObjectCallBack Not yet implemented");
                }
                jsonObjectCallBack.paseJsonToBean(jsonObject);
                if(handler!=null) {
                    handler.sendEmptyMessage(0);
                }
            }else{
                /**
                 * 处理 错误 到 广播 通知 (特注:这里的错误 是已经请求成功200状态下 后台返回的数据)
                 * */
                String errorContent=object.getString("Exception");
                Intent intent=new Intent();
                intent.setAction(PublicUtil.ERROR_ACTION);
                intent.putExtra(PublicUtil.ERROR_ACTION, errorContent);
                mContext.sendBroadcast(intent);
                //处理网络请求提示进度消失 ／ 处理服务中的请求成功但回应错误
                if (handler!=null) {
                    handler.sendEmptyMessage(PublicUtil.DEFAUTL_MSG_WHAT);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    /**
     *全部交给实现处理
     * */
    public void parseDataContent(JSONObject object,Handler handler){
        try {
            if (Integer.parseInt(object.get("Result").toString())==0){

                jsonObjectCallBack.paseJsonToBean(object);

                if(handler!=null) {
                    handler.sendEmptyMessage(0);
                }

            }else{

                String errorContent=object.getString("Exception");
                Intent intent=new Intent();
                intent.setAction(PublicUtil.ERROR_ACTION);
                intent.putExtra(PublicUtil.ERROR_ACTION, errorContent);
                mContext.sendBroadcast(intent);
                //处理网络请求提示进度消失
                if(handler!=null) {
                    handler.sendEmptyMessage(PublicUtil.DEFAUTL_MSG_WHAT);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}

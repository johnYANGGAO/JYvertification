package com.johnson.jyvertification.GetInfoByIDService;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Consts.RequestQue;
import com.johnson.jyvertification.Consts.UserLoginManager;
import com.johnson.jyvertification.Controller.Listeners.OnParseJsonObjectCallBack;
import com.johnson.jyvertification.Model.InfoModels.MyInfo;
import com.johnson.jyvertification.NetWorking.TimeUtil;
import com.johnson.jyvertification.NetWorking.crypto.RSA;
import com.johnson.jyvertification.NetWorking.utils.DataJsonParse;
import com.johnson.jyvertification.NetWorking.utils.HandleFile;
import com.johnson.jyvertification.NetWorking.utils.NetHasUtil;
import com.johnson.jyvertification.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class GetInfoIntentService extends IntentService implements OnParseJsonObjectCallBack {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_BY_ID = "action_a";
    private static final String ACTION_BY_undefine = "action_b";

    private static final String BY_ID = "id_type";
    private static final String BY_undefine = "undefine";
    private int SUCESS = 0;
    private int ERROR = 1;
    private DataJsonParse manager;

    //发送广播 通知
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (PublicUtil.DEFAUTL_MSG_WHAT == msg.what) {
                /*
                * 后台拒绝广播 解析的时候 已经处理
                * */

            }

            if (msg.what == SUCESS) {
                //TODO 发送请求成功广播
                Intent intent = new Intent();
                intent.setAction(PublicUtil.LOAD_FINISHED_ACTION);
                intent.putExtra("loaded", 0);
                GetInfoIntentService.this.sendBroadcast(intent);


            } else if (msg.what == ERROR) {
                //TODO 发送请求错误广播
                Intent intent = new Intent();
                intent.setAction(PublicUtil.ERROR_ACTION);
                intent.putExtra("serviceErrorMsg", (String) msg.obj);
                GetInfoIntentService.this.sendBroadcast(intent);

            }
        }
    };


    public GetInfoIntentService() {
        super("GetInfoIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        inintManager();
    }

    public static void startActionGetInfoById(Context context, String id) {

        Intent intent = new Intent(context, GetInfoIntentService.class);
        intent.setAction(ACTION_BY_ID);
        intent.putExtra(BY_ID, id);
        Log.i("MyTag",id);
        context.startService(intent);
    }

    private void inintManager() {

        manager = new DataJsonParse(GetInfoIntentService.this);
        manager.setOnParseJsonObjectCallBack(this);

    }

    public static void startActionUndefind(Context context) {
        Intent intent = new Intent(context, GetInfoIntentService.class);
        intent.setAction(ACTION_BY_undefine);
//        intent.putExtra(BY_undefine, params);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_BY_ID.equals(action)) {
                final String param1 = intent.getStringExtra(BY_ID);
                handleActionFoo(param1);
            } else if (ACTION_BY_undefine.equals(action)) {
//                final String param2 = intent.getStringExtra(BY_undefine);
                handleActionBaz(null);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param) {

        getData(param);

//        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param) {
        // 判断网络并广播

        Intent intent = new Intent();
        intent.setAction(PublicUtil.NETWORKING);
        NetHasUtil.getInstance().hasNet(this, intent);
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String TAG = "GetInfoIntentService";

    private void getData(String idtype) {

        JSONObject jsonObject = new JSONObject(getParams(idtype));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, getUrl(), jsonObject, new Response.Listener<JSONObject>
                        () {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, " SUCCESS  Response is: " + response);
                        manager.parseBaseContent(response, handler);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "ERROR  Response is: " + error);
                        Message msg = handler.obtainMessage();
                        msg.what = ERROR;
                        msg.obj = error.getLocalizedMessage();
                        handler.sendMessage(msg);
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {  //设置头信息
                Map<String, String> map = new HashMap<String, String>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {  //设置参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("Content-Type", "application/json");
                return map;
            }
        };

        RequestQue.getInstance(GetInfoIntentService.this).addToRequestQueue(jsonObjectRequest);

    }

    private String getUrl() {
        /**
         * api/Company/ById?login={login}&pwd={pwd}&signature={signature}
         * */
        String url=null;
        SharedPreferences spf = UserLoginManager.getInstance().getSharedPreferences(GetInfoIntentService.this, R.string.com_johnson_jyvertification);
        String loginname = spf.getString(PublicUtil.login_name_key, "");
        String loginpassword = spf.getString(PublicUtil.login_password_key, "");
        String sign = RSA.encryptWithKey(null, TimeUtil.getStartTime());
        try {
            url = PublicUtil.base_url + "api/Company/ById?login=" + loginname + "&pwd=" + loginpassword + "&signature=" + URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    private Map<String, String> getParams(String idType) {

        Map<String, String> params = new HashMap<>();
        params.put("ID", idType);

        return params;
    }

    @Override
    public void paseJsonToBean(JSONObject obj) {

        /**
         * 解析得到的 实体  保存 到 文件里 为了便于模拟器测试 不写到sdcard
         * */
        MyInfo myinfo = JSON.parseObject(obj.toString(), MyInfo.class);
        HandleFile handleFile = HandleFile.getInstance(GetInfoIntentService.this);
        try {
            String objStr=handleFile.serialize(myinfo);
            handleFile.saveObject(objStr);
            //标示保存了
            handleFile.flagStoreFile(PublicUtil.FILEOBJECT);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

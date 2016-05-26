package com.johnson.jyvertification.Controller.Activitys;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.johnson.jyvertification.BroadCastUtil.ErrorContentReceiver;
import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Consts.RequestQue;
import com.johnson.jyvertification.Consts.UserLoginManager;
import com.johnson.jyvertification.Controller.Listeners.OnParseJsonObjectCallBack;
import com.johnson.jyvertification.Model.TransferData.CreateUserDataModel;
import com.johnson.jyvertification.NetWorking.TimeUtil;
import com.johnson.jyvertification.NetWorking.crypto.RSA;
import com.johnson.jyvertification.NetWorking.utils.DataJsonParse;
import com.johnson.jyvertification.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityCreateUser extends AppCompatActivity implements ActivityCreateUserFragment.OnCreateUserPressSureListener, OnParseJsonObjectCallBack {

    private String TAG = ActivityCreateUser.class.getSimpleName();
    private TextView toolbar_custmer_title;
    private SweetAlertDialog waitDialog;
    private ErrorContentReceiver receiver;
    private DataJsonParse manager;
    private static int SUCESS = 0;
    private static int ERROR = 1;
    private String resultContent;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (waitDialog != null) {

                waitDialog.dismissWithAnimation();

            }

            if (PublicUtil.DEFAUTL_MSG_WHAT == msg.what) {


            }

            if (msg.what == SUCESS) {


                new SweetAlertDialog(ActivityCreateUser.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(resultContent)
                        .setContentText("点击OK返回").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        finish();
                    }
                }).show();


            } else if (msg.what == ERROR) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCreateUser.this);
                builder.setTitle("提示").setMessage((String) msg.obj)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).create().show();

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_custmer_title = (TextView) findViewById(R.id.toolbar_custmer_title);
        toolbar.setTitle("");
        toolbar_custmer_title.setText("创建平台用户");
        setSupportActionBar(toolbar);
        /**
         * 可以改指示 为 X
         * */
        toolbar.setNavigationIcon(R.drawable.back_quit_selector);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initManager();

    }

    private void initManager() {

        receiver = new ErrorContentReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PublicUtil.ERROR_ACTION);
        registerReceiver(receiver, filter);

        manager = new DataJsonParse(ActivityCreateUser.this);
        manager.setOnParseJsonObjectCallBack(this);


    }

    //网络请求
    @Override
    public void pressButton(CreateUserDataModel item) {

        int role = 0, statu = 0;
        List<String> options_role = Arrays.asList(getResources().getStringArray(R.array.roles));
        List<String> options_statu = Arrays.asList(getResources().getStringArray(R.array.status));
        String roleFromModel = item.getRoler();
        String statuFromModel = item.getStatus();
        if (options_role.get(0).equals(roleFromModel)) {
            role = 0;
        } else if (options_role.get(1).equals(roleFromModel)) {
            role = 1;
        } else if (options_role.get(2).equals(roleFromModel)) {
            role = 2;
        }
        if (options_statu.get(0).equals(statuFromModel)) {
            statu = 0;
        } else if (options_statu.get(1).equals(statuFromModel)) {
            statu = 1;
        }


        Map<String, String> params = new HashMap<>();
        params.put("Login", item.getName());
        params.put("Pwd", item.getPassword());
        params.put("Roles", String.valueOf(role));
        params.put("Status", String.valueOf(statu));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, getUrl(), new JSONObject(params), new Response.Listener<JSONObject>
                        () {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, " SUCCESS  Response is: " + response);
                        manager.parseDataContent(response, handler);
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

        RequestQue.getInstance(ActivityCreateUser.this).addToRequestQueue(jsonObjectRequest);

        waitDialog = new SweetAlertDialog(ActivityCreateUser.this, SweetAlertDialog.PROGRESS_TYPE);
        waitDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        waitDialog.setTitleText("Loading");
        waitDialog.setCancelable(false);
        waitDialog.show();


    }

    /**
     * api/Users/Create?login={login}&pwd={pwd}&signature={signature}
     */

    private String getUrl() {

        String url = "";
        SharedPreferences spf = UserLoginManager.getInstance().getSharedPreferences(ActivityCreateUser.this, R.string.com_johnson_jyvertification);
        String loginname = spf.getString(PublicUtil.login_name_key, "");
        String loginpassword = spf.getString(PublicUtil.login_password_key, "");
        String sign = RSA.encryptWithKey(null, TimeUtil.getStartTime());
        try {
            url = PublicUtil.base_url + "api/Users/Create?login=" + loginname + "&pwd=" + loginpassword + "&signature=" + URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;

    }


    @Override
    public void paseJsonToBean(JSONObject obj) {
        try {
            resultContent = obj.get("Exception").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}

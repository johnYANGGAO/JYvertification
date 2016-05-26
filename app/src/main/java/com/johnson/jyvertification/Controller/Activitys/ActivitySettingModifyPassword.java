package com.johnson.jyvertification.Controller.Activitys;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
import com.johnson.jyvertification.NetWorking.TimeUtil;
import com.johnson.jyvertification.NetWorking.crypto.RSA;
import com.johnson.jyvertification.NetWorking.utils.DataJsonParse;
import com.johnson.jyvertification.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivitySettingModifyPassword extends AppCompatActivity implements OnParseJsonObjectCallBack, OnClickListener {
    private EditText old_password, new_password, new_password_confirm;
    private Button modify_password_sure;
    private static int SUCESS = 0;
    private static int ERROR = 1;
    private SweetAlertDialog waitDialog = null;
    private ErrorContentReceiver receiver;
    private DataJsonParse manager;
    private String TAG = "ActivitySettingModifyPassword";
    private String result;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (waitDialog != null) {

                waitDialog.dismissWithAnimation();

            }

            if (PublicUtil.DEFAUTL_MSG_WHAT == msg.what) {


            }

            if (msg.what == SUCESS) {
                //展示成功
                new SweetAlertDialog(ActivitySettingModifyPassword.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(result)
                        .setConfirmText("重新登录").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        Intent intent = new Intent(ActivitySettingModifyPassword.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }).show();

            } else if (msg.what == ERROR) {
                new SweetAlertDialog(ActivitySettingModifyPassword.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定返回吗?")
                        .setContentText((String) msg.obj)
                        .setConfirmText("好的!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                    }
                }).show();
            }
        }
    };

    private void initParseManager() {

        receiver = new ErrorContentReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PublicUtil.ERROR_ACTION);
        registerReceiver(receiver, filter);

        manager = new DataJsonParse(ActivitySettingModifyPassword.this);
        manager.setOnParseJsonObjectCallBack(this);

    }

    private void initViews() {

        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        new_password_confirm = (EditText) findViewById(R.id.new_password_confirm);
        modify_password_sure = (Button) findViewById(R.id.modify_password_sure);

        modify_password_sure.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_modify_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("更改密码");
        }
        initViews();
        initParseManager();

    }

    /**
     * 网络请求
     */
    private void getData() {

        JSONObject jsonObject = new JSONObject(getParams());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, getUrl(), jsonObject, new Response.Listener<JSONObject>
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

        RequestQue.getInstance(ActivitySettingModifyPassword.this).addToRequestQueue(jsonObjectRequest);
        waitDialog = new SweetAlertDialog(ActivitySettingModifyPassword.this, SweetAlertDialog.PROGRESS_TYPE);
        waitDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        waitDialog.setTitleText("Loading");
        waitDialog.setCancelable(false);
        waitDialog.show();


    }

    private String getUrl() {

        String url = "";
        SharedPreferences spf = UserLoginManager.getInstance().getSharedPreferences(ActivitySettingModifyPassword.this, R.string.com_johnson_jyvertification);
        String loginname = spf.getString(PublicUtil.login_name_key, "");
        String loginpassword = spf.getString(PublicUtil.login_password_key, "");
        String sign = RSA.encryptWithKey(null, TimeUtil.getStartTime());
        try {
            url = PublicUtil.base_url + "api/Users/UpdatePwd?login=" + loginname + "&pwd=" + loginpassword + "&signature=" + URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     *
     * */
    private Map<String, String> getParams() {

        Map<String, String> params = new HashMap<>();
        params.put("OldPwd", older_pass);
        params.put("NewPwd", new_pass);
        return params;

    }


    @Override
    public void paseJsonToBean(JSONObject obj) {

        try {
            result = obj.getString("Exception");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    String older_pass, new_pass;

    @Override
    public void onClick(View view) {

        old_password.setError(null);
        new_password.setError(null);
        new_password_confirm.setError(null);
        View foucsView = null;
        boolean cancel = false;
        older_pass = old_password.getText().toString();
        new_pass = new_password.getText().toString();
        if (TextUtils.isEmpty(older_pass)) {
            old_password.setError("原始密码不能为空");
            foucsView = old_password;
            cancel = true;
        } else if (TextUtils.isEmpty(new_pass)) {

            new_password.setError("新密码不能为空");
            foucsView = new_password;
            cancel = true;

        }
        String confirm = new_password_confirm.getEditableText().toString();
        if (TextUtils.isEmpty(confirm) || !confirm.equals(new_pass)) {
            new_password_confirm.setError("请确认新密码");
            foucsView = new_password_confirm;
            cancel = true;
        }
        if (cancel) {
            foucsView.requestFocus();
        } else {
            getData();
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

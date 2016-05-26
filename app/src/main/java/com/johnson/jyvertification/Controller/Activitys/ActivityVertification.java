package com.johnson.jyvertification.Controller.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;

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
import com.johnson.jyvertification.Controller.Listeners.OnVertificationCallBackListener;
import com.johnson.jyvertification.NetWorking.TimeUtil;
import com.johnson.jyvertification.NetWorking.crypto.RSA;
import com.johnson.jyvertification.NetWorking.utils.AESOperator;
import com.johnson.jyvertification.NetWorking.utils.DataJsonParse;
import com.johnson.jyvertification.NetWorking.utils.NoteIDError;
import com.johnson.jyvertification.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityVertification extends BaseActivity implements OnVertificationCallBackListener, OnParseJsonObjectCallBack, NoteIDError {

    /**
     * 管理 ActivityVertificationFragment fragment;
     * <p/>
     * <p/>
     * android:id="@+id/fragment"
     * <p/>
     * if (fragment != null) {
     * FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
     * ft.replace(R.id.fragment, fragment);
     * ft.commit();
     * }
     * <p/>
     * // set the toolbar title
     * if (getSupportActionBar() != null) {
     * getSupportActionBar().setTitle("核查");
     * }
     */
    private static int SUCESS = 0;
    private static int ERROR = 1;
    private String TAG = "ActivityVertification";
    private SweetAlertDialog waitDialog = null;
    private ErrorContentReceiver receiver;
    private DataJsonParse manager;
    private int segment;
    private int SEGMENTID = 0;
    private int SEGMENTCREDITCARD = 1;
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
                /**
                 * 展示核查结果内容
                 * */

                new SweetAlertDialog(ActivityVertification.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("核查结果")
                        .setContentText(resultContent)
                        .show();


            } else if (msg.what == ERROR) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityVertification.this);
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
        setContentView(R.layout.activity_vertification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            if (getIntent().getIntExtra("type", 0) == SEGMENTID) {
                getSupportActionBar().setTitle("身份核查");
            } else {
                getSupportActionBar().setTitle("银行卡核查");
            }
        }
        initParseManager();
    }

    private void initParseManager() {

        receiver = new ErrorContentReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PublicUtil.ERROR_ACTION);
        registerReceiver(receiver, filter);

        manager = new DataJsonParse(ActivityVertification.this);
        manager.setOnParseJsonObjectCallBack(this);

    }

    @Override
    public void onVertificationCallBack(String... result) {
        /**
         * 截取参数
         *
         * */
        String account = result[0];
        String identify = result[1];
        //公共部分抽取
        SharedPreferences spf = UserLoginManager.getInstance().getSharedPreferences(ActivityVertification.this, R.string.com_johnson_jyvertification);
        String loginname = spf.getString(PublicUtil.login_name_key, "");
        String loginpassword = spf.getString(PublicUtil.login_password_key, "");

        /**
         * 如果 为空 则 跳转到登录页 重新登录
         * */
        if (TextUtils.isEmpty(loginname) || TextUtils.isEmpty(loginpassword)) {

            Intent intent = new Intent(ActivityVertification.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String key = AESOperator.getInstance().generateKey();
        Map<String, String> params = new HashMap<>();
        String getUrl = null;


        if (getIntent().getIntExtra("type", 0) == SEGMENTID) {

            try {
                getUrl = PublicUtil.base_url + "api/Verify/IDCard?login=" + loginname + "&pwd="
                        + loginpassword + "&signature=" + URLEncoder.encode(getSign(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("Name", AEScoding(account, key));
            params.put("IDCardNo", AEScoding(identify, key));

        } else if (getIntent().getIntExtra("type", 0) == SEGMENTCREDITCARD) {

            String credit_card = result[2];
            String cell_phone = result[3];

            // 这里的签名要做 特殊 处理

            try {
                getUrl = PublicUtil.base_url + "api/Verify/BankCard?login=" + loginname + "&pwd="
                        + loginpassword + "&signature=" + URLEncoder.encode(getSign(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            params = getParams(key, credit_card, identify, account, cell_phone);
        }

        JSONObject jsonObj = new JSONObject(params);
        Log.i("MyTag", "-->" + jsonObj.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, getUrl, jsonObj, new Response.Listener<JSONObject>
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

        RequestQue.getInstance(ActivityVertification.this).addToRequestQueue(jsonObjectRequest);

        waitDialog = new SweetAlertDialog(ActivityVertification.this, SweetAlertDialog.PROGRESS_TYPE);
        waitDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        waitDialog.setTitleText("Loading");
        waitDialog.setCancelable(false);
        waitDialog.show();

    }


    private String getSign(String key) {

        return RSA.encryptWithKey(null, getAESSign(key));
    }

    private Map<String, String> getParams(String key, String carNo, String id, String um, String cell_phone) {

        Map<String, String> params = new HashMap();

        params.put("CardNo", AEScoding(carNo, key));
        params.put("IDCardType", "01");
        params.put("IDCardNo", AEScoding(id, key));
        params.put("UserName", AEScoding(um, key));
        params.put("TelephoneNo", AEScoding(cell_phone, key));

        return params;

    }

    private String AEScoding(String original, String key) {

        String value = null;
        if (original != null) {
            try {
                Log.i("MyTag", "credit_card :" + original);
                value = AESOperator.getInstance().encrypt(original, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    private String getAESSign(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("time", TimeUtil.getStartTime());
        map.put("aes", key);
        return new JSONObject(map).toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {

            unregisterReceiver(receiver);
        }
    }

    private String resultContent;

    @Override
    public void paseJsonToBean(JSONObject obj) {

        /**
         * 解析信息
         * */

        try {
            resultContent = obj.getString("Data").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void showIdErroArea(String error) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(error)
                .setConfirmText("确定!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        //If view is in News fragment, exit application
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }).show();
    }
}

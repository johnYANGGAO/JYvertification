package com.johnson.jyvertification.Controller.Activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.johnson.jyvertification.BroadCastUtil.ErrorContentReceiver;
import com.johnson.jyvertification.BroadCastUtil.LoadInfoFinishedReceiver;
import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Consts.RequestQue;
import com.johnson.jyvertification.Consts.UserLoginManager;
import com.johnson.jyvertification.Controller.Listeners.OnParseJsonObjectCallBack;
import com.johnson.jyvertification.GetInfoByIDService.GetInfoIntentService;
import com.johnson.jyvertification.Model.LoginModel;
import com.johnson.jyvertification.NetWorking.MD5Util;
import com.johnson.jyvertification.NetWorking.TimeUtil;
import com.johnson.jyvertification.NetWorking.crypto.RSA;
import com.johnson.jyvertification.NetWorking.utils.DataJsonParse;
import com.johnson.jyvertification.R;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnParseJsonObjectCallBack {

    private final int SUCESS = 0;
    private final int ERROR = 1;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    public static final String TAG = "MyTag";
    RequestQueue mRequestQueue = null;
    private DataJsonParse manager;
    private ErrorContentReceiver receiver;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            showProgress(false);
            if (msg.what == PublicUtil.DEFAUTL_MSG_WHAT) {

            }
            if (msg.what == SUCESS) {
                Log.i("MyTag", "开启服务");
                saveBasicLoginInfo();

                GetInfoIntentService service = new GetInfoIntentService();
                service.startActionGetInfoById(LoginActivity.this, loginModel.getCompanyID());
                loadingDialog.show();


            } else if (msg.what == ERROR) {
//                Snackbar.make(findViewById(android.R.id.content), (String) msg.obj, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(msg.obj==null){
                    msg.obj="未能连接到 服务器";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage((String) msg.obj)
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
        setContentView(R.layout.activity_login);
        // Set up the login form.

        mRequestQueue = RequestQue.getInstance(this).getRequestQueue();

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                /**
                 * 测试 TODO
                 * **/
//                Intent intent=new Intent(LoginActivity.this,ActivityCreateUser.class);
//                startActivity(intent);

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        initParseManager();
        registerServiceBroadCast();
    }

    private LoadInfoFinishedReceiver infoFinishedReceiver;
    private SweetAlertDialog loadingDialog;

    private void registerServiceBroadCast() {
        loadingDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        loadingDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingDialog.setTitleText("获取相关信息\n请稍后...");
        loadingDialog.setCancelable(false);

        infoFinishedReceiver = new LoadInfoFinishedReceiver(loadingDialog);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PublicUtil.LOAD_FINISHED_ACTION);
        intentFilter.addAction(PublicUtil.ERROR_ACTION);

        registerReceiver(infoFinishedReceiver, intentFilter);

    }

    private void initParseManager() {

        receiver = new ErrorContentReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PublicUtil.ERROR_ACTION);
        registerReceiver(receiver, filter);

        manager = new DataJsonParse(LoginActivity.this);
        manager.setOnParseJsonObjectCallBack(this);

    }

    private void saveBasicLoginInfo() {

        UserLoginManager userLoginManager = UserLoginManager.getInstance();
        SharedPreferences sharedPreferences = userLoginManager.getSharedPreferences(LoginActivity.this, R.string.com_johnson_jyvertification);
        userLoginManager.setStringForKey(sharedPreferences, loginManagername, PublicUtil.login_name_key);
        userLoginManager.setStringForKey(sharedPreferences, loginManagermd5password, PublicUtil.login_password_key);
        userLoginManager.setIntForKey(sharedPreferences, loginModel.getRoles(), PublicUtil.role_id);
        userLoginManager.setIntForKey(sharedPreferences, loginModel.getStatus(), PublicUtil.status);
        userLoginManager.setStringForKey(sharedPreferences, loginModel.getCompanyID(), PublicUtil.login_company_id);

    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String account = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(account)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            UserLoginTask(account, password);

        }

    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public String loginManagermd5password, loginManagername;

    public void UserLoginTask(String account, String password) {

        try {


            String url = PublicUtil.base_url;

            String sign = RSA.encryptWithKey(null, TimeUtil.getStartTime());

            loginManagermd5password = MD5Util.getMD5String(password);
            loginManagername = account;

            Log.i(TAG, "->\n加密后" + sign);


            Log.i(TAG, "解密后 ：" + RSA.decryptWithStoredKey(sign));

            String format = String.format("api/Users/Login?login=%s&pwd=%s&signature=%s", account, MD5Util.getMD5String(password), URLEncoder.encode(sign, "UTF-8"));

            String getUrl = url + format;

            Log.i(TAG, "请求的URL ：" + getUrl);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, getUrl, (String) null, new Response.Listener<JSONObject>
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
//                            System.out.print("ERROR  Response is:"+error);
                            Message msg = handler.obtainMessage();
                            msg.what = ERROR;
                            msg.obj = error.getLocalizedMessage();
                            handler.sendMessage(msg);
                        }

                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {  //设置头信息
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("Content-Type", "application/json");

                    return map;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {  //设置参数
                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("Content-Type", "application/json");
                    return map;
                }
            };

            RequestQue.getInstance(this).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setTag(TAG);
        } catch (Exception e) {

            Log.i(TAG, "网络请求错误 ：" + e.getLocalizedMessage());
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        showProgress(false);

        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        if (infoFinishedReceiver != null) {
            unregisterReceiver(infoFinishedReceiver);
        }
    }

    private LoginModel loginModel;

    @Override
    public void paseJsonToBean(JSONObject obj) {

        loginModel = JSON.parseObject(obj.toString(), LoginModel.class);

    }


}


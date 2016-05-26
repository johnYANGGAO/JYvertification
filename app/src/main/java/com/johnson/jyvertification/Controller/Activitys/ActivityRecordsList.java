package com.johnson.jyvertification.Controller.Activitys;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.johnson.jyvertification.BroadCastUtil.ErrorContentReceiver;
import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Consts.RequestQue;
import com.johnson.jyvertification.Consts.UserLoginManager;
import com.johnson.jyvertification.Controller.ListDataController.CheckingAccountListFragment;
import com.johnson.jyvertification.Controller.ListDataController.CheckingIDListFragment;
import com.johnson.jyvertification.Controller.Listeners.OnListCheckingIDFragmentInteractionListener;
import com.johnson.jyvertification.Controller.Listeners.OnParseJsonObjectCallBack;
import com.johnson.jyvertification.Model.AccountBillItemDataModel;
import com.johnson.jyvertification.Model.AccountBillItemModel;
import com.johnson.jyvertification.Model.BaseBean;
import com.johnson.jyvertification.Model.RecordsItemDataModel;
import com.johnson.jyvertification.Model.RecordsItemModel;
import com.johnson.jyvertification.NetWorking.TimeUtil;
import com.johnson.jyvertification.NetWorking.crypto.RSA;
import com.johnson.jyvertification.NetWorking.utils.DataJsonParse;
import com.johnson.jyvertification.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityRecordsList extends AppCompatActivity implements OnListCheckingIDFragmentInteractionListener ,OnParseJsonObjectCallBack {
    /**
     * 管理CheckingAccountListFragment and CheckingIDListFragment
     */
    private int type;
    private  String TAG="ActivityRecordsList";
    private static int SUCESS=0;
    private static int ERROR=1;
    private SweetAlertDialog waitDialog=null;
    private ErrorContentReceiver receiver;
    private DataJsonParse manager;
    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(waitDialog!=null){

                waitDialog.dismissWithAnimation();

            }

            if(PublicUtil.DEFAUTL_MSG_WHAT==msg.what){


            }

            if(msg.what==SUCESS){
               Log.i(TAG,"数据加载完毕");
                displayView(type);

            }else if(msg.what==ERROR) {
                new SweetAlertDialog(ActivityRecordsList.this, SweetAlertDialog.WARNING_TYPE)
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

    private void initParseManager(){

        receiver=new ErrorContentReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(PublicUtil.ERROR_ACTION);
        registerReceiver(receiver, filter);

        manager=new DataJsonParse(ActivityRecordsList.this);
        manager.setOnParseJsonObjectCallBack(this);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("记录");
        }
        initParseManager();
        type = getIntent().getIntExtra("type", 0);
        getData(type);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
    }

    /**
     * TODO  网络要请求
     */
    private List<RecordsItemModel> datalist = new ArrayList<>();
    private List<AccountBillItemModel> dataAccountlist = new ArrayList<>();
    /**
     * api/Logs/GetVerify?login={login}&pwd={pwd}&signature={signature}

     * api/Logs/GetCompanyBill?login={login}&pwd={pwd}&signature={signature}

     * */

    public void getData(int type) {

        Map<String, String> params = getParams(type);

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest
                (Request.Method.POST,getUrl(type), jsonObj,new Response.Listener<JSONObject>
                        (){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, " SUCCESS  Response is: " + response);
                        manager.parseDataContent(response, handler);
                    }
                },new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG,"ERROR  Response is: " + error);
                        Message msg=handler.obtainMessage();
                        msg.what=ERROR;
                        msg.obj=error.getLocalizedMessage();
                        handler.sendMessage(msg);
                    }

                }){
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

        RequestQue.getInstance(ActivityRecordsList.this).addToRequestQueue(jsonObjectRequest);
        waitDialog=new SweetAlertDialog(ActivityRecordsList.this, SweetAlertDialog.PROGRESS_TYPE);
        waitDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        waitDialog.setTitleText("Loading");
        waitDialog.setCancelable(false);
        waitDialog.show();

    }

    private String getUrl(int type){
        String url="";
        SharedPreferences spf= UserLoginManager.getInstance().getSharedPreferences(ActivityRecordsList.this, R.string.com_johnson_jyvertification);
        String loginname=spf.getString(PublicUtil.login_name_key, "");
        String loginpassword=spf.getString(PublicUtil.login_password_key,"");
        String sign= RSA.encryptWithKey(null, TimeUtil.getStartTime());

        switch (type) {

            case 0:
                try {
                    url= PublicUtil.base_url+"api/Logs/GetVerify?login="+loginname+"&pwd="+loginpassword+"&signature="+ URLEncoder.encode(sign, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    url=PublicUtil.base_url+"api/Logs/GetCompanyBill?login="+loginname+"&pwd="+loginpassword+"&signature="+URLEncoder.encode(sign,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;

        }
        return url;
    }

    private Map<String, String> getParams(int type) {

        Map<String, String> params = new HashMap<>();

        String name_login = null, nameUser = null, noCard = null, dateStart = null, dateEnd = null;
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();

        if (type == 0) {

            if (getIntent().getStringExtra("name_login") != null) {
                name_login = bundle.getString("name_login");
                params.put("Login", name_login);
            }

            nameUser = bundle.getString("nameUser");
            noCard = bundle.getString("noCard");
            dateStart = bundle.getString("dateStart");
            dateEnd = bundle.getString("dateEnd");


            params.put("UserName", nameUser);
            params.put("CardNo", noCard);
            params.put("ModuleType", "4");
            params.put("IP", "");
            params.put("Skip", "8");
            params.put("Take", "9");

        } else if (type == 1) {
            dateStart = bundle.getString("dateStart");
            dateEnd = bundle.getString("dateEnd");
            params.put("Skip", "3");
            params.put("Take", "4");
        }


        params.put("StartDate", dateStart);
        params.put("EndDate", dateEnd);


        return params;

    }


    public void displayView(int type) {

        if (datalist.size() <= 0||dataAccountlist.size()<=0) {
            new SweetAlertDialog(ActivityRecordsList.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("确定返回吗?")
                    .setContentText("没有数据哦!")
                    .setConfirmText("好的!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    finish();
                }
            }).show();

            return;
        }
        Fragment fragment = null;
        switch (type) {

            case 0:
//                CheckingIDListFragment
                fragment = CheckingIDListFragment.newInstance(datalist);
                break;

            case 1:
//                CheckingAccountListFragment
                fragment = CheckingAccountListFragment.newInstance(dataAccountlist);
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_records_list_content_frame, fragment);
        ft.commit();


    }

    @Override
    public void onListFragmentInteraction(BaseBean item) {
        /**
         *
         * 点击了条目弹出对话框 显示 详情
         *
         * */
        Log.i("MyTag", "准备跳转 go");
        if (type==0){
            //item 为 RecordsItemModel
            RecordsItemModel model=(RecordsItemModel)item;
            new SweetAlertDialog(this)
                    .setTitleText("详情")
                    .setContentText(model.getBankCardNo()+"\n"+model.getFee()+"\n"+model.getCompanyID()+"\n"
                                    +model.getUserName()+"\n"+model.getExpenseUserID()+"\n"
                                    +model.getResult()+"\n"+model.getModule()+"\n"+model.getInsertDate()
                    ).show();


        }else if(type==1){
           //item 为 AccountBillItemModel
            AccountBillItemModel model=(AccountBillItemModel)item;
            new SweetAlertDialog(this)
                    .setTitleText("详情")
                    .setContentText(model.getTotalMoney()+"\n"+model.getVBankCharging()+"\n"+model.getCompanyID()+"\n"
                                    +model.getVIDCardCharging()+"\n"+model.getVIDCardCount()+"\n"
                                    +model.getVIDCardSucceed()).show();

        }





    }
    /**
     * 加载更多会使用到
     * */
    private RecordsItemDataModel   dataModel;
    private  AccountBillItemDataModel billItemDataModel;

    @Override
    public void paseJsonToBean(JSONObject obj) {
        /**
         * 根据type 来解析不同的数据
         * */
        try {
        if (type==0){
            //item 为 RecordsItemModel
            JSONArray jsonArray=obj.getJSONArray("Data");
            dataModel=new RecordsItemDataModel();
            dataModel.setData(JSON.parseArray(jsonArray.toString(),RecordsItemModel.class));
            dataModel.setTotal(obj.getInt("Total"));
            dataModel.setNext_id(obj.getInt("Next_id"));


            datalist=dataModel.getData();
        }else if(type==1){
            //item 为 AccountBillItemModel
//            dataAccountlist
            JSONArray jsonArray=obj.getJSONArray("Data");

            billItemDataModel=new AccountBillItemDataModel();
            billItemDataModel.setData(JSON.parseArray(jsonArray.toString(),AccountBillItemModel.class));
            billItemDataModel.setNext_id(obj.getInt("Next_id"));
            billItemDataModel.setTotal(obj.getInt("Total"));
            billItemDataModel.setTotalData(JSON.parseObject(obj.getString("TotalData"),AccountBillItemModel.class));


            dataAccountlist=billItemDataModel.getData();
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}

package com.johnson.jyvertification.Controller.Activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.GetInfoByIDService.GetInfoIntentService;
import com.johnson.jyvertification.NetWorking.utils.NetHasUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by johnsmac on 4/18/16.
 */
public class BaseActivity extends AppCompatActivity {
    BroadcastReceiver NetWorkingBroadcast;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        /**
         * 判断网络 是否有连接
         * */
       NetWorkingBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (PublicUtil.NETWORKING.equals(action)) {
                    new SweetAlertDialog(BaseActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("连接失败")
                            .setContentText("亲，网络连接了么？").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }).show();

                }

            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(PublicUtil.NETWORKING);
        registerReceiver(NetWorkingBroadcast, filter);


        GetInfoIntentService service = new GetInfoIntentService();
        service.startActionUndefind(this);

    }



    @Override
    protected void onDestroy() {
        if (NetWorkingBroadcast != null) {
            unregisterReceiver(NetWorkingBroadcast);
        }

        super.onDestroy();
    }
   /**
    * 备用*/
    private void quitSoftInput() {

        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

}

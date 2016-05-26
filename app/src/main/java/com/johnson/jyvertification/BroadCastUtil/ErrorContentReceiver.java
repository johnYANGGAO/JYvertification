package com.johnson.jyvertification.BroadCastUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Controller.Activitys.LoginActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ErrorContentReceiver extends BroadcastReceiver {
    public ErrorContentReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast. 注意  ACTION
        final Activity mContext = (Activity) context;
        if (PublicUtil.ERROR_ACTION.equals(intent.getAction())) {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(intent.getStringExtra(PublicUtil.ERROR_ACTION))
                    .setContentText("抱歉小主，没能给你想要的！").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    if (!(mContext instanceof LoginActivity)) {
                        mContext.finish();
                    }
                    sweetAlertDialog.dismissWithAnimation();
                }
            }).show();

        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}

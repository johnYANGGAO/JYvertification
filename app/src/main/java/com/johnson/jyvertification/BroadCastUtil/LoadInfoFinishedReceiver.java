package com.johnson.jyvertification.BroadCastUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Controller.Activitys.MainActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoadInfoFinishedReceiver extends BroadcastReceiver {

    SweetAlertDialog dialog;

    public LoadInfoFinishedReceiver(SweetAlertDialog loadingdialog) {

        dialog = loadingdialog;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();

        if (PublicUtil.LOAD_FINISHED_ACTION.equals(action)) {

            dialog.dismissWithAnimation();
            Log.i("MyTag","服务成功了");
            if (intent.getIntExtra("loaded", 1) == 0) {
                Log.i("MyTag","启动跳转");
                Intent loaded = new Intent(context, MainActivity.class);
                context.startActivity(loaded);
                Log.i("MyTag", "跳转完成了么");
            } else {
                Log.i("MyTag", "loaded为 0 没获得");
            }
        } else if (PublicUtil.ERROR_ACTION.equals(action)) {
            //由于服务方面的相关 和 可见页面的共享此 action 所以这里做下判断
            if (null==intent.getStringExtra("serviceErrorMsg")){
                return;
            }
            dialog.dismissWithAnimation();
            String msg = intent.getStringExtra("serviceErrorMsg");
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(msg)
                    .setContentText("Something went wrong!")
                    .show();

        }

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}

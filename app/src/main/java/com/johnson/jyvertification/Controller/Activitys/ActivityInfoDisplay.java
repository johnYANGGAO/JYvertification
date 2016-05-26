package com.johnson.jyvertification.Controller.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Controller.InfoDisplayController.AccountInfoFragment;
import com.johnson.jyvertification.Controller.InfoDisplayController.BasicInfoFragment;
import com.johnson.jyvertification.Controller.InfoDisplayController.MoudleInfoFragment;
import com.johnson.jyvertification.Model.InfoModels.AccountInfo;
import com.johnson.jyvertification.Model.InfoModels.BasicInfo;
import com.johnson.jyvertification.Model.InfoModels.ModuleInfo;
import com.johnson.jyvertification.Model.InfoModels.MyInfo;
import com.johnson.jyvertification.NetWorking.utils.HandleFile;
import com.johnson.jyvertification.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityInfoDisplay extends AppCompatActivity implements MoudleInfoFragment.OnListFragmentInteractionListener {

    /**
     * 统一请求 网络 获得整块 数据 ，根据传来的显示
     * 管理基本信息 ，组织信息 ，其他 fragment模块
     * 为解决 的是 整块数据要 事先 获取
     * 办法：当每次登录成功后，保存id ，并启动 服务 根据id 去获得数据 并保存。
     * 进到这一页面 要先做 个 是否含有数据判断，如有 ，则直接取 ，没有则 退出
     */

    private Fragment fragment = null;
    private boolean hasFile;
    private int type;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_displayer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);//setTitle一定要在此方法前 才能显示
        type = getIntent().getIntExtra("type", 0);
        isHasFile();
        if (hasFile) {
            getModelFromFileToShow();
        } else {
            showErrorAlertNote("未找到相关数据");
        }

    }

    private void showErrorAlertNote(String msg) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(msg)
                .setContentText("退出").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                finish();

            }
        }).show();

    }

    private HandleFile handleFile;

    private void isHasFile() {

        handleFile = HandleFile.getInstance(ActivityInfoDisplay.this);
        hasFile = handleFile.flagHasFile(PublicUtil.FILEOBJECT);

    }

    private MyInfo myInfo;

    private void getModelFromFileToShow() {

        try {
            myInfo = (MyInfo) handleFile.deSerialization(handleFile.getObject());
            if (myInfo != null) {

                disPlayViews(type);

            } else {
                showErrorAlertNote("抱歉 解析数据出错！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void disPlayViews(int type) {
        String title = null;
        if (0 == type) {
            title = "基本信息";
            BasicInfo basicInfo = myInfo.getBasic();
            fragment = BasicInfoFragment.newInstance(basicInfo);

        } else if (1 == type) {
            title = "账户信息";
            AccountInfo accountInfo = myInfo.getAccount();
            fragment = AccountInfoFragment.newInstance("1", accountInfo);
        } else if (2 == type) {
            title = "其他";
            List<ModuleInfo> moduleInfos = myInfo.getModules();
            fragment = MoudleInfoFragment.newInstance(moduleInfos);

        } else {
            showErrorAlertNote("请稍后......");
        }
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_info_display_fragment, fragment);
            ft.commit();
        }
    }

    @Override
    public void onListFragmentInteraction(ModuleInfo item) {

    }
}

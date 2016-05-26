package com.johnson.jyvertification.Controller.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.johnson.jyvertification.Consts.PublicUtil;
import com.johnson.jyvertification.Controller.CheckingFragment;
import com.johnson.jyvertification.Controller.Listeners.OnListFragmentInteractionListener;
import com.johnson.jyvertification.Controller.Listeners.OnSettingFragmentItemInteractionListener;
import com.johnson.jyvertification.Controller.MeFragment;
import com.johnson.jyvertification.Controller.RecordsFragment;
import com.johnson.jyvertification.Controller.SettingFragment;
import com.johnson.jyvertification.Model.CateGoryItme;
import com.johnson.jyvertification.Model.InfoModels.MyInfo;
import com.johnson.jyvertification.NetWorking.utils.HandleFile;
import com.johnson.jyvertification.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnListFragmentInteractionListener, MeFragment.OnFragmentInteractionListener, OnSettingFragmentItemInteractionListener {
    private boolean viewIsAtHome;
    private final int GETNAME = 6;
    private TextView my_account_name, my_email_address;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (GETNAME == msg.what) {
                Bundle bundle = msg.getData();
                String name = bundle.getString("name");
                String address = bundle.getString("address");
                Log.i("MyTag", "看看－－－" + name);
                Log.i("MyTag", "看看－－－" + address);
                my_account_name.setText(name);
                my_email_address.setText(address);

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.house);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isShown()) {
                    Log.i("MyTag", "开启状态的");
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        my_account_name = (TextView) view.findViewById(R.id.my_account_name);
        my_email_address = (TextView) view.findViewById(R.id.my_email_address);
        navigationView.setNavigationItemSelectedListener(this);
        getCheckList();
        getRecordsList();
        displayView(R.id.nav_checking);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initAccountName();
            }
        }).start();
    }

    private String company_name, email_address;

    private void initAccountName() {

        HandleFile handleFile = HandleFile.getInstance(this);
        if (handleFile.flagHasFile(PublicUtil.FILEOBJECT)) {
            try {
                MyInfo myInfo = (MyInfo) handleFile.deSerialization(handleFile.getObject());
                if (myInfo == null) {
                    return;
                }
                company_name = myInfo.getBasic().getCompanyName();
                email_address = myInfo.getBasic().getContactEmail();
                Log.i("MyTag", "---->>>" + company_name);
                Log.i("MyTag", "---->>>" + email_address);
                if (company_name == null) {
                    company_name = "未设置公司名称";
                }
                if (email_address == null) {
                    email_address = "未设置邮箱";
                }
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("name", company_name);
                bundle.putString("address", email_address);
                msg.setData(bundle);
                msg.what = GETNAME;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtHome) { //if the current view is not the News fragment
            displayView(R.id.nav_checking); //display the first fragment
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("确定退出吗?")
                    .setConfirmText("好!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            //If view is in News fragment, exit application
                            moveTaskToBack(true);
                        }
                    }).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("确定吗?")
                    .setContentText("退出喽!")
                    .setConfirmText("确定!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    moveTaskToBack(true);
                }
            }).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_checking:

                fragment = CheckingFragment.newInstance("CheckFragment", checklist);
                title = "查询";
                viewIsAtHome = true;
                break;
            case R.id.nav_records:

                fragment = RecordsFragment.newInstance("RecordsFragment", recordslist);
                title = "记录";
                viewIsAtHome = false;
                break;
            case R.id.nav_setting:
                fragment = new SettingFragment();
                title = "设置";
                viewIsAtHome = false;
                break;
            case R.id.nav_aboutMe:
                fragment = new MeFragment();
                title = "我";
                viewIsAtHome = false;
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    /**
     * 模拟数据1是查询2是记录
     */

    List<CateGoryItme> checklist = new ArrayList();
    List<CateGoryItme> recordslist = new ArrayList();

    public List getCheckList() {
        CateGoryItme a = new CateGoryItme();
        a.setContent("身份核查");
        a.setId(0);
        CateGoryItme b = new CateGoryItme();
        b.setContent("银行卡核查");
        b.setId(1);

        checklist.add(a);
        checklist.add(b);

        return checklist;

    }

    public List getRecordsList() {

        CateGoryItme ac = new CateGoryItme();
        ac.setContent("核查记录");
        ac.setId(0);
        CateGoryItme bc = new CateGoryItme();
        bc.setContent("账单");
        bc.setId(1);

        recordslist.add(ac);
        recordslist.add(bc);

        return recordslist;
    }

    /**
     * 和含有List的Fragment通信
     */
    @Override
    public void onListFragmentInteraction(CateGoryItme positionitme) {
        Log.i("MyTag", "clicked the " + positionitme.getContent());
        String title = positionitme.getContent();
        if ("身份核查".equals(title) || "银行卡核查".equals(title)) {
            Intent intent = new Intent(MainActivity.this, ActivityVertification.class);
            intent.putExtra("type", positionitme.getId());
            startActivity(intent);
        } else {
            /**
             *跳转到 登录条件核查
             * */
            Log.i("MyTag", "点击的是 " + positionitme.getId());

            Intent intent = new Intent(MainActivity.this, ActivityCheckingLogin.class);
            intent.putExtra("type", positionitme.getId());
            startActivity(intent);
        }

    }

    /**
     * 和MeFragment通信
     */
    @Override
    public void onFragmentInteraction(String tag) {
        int type = 0;
        if ("base_info".equals(tag)) {
            Log.i("MyTag", "clicked  base_info");
            type = 0;
        } else if ("firm_info".equals(tag)) {
            Log.i("MyTag", "clicked  firm_info");
            type = 1;
        } else if ("contact".equals(tag)) {
            Log.i("MyTag", "clicked  contact");
//            type = 2;
        } else if ("extra".equals(tag)) {
            Log.i("MyTag", "clicked  extra");
            type = 2;

        }
        Intent intent = new Intent(MainActivity.this, ActivityInfoDisplay.class);
        intent.putExtra("type", type);
        startActivity(intent);

    }

    /**
     * 和SettinFragment通信
     */
    @Override
    public void onSettingFragmentItemInteraction(String tag) {
        /**
         * 为了网络请求简洁  这里就不用FragmentActivity 管理了，直接用 activity
         * */
        if ("modify_password".equals(tag)) {
            Log.i("MyTag", "clicked  modify_password");
            Intent intent = new Intent(MainActivity.this, ActivitySettingModifyPassword.class);
            startActivity(intent);
        } else if ("update_user".equals(tag)) {
            Log.i("MyTag", "clicked  update_user");
        } else if ("create_user_plate".equals(tag)) {
            Log.i("MyTag", "clicked  create_user_plate");
            Intent intent = new Intent(MainActivity.this, ActivityCreateUser.class);
            startActivity(intent);
        } else if ("about_me".equals(tag)) {
            Log.i("MyTag", "clicked  about_me");
        }
    }
}

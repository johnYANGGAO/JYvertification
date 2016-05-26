package com.johnson.jyvertification.Controller.Activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.johnson.jyvertification.NetWorking.TimeUtil;
import com.johnson.jyvertification.R;

import java.util.Date;

/**
 * A login screen that offers login via email/password.
 */
public class ActivityCheckingLogin extends AppCompatActivity {

    // UI references.

    private EditText Login_name, UserName, CardNo, StartDate, EndDate;
    private View mProgressView;
    private View mLoginFormView;
    private String TAG = "ActivityCheckingLogin";
    private Button transfer_button;
    private TextView start_date_option, end_date_option;
    private TimePickerView pvTime;
    private Boolean begin = false;
    private TextInputLayout login_name_input_layout, username_input_type, carno_input_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_login);
        initViews();
        Login_name = (EditText) findViewById(R.id.Login_name);
        UserName = (EditText) findViewById(R.id.UserName);
        CardNo = (EditText) findViewById(R.id.CardNo);
        EndDate = (EditText) findViewById(R.id.EndDate);
        StartDate = (EditText) findViewById(R.id.StartDate);
        start_date_option = (TextView) findViewById(R.id.start_date_option);
        end_date_option = (TextView) findViewById(R.id.end_date_option);
        final int narrow = getIntent().getIntExtra("type", 0);
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                Log.i(TAG, "system time is : " + date + "");
                String dateString = TimeUtil.dateToStringSimple(date);
                Log.i(TAG, "format time is : " + dateString);
                if (begin) {
                    StartDate.setText(dateString);
                } else {
                    EndDate.setText(dateString);
                }

            }
        });
        if (0 == narrow) {

        } else if (1 == narrow) {
            setViewGone(login_name_input_layout);
            setViewGone(username_input_type);
            setViewGone(carno_input_type);

        }


        start_date_option.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "CLICKED THE  START DATE");
                pvTime.show();
                begin = true;
            }
        });
        end_date_option.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "CLICKED THE  END DATE");
                pvTime.show();
                begin = false;
            }
        });
        transfer_button = (Button) findViewById(R.id.transfer_button);
        transfer_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(narrow);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        initDateText();
    }

    private void setViewGone(View view) {

        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }

    }

    private void initViews() {
        login_name_input_layout = (TextInputLayout) findViewById(R.id.login_name_input_layout);
        username_input_type = (TextInputLayout) findViewById(R.id.username_input_type);
        carno_input_type = (TextInputLayout) findViewById(R.id.carno_input_type);
    }

    private void setNullError() {
        Login_name.setError(null);
        CardNo.setError(null);
        UserName.setError(null);
        EndDate.setError(null);
        StartDate.setError(null);
    }


    private void attemptLogin(int type) {

        // Reset errors.

        setNullError();

        // Store values at the time of the login attempt.
        String name_login = Login_name.getText().toString();
        String nameUser = UserName.getText().toString();
        String noCard = CardNo.getText().toString();
        StringBuffer dateStart = new StringBuffer();
        dateStart.append(StartDate.getText().toString()).append("T00:00:00");
        StringBuffer dateEnd = new StringBuffer();
        dateEnd.append(EndDate.getText().toString()).append("T00:00:00");

        boolean cancel = false;
        View focusView = null;
        if (type == 0) {
            //可选项
//            if (!TextUtils.isEmpty(name_login)) {
//                Login_name.setError("");
//                focusView = Login_name;
//                cancel = true;
//            }

            if (TextUtils.isEmpty(nameUser)) {
                UserName.setError("客户名不能为空");
                focusView = UserName;
                cancel = true;
            } else if (TextUtils.isEmpty(noCard)) {
                CardNo.setError("身份证号不能为空");
                focusView = CardNo;
                cancel = true;
            } else if (TextUtils.isEmpty(dateStart)) {
                StartDate.setError("起始时间不能为空");
                focusView = StartDate;
                cancel = true;
            } else if (TextUtils.isEmpty(dateEnd)) {
                EndDate.setError("终止时间不能为空");
                focusView = EndDate;
                cancel = true;
            }
        } else if (type == 1) {

            if (TextUtils.isEmpty(dateStart)) {
                StartDate.setError(getString(R.string.error_field_required));
                focusView = StartDate;
                cancel = true;
            } else if (TextUtils.isEmpty(dateEnd)) {
                EndDate.setError(getString(R.string.error_field_required));
                focusView = EndDate;
                cancel = true;
            }

        }
        if (cancel) {
            focusView.requestFocus();
        } else {

            Intent intent = new Intent(ActivityCheckingLogin.this, ActivityRecordsList.class);
            Bundle bundle = new Bundle();

            if (type == 0) {
                bundle.putString("name_login", name_login);
                bundle.putString("nameUser", nameUser);
                bundle.putString("noCard", noCard);
                bundle.putString("dateStart", dateStart.toString());
                bundle.putString("dateEnd", dateEnd.toString());
            } else if (type == 1) {
                bundle.putString("dateStart", dateStart.toString());
                bundle.putString("dateEnd", dateEnd.toString());
            }

            intent.putExtras(bundle);
            intent.putExtra("type", type);
            startActivity(intent);

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

    private void initDateText() {
        String dateString = TimeUtil.dateToStringSimple(new Date());
        StartDate.setText(dateString);
        EndDate.setText(dateString);

    }
}



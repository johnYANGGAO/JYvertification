package com.johnson.jyvertification.Controller.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dpizarro.uipicker.library.picker.PickerUI;
import com.johnson.jyvertification.Model.TransferData.CreateUserDataModel;
import com.johnson.jyvertification.R;

import java.util.Arrays;
import java.util.List;

/**
 * PickerUI mPickerUI = (PickerUI) findViewById(R.id.picker_ui_view);
 * <p/>
 * List<String> options = Arrays.asList(getResources().getStringArray(R.array.months));
 * <p/>
 * PickerUISettings pickerUISettings = new PickerUISettings.Builder()
 * .withItems(options)
 * .withBackgroundColor(getRandomColor())
 * .withAutoDismiss(true)
 * .withItemsClickables(false)
 * .withUseBlur(false)
 * .build();
 * <p/>
 * mPickerUI.setItems(this, options);
 * mPickerUI.setSettings(pickerUISettings);
 */
public class ActivityCreateUserFragment extends Fragment {


    private EditText create_user_name, create_user_password, create_user_password_confirm;

    private EditText create_user_roler, create_user_status;

    private TextView create_user_option_roler, create_user_option_status, create_user_sure;
    private static OnCreateUserPressSureListener pressButtonLisener;

    private PickerUI pickerUI;
    private String TAG = ActivityCreateUserFragment.class.getSimpleName();

    public ActivityCreateUserFragment() {
    }

//    public static ActivityCreateUserFragment newInstance(String arg) {
//
//        ActivityCreateUserFragment fragment = new ActivityCreateUserFragment();
//        Bundle args = new Bundle();
////        args.putString("", arg);
//        fragment.setArguments(args);
//
//        return fragment;
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity_create_user, container, false);
        initViews(view);
        return view;
    }

    private String name, password, password_confirm, status, roles;
    private int whichOptin;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pickerUI.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Log.i(TAG, valueResult);

                if (whichOptin == 0) {
                    create_user_roler.setText(valueResult);
                } else if (whichOptin == 1) {
                    create_user_status.setText(valueResult);
                }
            }
        });


        create_user_option_roler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> options = Arrays.asList(getResources().getStringArray(R.array.roles));
                pickerUI.setItems(getActivity(), options);
                pickerUI.slide();
                whichOptin = 0;
            }
        });

        create_user_option_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> options = Arrays.asList(getResources().getStringArray(R.array.status));
                pickerUI.setItems(getActivity(), options);
                pickerUI.slide();
                whichOptin = 1;
            }
        });
        create_user_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetError();
                CreateUserDataModel model = new CreateUserDataModel();
                name = getString(create_user_name);
                password = getString(create_user_password);
                password_confirm = getString(create_user_password_confirm);
                status = getString(create_user_status);
                roles = getString(create_user_roler);
                View focus = null;
                boolean cancel = false;
                if (isUnValid(name)) {
                    create_user_name.setError("登录名不能为空");
                    cancel = true;
                    focus = create_user_name;

                } else if (isUnValid(password)) {
                    create_user_password.setError("密码不能为空");
                    cancel = true;
                    focus = create_user_password;

                } else if (isUnValid(status)) {
                    create_user_status.setError("请选择用户状态");
                    cancel = true;
                    focus = create_user_status;

                } else if (isUnValid(roles)) {
                    create_user_roler.setError("请选择用户角色");
                    focus = create_user_roler;
                    cancel=true;
                } else if (isUnValid(password_confirm) || !password.equals(password_confirm)) {

                    showAlert("请确认密码是否一致 ！");
                    focus = create_user_password_confirm;
                    cancel = true;
                }
                if (cancel) {
                    focus.requestFocus();
                } else {

                    model.setName(name);
                    model.setPassword(password);
                    model.setRoler(roles);
                    model.setStatus(status);

                    pressButtonLisener.pressButton(model);
                }
            }
        });


    }

    private String getString(TextView view) {

        return view.getEditableText().toString();
    }

    private void resetError() {

        create_user_name.setError(null);
        create_user_password.setError(null);
        create_user_password_confirm.setError(null);
        create_user_status.setError(null);
        create_user_roler.setError(null);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCreateUserPressSureListener) {
            pressButtonLisener = (OnCreateUserPressSureListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implements OnCreateUserPressSureListener");
        }

    }

    private void initViews(View view) {

        create_user_sure = (TextView) view.findViewById(R.id.create_user_sure);
        create_user_name = (EditText) view.findViewById(R.id.create_user_name);
        create_user_password = (EditText) view.findViewById(R.id.create_user_password);
        create_user_password_confirm = (EditText) view.findViewById(R.id.create_user_password_confirm);
        create_user_roler = (EditText) view.findViewById(R.id.create_user_roler);
        create_user_option_roler = (TextView) view.findViewById(R.id.create_user_option_roler);
        create_user_status = (EditText) view.findViewById(R.id.create_user_status);
        create_user_option_status = (TextView) view.findViewById(R.id.create_user_option_status);
        pickerUI = (PickerUI) view.findViewById(R.id.picker_ui_view);

    }

    public interface OnCreateUserPressSureListener {

        void pressButton(CreateUserDataModel item);

    }

    public void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).create().show();

    }

    public boolean isUnValid(String text) {

        if (TextUtils.isEmpty(text)) {
            return true;
        }
        return false;

    }

}

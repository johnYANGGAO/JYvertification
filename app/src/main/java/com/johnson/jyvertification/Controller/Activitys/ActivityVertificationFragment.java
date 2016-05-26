package com.johnson.jyvertification.Controller.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnson.jyvertification.Controller.Listeners.OnVertificationCallBackListener;
import com.johnson.jyvertification.NetWorking.utils.IDCardValidate;
import com.johnson.jyvertification.NetWorking.utils.NoteIDError;
import com.johnson.jyvertification.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActivityVertificationFragment extends Fragment {

    private EditText account_name;
    private EditText identify;
    private EditText credit_card;
    private EditText cell_phone;
    private TextView sure;
    private LinearLayout layout_cd;
    private OnVertificationCallBackListener mCallBack;
    private NoteIDError noteIDError;

    public ActivityVertificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_activity_vertification, container, false);
        layout_cd = (LinearLayout) contentView.findViewById(R.id.layout_credit_card);

        if (getActivity().getIntent().getIntExtra("type", 0) == 0) {
            layout_cd.setVisibility(View.GONE);
        } else {
            layout_cd.setVisibility(View.VISIBLE);
        }
        account_name = (EditText) contentView.findViewById(R.id.account_name);
        identify = (EditText) contentView.findViewById(R.id.identify);
        credit_card = (EditText) contentView.findViewById(R.id.credit_card);
        cell_phone = (EditText) contentView.findViewById(R.id.cell_phone);
        sure = (TextView) contentView.findViewById(R.id.sure);

        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isValid(account_name)) {
                    showAlert("姓名不能为空");
                    return;
                }
                if (!isValid(identify)) {
                    showAlert("身份证号不能为空");
                    return;
                }
                /**
                 * 验证身份证号
                 * */
                if (!IDCardValidate.validate_effective(identify.getEditableText().toString(), noteIDError)) {
                    Log.i("MyTag", "return start ---->");
                    return;
                }
                Log.i("MyTag", "return end ? COME ON ,NO WAY ? ");
                if (layout_cd.getVisibility() == View.VISIBLE) {

                    if (!isValid(credit_card)) {
                        showAlert("银行卡号不能为空");
                        return;
                    }
                    if (!isValid(cell_phone)) {
//                        showAlert("请输入在银行柜台办理卡时预留的手机号码");
//                        return;
                    }

                    mCallBack.onVertificationCallBack(
                            account_name.getEditableText().toString(),
                            identify.getEditableText().toString(),
                            credit_card.getEditableText().toString(),
                            cell_phone.getEditableText().toString());

                } else {
                    Log.i("MyTag", "return end ? WHAT ? ");
                    mCallBack.onVertificationCallBack(
                            account_name.getEditableText().toString(),
                            identify.getEditableText().toString());
                }
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnVertificationCallBackListener ||
                context instanceof NoteIDError) {
            mCallBack = (OnVertificationCallBackListener) context;

        }
        if (context instanceof NoteIDError) {
            noteIDError = (NoteIDError) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnVertificationCallBackListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack = null;
        noteIDError = null;
    }


    public void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).create().show();

    }

    public boolean isValid(EditText text) {

        if (TextUtils.isEmpty(text.getEditableText().toString())) {
            return false;
        }
        return true;

    }


}

package com.johnson.jyvertification.Controller.InfoDisplayController;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnson.jyvertification.Model.InfoModels.AccountInfo;
import com.johnson.jyvertification.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountInfoFragment extends Fragment {


    private static final String TAG = "AccountInfoFragment";
    private static AccountInfo accountInfo;
    private String flag;


    public AccountInfoFragment() {
        // Required empty public constructor
    }

    public static AccountInfoFragment newInstance(String param1, AccountInfo aCcount) {
        AccountInfoFragment fragment = new AccountInfoFragment();
        Bundle args = new Bundle();
        args.putString(TAG, param1);
        accountInfo = aCcount;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flag = getArguments().getString(TAG);
        }
    }

    /**
     * * "Beforehand": 1,
     * "Deposit": 2.1,
     * "Money": 3.1,
     * "TotalExpeditureMoney": 4.1,
     * "WarnLimit": 5.1,
     * "IsOpenWarn": 6,
     * "Phone": [
     * "sample string 1",
     * "sample string 2"
     * ],
     * "Email": [
     * "sample string 1",
     * "sample string 2"
     * ]
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_account_info, container, false);
        TextView textView = (TextView) contentView.findViewById(R.id.account_content);
        int indicator = accountInfo.getIsOpenWarn();
        String warn;
        if (indicator == 1) {
            warn = "开启短信预警";
        } else if (indicator == 2) {
            warn = "开启邮箱预警";
        } else {
            warn = "预警关闭";

        }
        String strContent = "\n" + "用户付费类型 : " + (accountInfo.getBeforehand() == 1 ? "预付费" : "后付费") + "\n\n" + "保证金 : " +
                accountInfo.getDeposit() + "元" + "\n\n" + "公司账户余额 : " + accountInfo.getMoney() + "元" + "\n\n" +
                "总共消费(累计支出) : " + accountInfo.getTotalExpeditureMoney() + "元" + "\n\n" +
                "余额预警额度 : " + accountInfo.getWarnLimit() + "元" + "\n\n" + "预警状态 : " + warn + "\n\n"
                + getPhones() + getEmails();

        textView.setText(strContent);

        return contentView;
    }

    private String getPhones() {

        StringBuffer stringBuffer = new StringBuffer();
        int length = accountInfo.getPhone().length;
        if (length > 0) {
            stringBuffer.append("用于接收余额预警的手机号码 : ");
            for (int i = 0; i < length; i++) {

                stringBuffer.append(accountInfo.getPhone()[i] + "\n");
            }


        } else {
            stringBuffer.append("未设置\n");

        }
        return stringBuffer.toString();

    }

    private String getEmails() {
        StringBuffer stringBuffer = new StringBuffer();
        int length = accountInfo.getEmail().length;
        if (length > 0) {
            stringBuffer.append("用于接收余额预警的邮箱 : ");
            for (int i = 0; i < length; i++) {

                stringBuffer.append(accountInfo.getEmail()[i] + "\n");
            }

        } else {
            stringBuffer.append("未设置");

        }
        return stringBuffer.toString();


    }
}

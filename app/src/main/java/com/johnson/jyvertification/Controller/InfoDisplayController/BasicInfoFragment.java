package com.johnson.jyvertification.Controller.InfoDisplayController;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnson.jyvertification.Model.InfoModels.BasicInfo;
import com.johnson.jyvertification.R;


public class BasicInfoFragment extends Fragment {


    private static BasicInfo basicInfo;

    public BasicInfoFragment() {

    }

    public static BasicInfoFragment newInstance(BasicInfo mBasicInfo) {
        BasicInfoFragment fragment = new BasicInfoFragment();
        Bundle args = new Bundle();
        basicInfo = mBasicInfo;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_basic_info, container, false);
        TextView content_textView = (TextView) contentView.findViewById(R.id.basic_content);

        String basicContentText = "\n" + "公司名称 : " + basicInfo.getCompanyName() + "\n\n" + "状态 : " + (basicInfo.getStatus() == 1 ? "正常" : "冻结") +
                "\n\n" + "省份  : " + basicInfo.getProvince() + "\n\n" + "城市 : " + basicInfo.getCity() +
                "\n\n" + "地址 : " + basicInfo.getAddress() + "\n\n" + "联系人 : " + basicInfo.getContactPerson() +
                "\n\n" + "联系电话 : " + getContactorPhone() + "联系Email : " + basicInfo.getContactEmail() + "\n\n" + "公司网址  : " + basicInfo.getCompanyURL();

        content_textView.setText(basicContentText);

        return contentView;
    }

    public String getContactorPhone() {

        StringBuffer buffer = new StringBuffer();

        int length = basicInfo.getContactTel().length;
        if (length > 0) {
            String[] str = basicInfo.getContactTel();
            for (int i = 0; i < length; i++) {

                buffer.append(str[i] + "\n");

            }

        } else {

            buffer.append("未设置" + "\n");

        }

        return buffer.toString();
    }


}

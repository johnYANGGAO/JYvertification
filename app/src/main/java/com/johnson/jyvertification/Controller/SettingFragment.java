package com.johnson.jyvertification.Controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.johnson.jyvertification.Controller.Listeners.OnSettingFragmentItemInteractionListener;
import com.johnson.jyvertification.R;

public class SettingFragment extends Fragment {
    private static final String ARG_PARAM1 = "SettingFragment";

    private String mParam1;

    private OnSettingFragmentItemInteractionListener mListener;

    public static SettingFragment newInstance(String param1) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final RelativeLayout modify_password,update_user,create_user_plate,about_me;
        View contentView=inflater.inflate(R.layout.fragment_setting, container, false);

        modify_password=(RelativeLayout)contentView.findViewById(R.id.modify_password);
        update_user=(RelativeLayout)contentView.findViewById(R.id.update_user);
        create_user_plate=(RelativeLayout)contentView.findViewById(R.id.create_user_plate);
        about_me=(RelativeLayout)contentView.findViewById(R.id.about_me);

        modify_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed("modify_password");
            }
        });
        update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed("update_user");
            }
        });
        create_user_plate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed("create_user_plate");
            }
        });
        about_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed("about_me");
            }
        });

        return contentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String tag) {
        if (mListener != null) {
            mListener.onSettingFragmentItemInteraction(tag);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingFragmentItemInteractionListener) {
            mListener = (OnSettingFragmentItemInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSettingFragmentItemInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}

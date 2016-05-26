package com.johnson.jyvertification.Controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnson.jyvertification.Controller.Adapters.CheckingItemRecyclerViewAdapter;
import com.johnson.jyvertification.Controller.Listeners.OnListFragmentInteractionListener;
import com.johnson.jyvertification.Model.CateGoryItme;
import com.johnson.jyvertification.R;

import java.util.ArrayList;
import java.util.List;

public class RecordsFragment extends Fragment {


    private static final String TAG = "CheckingFragment";
    private static List<CateGoryItme> data= new ArrayList();
    private String flag = null;
    private OnListFragmentInteractionListener mListener;


    public RecordsFragment() {
    }


    public  static  RecordsFragment newInstance(String tag,List serverData) {

        RecordsFragment fragment = new RecordsFragment();
        Bundle args = new Bundle();
        args.putString(TAG, tag);
        data=serverData;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checking_fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new CheckingItemRecyclerViewAdapter(data, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}

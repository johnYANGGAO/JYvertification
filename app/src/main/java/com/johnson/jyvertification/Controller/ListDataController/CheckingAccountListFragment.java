package com.johnson.jyvertification.Controller.ListDataController;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.johnson.jyvertification.Controller.Listeners.OnListCheckingIDFragmentInteractionListener;
import com.johnson.jyvertification.Model.AccountBillItemModel;
import com.johnson.jyvertification.Model.RecordsItemModel;
import com.johnson.jyvertification.R;
import java.util.ArrayList;
import java.util.List;


public class CheckingAccountListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String TAG = "CheckingAccountListFragment";
    // TODO: Customize parameters
    private static String flag=null;
    private OnListCheckingIDFragmentInteractionListener mListener;
    private static List<AccountBillItemModel> values =new ArrayList();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CheckingAccountListFragment() {
    }


    public static CheckingAccountListFragment newInstance(List<AccountBillItemModel> data) {
        CheckingAccountListFragment fragment = new CheckingAccountListFragment();
        Bundle args = new Bundle();
        args.putString(TAG, flag);
        values=data;
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
        View view = inflater.inflate(R.layout.fragment_checkingaccountlist_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new CheckingAccountListRecyclerViewAdapter(values, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListCheckingIDFragmentInteractionListener) {
            mListener = (OnListCheckingIDFragmentInteractionListener) context;
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

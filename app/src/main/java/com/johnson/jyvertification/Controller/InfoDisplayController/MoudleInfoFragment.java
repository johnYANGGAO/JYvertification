package com.johnson.jyvertification.Controller.InfoDisplayController;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnson.jyvertification.Model.InfoModels.ModuleInfo;
import com.johnson.jyvertification.R;

import java.util.List;


public class MoudleInfoFragment extends Fragment {

    private static final String TAG = "MoudleInfoFragment";
    private static List<ModuleInfo> data;
    private OnListFragmentInteractionListener mListener;


    public MoudleInfoFragment() {
    }

    public static MoudleInfoFragment newInstance(List<ModuleInfo> moduleInfos) {
        MoudleInfoFragment fragment = new MoudleInfoFragment();
        Bundle args = new Bundle();

        data = moduleInfos;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.moudle_info_fragment_item_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setAdapter(new MoudleInfoRecyclerViewAdapter(data, mListener));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

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


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ModuleInfo item);
    }
}

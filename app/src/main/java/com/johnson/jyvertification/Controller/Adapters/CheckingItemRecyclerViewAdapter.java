package com.johnson.jyvertification.Controller.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnson.jyvertification.Controller.Listeners.OnListFragmentInteractionListener;
import com.johnson.jyvertification.Model.CateGoryItme;
import com.johnson.jyvertification.R;

import java.util.List;


public class CheckingItemRecyclerViewAdapter extends RecyclerView.Adapter<CheckingItemRecyclerViewAdapter.ViewHolder> {

    private final List<CateGoryItme> data;
    private final OnListFragmentInteractionListener mListener;

    public CheckingItemRecyclerViewAdapter(List<CateGoryItme> items, OnListFragmentInteractionListener listener) {
        data = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checking_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.item=data.get(position);
        holder.title.setText(data.get(position).getContent());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView title;
        public CateGoryItme item;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.check_category_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + title.getText() + "'";
        }
    }
}

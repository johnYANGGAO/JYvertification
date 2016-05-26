package com.johnson.jyvertification.Controller.ListDataController;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnson.jyvertification.Controller.Listeners.OnListCheckingIDFragmentInteractionListener;
import com.johnson.jyvertification.Model.RecordsItemModel;
import com.johnson.jyvertification.R;

import java.util.List;


public class CheckingIDListRecyclerViewAdapter extends RecyclerView.Adapter<CheckingIDListRecyclerViewAdapter.ViewHolder> {

    private final List<RecordsItemModel> mValues;
    private final OnListCheckingIDFragmentInteractionListener mListener;

    public CheckingIDListRecyclerViewAdapter(List<RecordsItemModel> items, OnListCheckingIDFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_checkingidlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.records_id_time.setText(holder.mItem.getInsertDate());
        holder.records_id_content.setText(holder.mItem.getUserName());
        holder.records_project_type.setText(holder.mItem.getModule());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView records_id_time;
        public final TextView records_id_content;
        public final TextView records_project_type;
        public RecordsItemModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            records_project_type=(TextView)view.findViewById(R.id.records_project_type);
            records_id_time = (TextView) view.findViewById(R.id.records_id_time);
            records_id_content = (TextView) view.findViewById(R.id.records_id_content);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}

package com.johnson.jyvertification.Controller.ListDataController;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnson.jyvertification.Controller.Listeners.OnListCheckingIDFragmentInteractionListener;
import com.johnson.jyvertification.Model.AccountBillItemModel;
import com.johnson.jyvertification.Model.RecordsItemModel;
import com.johnson.jyvertification.R;

import java.util.List;


public class CheckingAccountListRecyclerViewAdapter extends RecyclerView.Adapter<CheckingAccountListRecyclerViewAdapter.ViewHolder> {

    private final List<AccountBillItemModel> mValues;
    private final OnListCheckingIDFragmentInteractionListener mListener;

    public CheckingAccountListRecyclerViewAdapter(List<AccountBillItemModel> items, OnListCheckingIDFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_checkingaccountlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.records_account_time.setText(mValues.get(position).getViewDate());
        holder.records_account_content.setText(mValues.get(position).getTotalMoney()+"元");
        holder.records_check_type.setText(mValues.get(position).getVIDCardCount());

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
        public final TextView records_account_time;
        public final TextView records_account_content;
        public final TextView records_check_type;
        public AccountBillItemModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            records_check_type=(TextView)view.findViewById(R.id.records_check_type);
            records_account_time = (TextView) view.findViewById(R.id.records_account_time);
            records_account_content = (TextView) view.findViewById(R.id.records_account_content);
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}

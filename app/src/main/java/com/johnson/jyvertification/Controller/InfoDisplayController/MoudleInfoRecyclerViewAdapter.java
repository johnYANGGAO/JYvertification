package com.johnson.jyvertification.Controller.InfoDisplayController;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.johnson.jyvertification.Model.InfoModels.ModuleInfo;
import com.johnson.jyvertification.R;

import java.util.List;


public class MoudleInfoRecyclerViewAdapter extends RecyclerView.Adapter<MoudleInfoRecyclerViewAdapter.ViewHolder> {

    private final List<ModuleInfo> mValues;
    private String TAG="MoudleInfoRecyclerViewAdapter";
    private final MoudleInfoFragment.OnListFragmentInteractionListener mListener;



    public MoudleInfoRecyclerViewAdapter(List<ModuleInfo> items,MoudleInfoFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moudle_info_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.moudle_type.setText("类型 : " + holder.mItem.getType());
        holder.moudle_name.setText(holder.mItem.getName());
        holder.moudle_min_fee.setText(holder.mItem.getMinFee()+"元");

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
        Log.i(TAG, "数据长度"+mValues.size());
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView moudle_type,moudle_name,moudle_min_fee;
        public ModuleInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            moudle_type = (TextView) view.findViewById(R.id.moudle_type);
            moudle_name = (TextView) view.findViewById(R.id.moudle_name);
            moudle_min_fee=(TextView) view.findViewById(R.id.moudle_min_fee);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + moudle_name.getText() + "'";
        }
    }
}

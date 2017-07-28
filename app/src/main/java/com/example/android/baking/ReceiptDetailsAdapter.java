package com.example.android.baking;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.ReceiptData.Ingredient;
import com.example.android.baking.ReceiptData.ReceiptItem;
import com.example.android.baking.ReceiptData.Step;

import java.util.List;

public class ReceiptDetailsAdapter extends RecyclerView.Adapter<ReceiptDetailsAdapter.detailsViewHolder> {

    private List<Step> mStep;

    public ReceiptDetailsAdapter(List<Step> steps){
        mStep = steps;
    }

    @Override
    public ReceiptDetailsAdapter.detailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);

        return new detailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(detailsViewHolder holder, int position) {
        holder.mStepClass = mStep.get(position);
        holder.mShortDescripView.setText(mStep.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        int itemCount = (mStep == null? 0: mStep.size());
        return itemCount;
    }

    public class detailsViewHolder extends RecyclerView.ViewHolder{
        final View mDetailView;
        final TextView mShortDescripView;
        Step mStepClass;

        public detailsViewHolder(View itemView) {
            super(itemView);
            mDetailView = itemView;
            mShortDescripView = (TextView) itemView.findViewById(R.id.tv_step_shortDescription);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mShortDescripView.getText() + "'";
        }
    }
}

package com.example.android.baking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.ReceiptData.ReceiptItem;

import java.io.Serializable;
import java.util.List;


public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.ViewHolder> {

    private List<ReceiptItem> mReceipts;


    public ReceiptListAdapter(List<ReceiptItem> items) {
        mReceipts = items;
    }



    @Override
    public ReceiptListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receipt_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mReceipts.get(position);
        holder.mTitleView.setText(mReceipts.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();

                Intent intent = new Intent(context, ItemDetailActivity.class);

                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return  mReceipts == null? 0: mReceipts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
         final View mView;
         final TextView mTitleView;
         ReceiptItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.tv_title_receipt);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }

        }
    }


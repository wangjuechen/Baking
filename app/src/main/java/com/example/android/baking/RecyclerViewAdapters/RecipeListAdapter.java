package com.example.android.baking.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.ItemDetailActivity;
import com.example.android.baking.ItemDetailFragment;
import com.example.android.baking.R;
import com.example.android.baking.RecipeData.RecipeItem;

import java.util.List;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private List<RecipeItem> mRecipeList;


    public RecipeListAdapter(List<RecipeItem> items) {
        mRecipeList = items;
    }



    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_recipes_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mRecipeList.get(position);

        holder.mTitleView.setText(mRecipeList.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();

                Bundle bundle  = new Bundle();

                bundle.putParcelable(ItemDetailFragment.ARG_ITEM_ID, mRecipeList.get(position));

                Intent intent = new Intent(context, ItemDetailActivity.class);

                intent.putExtra(ItemDetailFragment.ARG_BUNDLE,bundle);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return  mRecipeList == null? 0: mRecipeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
         final View mView;
         final TextView mTitleView;
         RecipeItem mItem;

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


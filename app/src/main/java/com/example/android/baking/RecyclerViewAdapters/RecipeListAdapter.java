package com.example.android.baking.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
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

    private List<RecipeItem> mRecipes;


    public RecipeListAdapter(List<RecipeItem> items) {
        mRecipes = items;
    }



    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_recipes_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mRecipes.get(position);
        holder.mTitleView.setText(mRecipes.get(position).getName());

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
        return  mRecipes == null? 0: mRecipes.size();
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


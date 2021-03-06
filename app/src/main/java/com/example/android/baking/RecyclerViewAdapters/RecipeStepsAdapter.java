package com.example.android.baking.RecyclerViewAdapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.RecipeData.RecipeItem;
import com.example.android.baking.RecipeData.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.detailsViewHolder> {

    private Context mContext;
    private List<Step> mStepList;
    private RecipeItem mItem;

    public RecipeStepsAdapter(List<Step> steps, RecipeItem items, Context context) {
        mStepList = steps;
        mItem = items;
        mContext = context;
    }

    onStepsVideoFragment mFragmentListener;

    @Override
    public RecipeStepsAdapter.detailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_steps_content, parent, false);

        return new detailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final detailsViewHolder holder, final int position) {
        holder.mStepClass = mStepList.get(position);

        holder.mShortDescripeView.setText(mStepList.get(position).getShortDescription());

        holder.mThumbnailUrl = mStepList.get(position).getThumbnailURL();

        if (!TextUtils.isEmpty(holder.mThumbnailUrl)) {
            Picasso.with(mContext).load(holder.mThumbnailUrl).into(holder.mImageView);
        }

        holder.mStepsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFragmentListener.onStepsVideoTwoPane(holder.mStepClass, mStepList, mItem);

            }
        });
    }


    @Override
    public int getItemCount() {
        int itemCount = (mStepList == null ? 0 : mStepList.size());
        return itemCount;
    }

    public int getStepSize() {
        return mStepList.size();
    }

    public interface onStepsVideoFragment {
        void onStepsVideoTwoPane(Step step, List<Step> steps, RecipeItem items);
    }

    public void setmFragmentListener(onStepsVideoFragment onStepsVideoFragment) {
        this.mFragmentListener = onStepsVideoFragment;
    }

    public class detailsViewHolder extends RecyclerView.ViewHolder {
        final View mStepsView;
        final TextView mShortDescripeView;
        final ImageView mImageView;
        String mThumbnailUrl;
        Step mStepClass;

        public detailsViewHolder(View itemView) {
            super(itemView);
            mStepsView = itemView;
            mShortDescripeView = (TextView) itemView.findViewById(R.id.tv_step_shortDescription);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_step_thumbnail);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mShortDescripeView.getText() + "'";
        }
    }
}

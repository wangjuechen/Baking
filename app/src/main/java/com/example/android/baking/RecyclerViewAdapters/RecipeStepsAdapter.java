package com.example.android.baking.RecyclerViewAdapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.RecipeData.Step;
import com.example.android.baking.StepsDetailActivity;
import com.example.android.baking.StepsDetailFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.detailsViewHolder> {

    private List<Step> mStep;

    public RecipeStepsAdapter(List<Step> steps) {
        mStep = steps;
    }

    @Override
    public RecipeStepsAdapter.detailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_steps_content, parent, false);

        return new detailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final detailsViewHolder holder, final int position) {
        holder.mStepClass = mStep.get(position);
        holder.mShortDescripeView.setText(mStep.get(position).getShortDescription());


        holder.mStepsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Bundle bundle = new Bundle();

                //TODO: here need to modified
                Intent intent = new Intent(context, StepsDetailActivity.class);

                bundle.putString(StepsDetailFragment.STEP_DESCRIBE, holder.mStepClass.getDescription());

                bundle.putInt(StepsDetailFragment.STEP_ID, holder.mStepClass.getId());

                bundle.putString(StepsDetailFragment.STEP_URL, holder.mStepClass.getVideoURL());

                bundle.putInt(StepsDetailFragment.STEPS_SIZE, mStep.size());

                bundle.putParcelableArrayList(StepsDetailFragment.STEPS, (ArrayList<? extends Parcelable>) mStep);

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        int itemCount = (mStep == null ? 0 : mStep.size());
        return itemCount;
    }

    public int getStepSize() {
        return mStep.size();
    }

    public class detailsViewHolder extends RecyclerView.ViewHolder {
        final View mStepsView;
        final TextView mShortDescripeView;
        Step mStepClass;

        public detailsViewHolder(View itemView) {
            super(itemView);
            mStepsView = itemView;
            mShortDescripeView = (TextView) itemView.findViewById(R.id.tv_step_shortDescription);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mShortDescripeView.getText() + "'";
        }
    }
}

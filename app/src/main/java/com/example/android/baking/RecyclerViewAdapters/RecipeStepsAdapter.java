package com.example.android.baking.RecyclerViewAdapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.RecipeData.Step;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.detailsViewHolder> {

    private List<Step> mStep;

    public RecipeStepsAdapter(List<Step> steps){
        mStep = steps;
    }

    @Override
    public RecipeStepsAdapter.detailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_steps_content, parent, false);

        return new detailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(detailsViewHolder holder, int position) {
        holder.mStepClass = mStep.get(position);
        holder.mShortDescripeView.setText(mStep.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        int itemCount = (mStep == null? 0: mStep.size());
        return itemCount;
    }

    public class detailsViewHolder extends RecyclerView.ViewHolder{
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

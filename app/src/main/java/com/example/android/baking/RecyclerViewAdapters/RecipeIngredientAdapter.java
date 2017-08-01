package com.example.android.baking.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.RecipeData.Ingredient;
import com.example.android.baking.RecipeData.RecipeItem;

import java.util.List;


public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.ingredientViewHolder> {

    private List<Ingredient> mIngredient;
    private Context mContext;
    private RecipeItem mItem;

    public RecipeIngredientAdapter(List<Ingredient> ingredients , RecipeItem item){
        mIngredient = ingredients;
        mItem = item;
    }

    @Override
    public RecipeIngredientAdapter.ingredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ingredient_content, parent, false);

        return new ingredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ingredientViewHolder holder, int position) {
        holder.mIngredientClass = mItem.getIngredients().get(position);

        double quantity = holder.mIngredientClass.getQuantity();
        String measure = holder.mIngredientClass.getMeasure();
        String ingredient = holder.mIngredientClass.getIngredient();

        String placeholder = mContext.getString(R.string.placeholder);

        holder.mIngredientQuantity.setText(String.valueOf(quantity) + placeholder);
        holder.mIngredientMeasure.setText(measure + placeholder);
        holder.mIngredientName.setText(ingredient);

    }


    @Override
    public int getItemCount() {
        int itemCount = (mIngredient == null? 0: mIngredient.size());
        return itemCount;
    }

    public class ingredientViewHolder extends RecyclerView.ViewHolder{
        final View mIngredientView;
        final TextView mIngredientQuantity;
        final TextView mIngredientMeasure;
        final TextView mIngredientName;
        Ingredient mIngredientClass;

        public ingredientViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();

            mIngredientView = itemView;

            mIngredientQuantity = (TextView) itemView.findViewById(R.id.tv_ingredient_quantity);
            mIngredientMeasure = (TextView) itemView.findViewById(R.id.tv_ingredient_measure);
            mIngredientName = (TextView) itemView.findViewById(R.id.tv_ingredient_name);
        }
    }
}

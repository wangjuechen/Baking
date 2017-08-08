package com.example.android.baking.WidgetUtils;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baking.ItemDetailFragment;
import com.example.android.baking.R;
import com.example.android.baking.RecipeData.Ingredient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory{
    private Context mContext;
    private List<Ingredient> mIngredient = new ArrayList<>();

    public WidgetAdapter(Context context){
        this.mContext = context;
    }


    @Override
    public void onCreate() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(ItemDetailFragment.ARG_WIDGET_INGREDIENT, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String jsonString = sharedPreferences.getString(ItemDetailFragment.ARG_WIDGET_INGREDIENT_EDITOR, null);

        Ingredient[] ingredients = gson.fromJson(jsonString, Ingredient[].class);

        if (ingredients != null) {
            for (int i = 0; i < ingredients.length; i++) {
                mIngredient.add(ingredients[i]);
            }
        }
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mIngredient != null ? mIngredient.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {


        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_listview_content);

        rv.setTextViewText(R.id.widget_tv_ingredient_quantity, String.valueOf(mIngredient.get(position).getQuantity()));
        rv.setTextViewText(R.id.widget_tv_ingredient_measure, String.valueOf(mIngredient.get(position).getMeasure()));
        rv.setTextViewText(R.id.widget_tv_ingredient_name, String.valueOf(mIngredient.get(position).getIngredient()));

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {

        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

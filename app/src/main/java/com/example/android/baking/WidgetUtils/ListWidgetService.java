package com.example.android.baking.WidgetUtils;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baking.ItemDetailFragment;
import com.example.android.baking.R;
import com.example.android.baking.RecipeData.Ingredient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Ingredient> mIngredientLists = new ArrayList<Ingredient>();
    private Context mContext;
    private int mAppWidgetId;

    public ListViewRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(ItemDetailFragment.ARG_WIDGET_INGREDIENT, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String jsonString = sharedPreferences.getString(ItemDetailFragment.ARG_WIDGET_INGREDIENT_EDITOR, null);

        Ingredient[] ingredients = gson.fromJson(jsonString, Ingredient[].class);

        for (int i = 0; i < ingredients.length; i++) {
            mIngredientLists.add(ingredients[i]);
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
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_listview_content);
        rv.setTextViewText(R.id.widget_tv_ingredient_quantity, String.valueOf(mIngredientLists.get(position).getQuantity()));
        rv.setTextViewText(R.id.widget_tv_ingredient_measure, String.valueOf(mIngredientLists.get(position).getMeasure()));
        rv.setTextViewText(R.id.widget_tv_ingredient_name, String.valueOf(mIngredientLists.get(position).getIngredient()));


        // Next, set a fill-intent, which will be used to fill in the pending intent template
        // that is set on the collection view in StackWidgetProvider.
        Bundle extras = new Bundle();
        extras.putInt(BakingWidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        // Make it possible to distinguish the individual on-click
        // action of a given item
        rv.setOnClickFillInIntent(R.id.widget_tv_ingredient_quantity, fillInIntent);


        // Return the RemoteViews object.
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


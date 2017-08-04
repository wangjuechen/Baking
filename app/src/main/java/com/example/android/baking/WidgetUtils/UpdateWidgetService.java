package com.example.android.baking.WidgetUtils;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.android.baking.ItemDetailFragment;
import com.example.android.baking.R;
import com.example.android.baking.RecipeData.Ingredient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UpdateWidgetService extends Service {
    private List<Ingredient> mIngredientLists = new ArrayList<>();

    public UpdateWidgetService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        int[] allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        SharedPreferences sharedPreferences = this.getSharedPreferences(ItemDetailFragment.ARG_WIDGET_INGREDIENT, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String jsonString = sharedPreferences.getString(ItemDetailFragment.ARG_WIDGET_INGREDIENT_EDITOR, null);

        Ingredient[] ingredients = gson.fromJson(jsonString, Ingredient[].class);

        if (ingredients != null) {
            for (int i = 0; i < ingredients.length; i++) {
                mIngredientLists.add(ingredients[i]);
            }
        }
//      ComponentName thisWidget = new ComponentName(getApplicationContext(),
//              MyWidgetProvider.class);
//      int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
            // create some random data

            RemoteViews remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.widget_listview_content);

            remoteViews.setTextViewText(R.id.widget_tv_ingredient_quantity,
                    mIngredientLists.get(0).getIngredient());

            // Register an onClickListener
            Intent clickIntent = new Intent(this.getApplicationContext(),
                    ListWidgetService.class);

            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

            remoteViews.setRemoteAdapter(R.id.widget_listView, clickIntent);

            /*clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    allWidgetIds);*/

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getApplicationContext(), 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

//remoteViews.setOnClickPendingIntent(R.id.empty_view, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

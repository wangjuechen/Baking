package com.example.android.baking;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.RecipeData.RecipeItem;
import com.example.android.baking.RecipeData.Step;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.baking.StepsDetailFragment.STEP_DESCRIBE;
import static com.example.android.baking.StepsDetailFragment.STEP_ID;
import static com.example.android.baking.StepsDetailFragment.STEP_THUMBNAILURL;
import static com.example.android.baking.StepsDetailFragment.STEP_URL;

public class StepsDetailActivity extends AppCompatActivity implements StepsDetailFragment.OnFragmentInteractionListener {

    private int mStepId;
    private String mDetailedDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;
    private int mStepsSize;
    private List<Step> mStepList;
    private RecipeItem mRecipeItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();

            Bundle bundle = getIntent().getExtras();

            mRecipeItem = (RecipeItem) bundle.getSerializable(ItemDetailFragment.ARG_ITEM_ID);
            mStepList = bundle.getParcelableArrayList(StepsDetailFragment.STEPS);

            mVideoUrl = bundle.getString(STEP_URL);
            mDetailedDescription = bundle.getString(STEP_DESCRIBE);
            mStepId = bundle.getInt(STEP_ID, 0);
            mStepsSize = mStepList.size();
            mThumbnailUrl = bundle.getString(STEP_THUMBNAILURL);

            stepsDetailFragment.setItem(mRecipeItem);
            stepsDetailFragment.setStepList(mStepList);
            stepsDetailFragment.setStepId(mStepId);
            stepsDetailFragment.setDetailedDescription(mDetailedDescription);
            stepsDetailFragment.setVideoUrl(mVideoUrl);
            stepsDetailFragment.setStepsSize(mStepsSize);
            stepsDetailFragment.setThumbnailUrl(mThumbnailUrl);


            Bundle arguments = new Bundle();
            arguments.putString(STEP_ID,
                    getIntent().getStringExtra(STEP_ID));

            stepsDetailFragment.setArguments(arguments);

            setContentView(R.layout.activity_stpes_list);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_fragment_container, stepsDetailFragment)
                    .commit();
        }else{

            Bundle bundle = new Bundle();
            //TODO something wrong, item value is null, cant pass to here
            RecipeItem item = (RecipeItem) savedInstanceState.getSerializable(ItemDetailFragment.ARG_ITEM_ID);
            List<Step> step = savedInstanceState.getParcelableArrayList(STEP_ID);
            int id = savedInstanceState.getInt("ID");

            bundle.putSerializable(ItemDetailFragment.ARG_ITEM_ID, item);
            bundle.putParcelableArrayList(STEP_ID, (ArrayList<? extends Parcelable>) step);
            bundle.putInt("ID",id);

            Intent intent =  new Intent(this, ItemDetailActivity.class);

            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        SimpleExoPlayerView player = (SimpleExoPlayerView) findViewById(R.id.video_player_view);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            player.setLayoutParams(layoutParams);
        }
    }*/

    public void onFragmentInteraction(int stepID, List<Step> stepList) {

        StepsDetailFragment stepFragment = new StepsDetailFragment();

        mVideoUrl = stepList.get(stepID).getVideoURL();
        mDetailedDescription = stepList.get(stepID).getDescription();
        mStepsSize = stepList.size();

        stepFragment.setStepList(stepList);
        stepFragment.setStepId(stepID);
        stepFragment.setDetailedDescription(mDetailedDescription);
        stepFragment.setVideoUrl(mVideoUrl);
        stepFragment.setStepsSize(mStepsSize);
        stepFragment.setThumbnailUrl(mThumbnailUrl);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.steps_fragment_container, stepFragment)
                .commit();

    }
}

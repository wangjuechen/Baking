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
import static com.example.android.baking.StepsDetailFragment.STEP_URL;

public class StepsDetailActivity extends AppCompatActivity implements StepsDetailFragment.OnFragmentInteractionListener {

    private int mStepId;
    private String mDetailedDescription;
    private String mVideoUrl;
    private int mStepsSize;
    private List<Step> mSteps;
    private RecipeItem mItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();

            mItem = (RecipeItem) getIntent().getSerializableExtra("ITEM");
            mSteps = getIntent().getParcelableArrayListExtra(StepsDetailFragment.STEPS);

            mVideoUrl = getIntent().getStringExtra(STEP_URL);
            mDetailedDescription = getIntent().getStringExtra(STEP_DESCRIBE);
            mStepId = getIntent().getIntExtra(STEP_ID, 0);
            mStepsSize = mSteps.size();

            stepsDetailFragment.setItem(mItem);
            stepsDetailFragment.setStepList(mSteps);
            stepsDetailFragment.setStepId(mStepId);
            stepsDetailFragment.setDetailedDescription(mDetailedDescription);
            stepsDetailFragment.setVideoUrl(mVideoUrl);
            stepsDetailFragment.setStepsSize(mStepsSize);

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

            bundle.putSerializable(ItemDetailFragment.ARG_ITEM_ID, savedInstanceState.getSerializable(ItemDetailFragment.ARG_ITEM_ID));
            bundle.putParcelableArrayList(STEP_ID, savedInstanceState.getParcelableArrayList(STEP_ID));
            bundle.putInt("ID", savedInstanceState.getInt("ID"));

            Intent intent =  new Intent(this, ItemDetailActivity.class);

            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    public void onFragmentInteraction(int stepID, List<Step> stepList) {

        StepsDetailFragment stepFragment = new StepsDetailFragment();

        mVideoUrl = stepList.get(stepID).getVideoURL();
        mDetailedDescription = stepList.get(stepID).getDescription();
        mStepsSize = stepList.size();

        stepFragment.setItem(mItem);
        stepFragment.setStepList(stepList);
        stepFragment.setStepId(stepID);
        stepFragment.setDetailedDescription(mDetailedDescription);
        stepFragment.setVideoUrl(mVideoUrl);
        stepFragment.setStepsSize(mStepsSize);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.steps_fragment_container, stepFragment)
                .commit();

    }
}

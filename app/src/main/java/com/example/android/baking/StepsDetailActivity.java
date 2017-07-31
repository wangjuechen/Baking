package com.example.android.baking;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.RecipeData.Step;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();

            mSteps = getIntent().getParcelableArrayListExtra(StepsDetailFragment.STEPS);

            mVideoUrl = getIntent().getStringExtra(STEP_URL);
            mDetailedDescription = getIntent().getStringExtra(STEP_DESCRIBE);
            mStepId = getIntent().getIntExtra(STEP_ID, 0);
            mStepsSize = mSteps.size();

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
        }
    }

    public void onFragmentInteraction(int stepID, List<Step> stepList) {

        StepsDetailFragment stepFragment = new StepsDetailFragment();

        mVideoUrl = stepList.get(stepID).getVideoURL();
        mDetailedDescription = stepList.get(stepID).getDescription();
        mStepsSize = stepList.size();

        stepFragment.setStepId(stepID);
        stepFragment.setDetailedDescription(mDetailedDescription);
        stepFragment.setVideoUrl(mVideoUrl);
        stepFragment.setStepsSize(mStepsSize);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.steps_fragment_container, stepFragment)
                .commit();

    }
}

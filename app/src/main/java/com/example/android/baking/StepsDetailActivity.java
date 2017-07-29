package com.example.android.baking;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class StepsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stpes_list);

        if (savedInstanceState == null) {
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();

            Bundle arguments = new Bundle();
            arguments.putString(StepsDetailFragment.ARG_STEP_ID,
                    getIntent().getStringExtra(StepsDetailFragment.ARG_STEP_ID));

            stepsDetailFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_fragment_container, stepsDetailFragment)
                    .commit();
        }
    }


}

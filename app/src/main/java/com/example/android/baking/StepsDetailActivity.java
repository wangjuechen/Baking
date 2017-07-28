package com.example.android.baking;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class StepsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stpes_list);

        StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.steps_fragment_container, stepsDetailFragment)
                .commit();
    }
}

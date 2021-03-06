package com.example.android.baking;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.baking.RecipeData.RecipeItem;
import com.example.android.baking.RecipeData.Step;
import com.example.android.baking.RecyclerViewAdapters.RecipeStepsAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.baking.StepsDetailFragment.STEP_ID;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity implements StepsDetailFragment.OnFragmentInteractionListener,
        RecipeStepsAdapter.onStepsVideoFragment {

    private boolean mTwoPane = false;

    private int mStepId;
    private String mDetailedDescription;
    private String mVideoUrl;
    private int mStepsSize;
    private List<Step> mSteps;
    private RecipeItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        if (isTabletLandscape(this)) {
            mTwoPane = true;
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            Intent intent = getIntent();

            Bundle old = intent.getBundleExtra(ItemDetailFragment.ARG_BUNDLE);

            mItem = old.getParcelable(ItemDetailFragment.ARG_ITEM_ID);

            arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID,
                    mItem);

            ItemDetailFragment fragment = new ItemDetailFragment();

            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.receiptView_details_fragment_container, fragment)
                    .commit();
        }

        if (!mTwoPane) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
            setSupportActionBar(toolbar);

            // Show the Up button in the action bar.
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            // savedInstanceState is non-null when there is fragment state
            // saved from previous configurations of this activity
            // (e.g. when rotating the screen from portrait to landscape).
            // In this case, the fragment will automatically be re-added
            // to its container so we don't need to manually add it.
            // For more information, see the Fragments API guide at:
            //
            // http://developer.android.com/guide/components/fragments.html
            //
        } else {


            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();

            Intent intent = getIntent();

            Bundle old = intent.getBundleExtra(ItemDetailFragment.ARG_BUNDLE);

            mItem = old.getParcelable(ItemDetailFragment.ARG_ITEM_ID);

            Step step = mItem.getSteps().get(0);

            stepsDetailFragment.setItem(mItem);
            stepsDetailFragment.setStepList(mItem.getSteps());
            stepsDetailFragment.setStepId(0);
            stepsDetailFragment.setDetailedDescription(step.getDescription());
            stepsDetailFragment.setVideoUrl(step.getVideoURL());
            stepsDetailFragment.setStepsSize(mItem.getSteps().size());

            Bundle arguments = new Bundle();
            arguments.putString(STEP_ID,
                    getIntent().getStringExtra(STEP_ID));

            stepsDetailFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_fragment_container, stepsDetailFragment)
                    .commit();

        }
    }

    private boolean isTabletLandscape(Context context) {
        return ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE) && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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


    public void onStepsVideoTwoPane(Step step, List<Step> stepList, RecipeItem items) {

        if (!mTwoPane) {
            Bundle bundle = new Bundle();

            Intent intent = new Intent(this, StepsDetailActivity.class);

            bundle.putParcelable(ItemDetailFragment.ARG_ITEM_ID, items);

            bundle.putString(StepsDetailFragment.STEP_DESCRIBE, step.getDescription());

            bundle.putInt(StepsDetailFragment.STEP_ID, step.getId());

            bundle.putString(StepsDetailFragment.STEP_URL, step.getVideoURL());

            bundle.putString(StepsDetailFragment.STEP_THUMBNAILURL, step.getThumbnailURL());

            bundle.putInt(StepsDetailFragment.STEPS_SIZE, stepList.size());

            bundle.putParcelableArrayList(StepsDetailFragment.STEPS, (ArrayList<? extends Parcelable>) stepList);

            intent.putExtras(bundle);

            startActivity(intent);

        } else {

            StepsDetailFragment stepFragment = new StepsDetailFragment();

            mVideoUrl = stepList.get(step.getId()).getVideoURL();
            mDetailedDescription = stepList.get(step.getId()).getDescription();
            mStepsSize = stepList.size();

            stepFragment.setItem(items);
            stepFragment.setStepId(step.getId());
            stepFragment.setDetailedDescription(mDetailedDescription);
            stepFragment.setVideoUrl(mVideoUrl);
            stepFragment.setStepsSize(mStepsSize);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.steps_fragment_container, stepFragment)
                    .commit();
        }
    }
}

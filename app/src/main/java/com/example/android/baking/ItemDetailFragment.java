package com.example.android.baking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.RecipeData.Ingredient;
import com.example.android.baking.RecipeData.RecipeItem;
import com.example.android.baking.RecipeData.Step;
import com.example.android.baking.RecyclerViewAdapters.RecipeIngredientAdapter;
import com.example.android.baking.RecyclerViewAdapters.RecipeStepsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements RecipeStepsAdapter.onStepsVideoFragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private RecyclerView mStepsRecycleView;

    private RecyclerView mIngredientRecycleView;

    private RecipeStepsAdapter mStepsAdapter;

    private RecipeIngredientAdapter mIngredientAdapter;

    private final LinearLayoutManager mStepsLinearLayoutManager = new LinearLayoutManager(getActivity());

    private final LinearLayoutManager mIngredientLinearLayoutManager = new LinearLayoutManager(getActivity());


    /**
     * The dummy content this fragment is presenting.
     */

    private List<Ingredient> mIngredient;

    private List<Step> mSteps;

    @BindView(R.id.tv_title_ingredient)
    TextView mTvIngredientContent;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecipeItem item;

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            item = (RecipeItem) getActivity().getIntent().getSerializableExtra(ARG_ITEM_ID);

            mSteps = item.getSteps();

            mIngredient = item.getIngredients();

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(item.getName());
            }
        }

        View rootView = inflater.inflate(R.layout.fragment_recipe_ingreandsteps_list, container, false);

        ButterKnife.bind(this, rootView);

        mStepsRecycleView = (RecyclerView) rootView.findViewById(R.id.rv_item_steps);

        mIngredientRecycleView = (RecyclerView) rootView.findViewById(R.id.rv_item_ingredient);

        mStepsRecycleView.setHasFixedSize(true);

        mIngredientRecycleView.setHasFixedSize(true);

        mStepsLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mIngredientLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mStepsRecycleView.setLayoutManager(mStepsLinearLayoutManager);

        mIngredientRecycleView.setLayoutManager(mIngredientLinearLayoutManager);

        mStepsAdapter = new RecipeStepsAdapter(mSteps);

        mIngredientAdapter = new RecipeIngredientAdapter(mIngredient);

        mStepsAdapter.setmFragmentListener(this);

        mStepsRecycleView.setAdapter(mStepsAdapter);

        mIngredientRecycleView.setAdapter(mIngredientAdapter);

        return rootView;
    }

    @Override
    public void onStepsVideoTwoPane(Step step, List<Step> steps) {

            Bundle bundle = new Bundle();

            Intent intent = new Intent(getContext(), StepsDetailActivity.class);

            bundle.putString(StepsDetailFragment.STEP_DESCRIBE, step.getDescription());

            bundle.putInt(StepsDetailFragment.STEP_ID, step.getId());

            bundle.putString(StepsDetailFragment.STEP_URL, step.getVideoURL());

            bundle.putInt(StepsDetailFragment.STEPS_SIZE, steps.size());

            bundle.putParcelableArrayList(StepsDetailFragment.STEPS, (ArrayList<? extends Parcelable>) steps);

            intent.putExtras(bundle);

        if (getActivity().findViewById(R.id.tablet_linear_layout) == null) {

            startActivity(intent);

        } else {

            StepsDetailFragment stepFragment = new StepsDetailFragment();

            String mVideoUrl = steps.get(step.getId()).getVideoURL();
            String mDetailedDescription = steps.get(step.getId()).getDescription();
            int mStepsSize = steps.size();

            stepFragment.setStepList(steps);
            stepFragment.setStepId(step.getId());
            stepFragment.setDetailedDescription(mDetailedDescription);
            stepFragment.setVideoUrl(mVideoUrl);
            stepFragment.setStepsSize(mStepsSize);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.steps_fragment_container, stepFragment)
                    .commit();
        }

    }
}

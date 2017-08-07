package com.example.android.baking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import com.google.gson.Gson;

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

    public static final String ARG_WIDGET_INGREDIENT_EDITOR = "ingredient_pref_editor";

    public static final String ARG_WIDGET_INGREDIENT = "ingredient_pref";

    public static final String ARG_BUNDLE = "list_bundle";

    private RecyclerView mStepsRecycleView;

    private RecyclerView mIngredientRecycleView;

    private RecipeStepsAdapter mStepsAdapter;

    private RecipeIngredientAdapter mIngredientAdapter;

    private final LinearLayoutManager mStepsLinearLayoutManager = new LinearLayoutManager(getActivity());

    private final LinearLayoutManager mIngredientLinearLayoutManager = new LinearLayoutManager(getActivity());

    private static final String BUNDLE_RECYCLER_LAYOUT = "Recycler_layout";
    /**
     * The dummy content this fragment is presenting.
     */

    private List<Ingredient> mIngredientList;

    private List<Step> mStepList;

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

        setRetainInstance(true);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mStepsRecycleView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mStepsRecycleView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecipeItem item = null;

        if (getArguments().containsKey(ItemDetailFragment.ARG_ITEM_ID)) {

            Bundle bundle = getArguments();

            if(bundle!= null){
                item = bundle.getParcelable(ItemDetailFragment.ARG_ITEM_ID);
            }
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            mStepList = item.getSteps();

            mIngredientList = item.getIngredients();

            saveIngredientInSharePrefs(mIngredientList);

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

        mStepsAdapter = new RecipeStepsAdapter(mStepList,item, getActivity());

        mIngredientAdapter = new RecipeIngredientAdapter(mIngredientList,item);

        mStepsAdapter.setmFragmentListener(this);

        mStepsRecycleView.setAdapter(mStepsAdapter);

        mIngredientRecycleView.setAdapter(mIngredientAdapter);


        return rootView;
    }

    private void saveIngredientInSharePrefs(List<Ingredient> ingredientList){

        Gson gson = new Gson();

        String jsonText = gson.toJson(ingredientList);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ARG_WIDGET_INGREDIENT, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ARG_WIDGET_INGREDIENT_EDITOR, jsonText);

        editor.commit();
    }

    @Override
    public void onStepsVideoTwoPane(Step step, List<Step> steps , RecipeItem item) {

            Bundle bundle = new Bundle();

            Intent intent = new Intent(getContext(), StepsDetailActivity.class);

            bundle.putParcelable(ItemDetailFragment.ARG_ITEM_ID, item);

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

            stepFragment.setItem(item);
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

package com.example.android.baking;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.baking.ReceiptData.Ingredient;
import com.example.android.baking.ReceiptData.ReceiptItem;
import com.example.android.baking.ReceiptData.Step;

import java.util.List;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

    private RecyclerView mRecycleView;

    private ReceiptDetailsAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager;
    /**
     * The dummy content this fragment is presenting.
     */
    private List<ReceiptItem> mItem;

    private List<Ingredient> mIngredient;

    private List<Step> mSteps;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mIngredient = ReceiptItem.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.receipt_item_detail, container, false);

        mRecycleView = (RecyclerView) rootView.findViewById(R.id.rv_item_steps);

        mRecycleView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecycleView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new ReceiptDetailsAdapter(mSteps);

        mRecycleView.setAdapter(mAdapter);

        return rootView;
    }
}

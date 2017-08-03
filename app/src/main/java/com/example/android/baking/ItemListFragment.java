package com.example.android.baking;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.baking.NetworkUtils.ConnectivityReceiver;
import com.example.android.baking.NetworkUtils.InternetConnectivityStarter;
import com.example.android.baking.RecipeData.RecipeItem;
import com.example.android.baking.RecyclerViewAdapters.RecipeListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemListFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {

    private String JSON_RECEIPT_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private static final String BUNDLE_LIST_RECYCLER_LAYOUT = "Recycler_layout";

    private RequestQueue mRequestQueue;

    private Gson gson;

    private final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

    private RecyclerView mRecycleView;

    private RecipeListAdapter mAdapter;

    private List<RecipeItem> mRecipeItems;

    private boolean mTabletDisplay;

    @BindView(R.id.list_fragmentLayout)
    RelativeLayout mLayout;

    public ItemListFragment() {
        // Required empty public constructor
    }

    public static ItemListFragment newInstance(String param1, String param2) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        if (getResources().getDisplayMetrics().widthPixels > 1600) {
            mTabletDisplay = true;
        }

        mRecycleView = (RecyclerView) rootView.findViewById(R.id.rv_receipt_list);
        mRecycleView.setHasFixedSize(true);

        if (mTabletDisplay) {
            int numOfCol = 3;
            mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), numOfCol));

        } else {
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecycleView.setLayoutManager(mLayoutManager);
        }

        if (checkConnectivity()) {

            mRequestQueue = Volley.newRequestQueue(getActivity());
            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.setDateFormat("M/d/yy hh:mm a");
            gson = gsonbuilder.create();
            fetchPosts();
        }

        return rootView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_LIST_RECYCLER_LAYOUT);
            mRecycleView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_LIST_RECYCLER_LAYOUT, mRecycleView.getLayoutManager().onSaveInstanceState());
    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, JSON_RECEIPT_URL, onPostsLoaded, onPostsError);

        mRequestQueue.add(request);

    }

    @Override
    public void onResume() {
        super.onResume();

        InternetConnectivityStarter.getInstance().setConnectivityListener(this);
    }

    private boolean checkConnectivity() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
        return isConnected;
    }

    private void showSnack(boolean isConnected) {
        if (!isConnected) {
            String message = getString(R.string.SnackBar_noInternet);
            Snackbar snackbar = Snackbar
                    .make(mLayout, message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            snackbar.show();
        }
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            mRecipeItems = Arrays.asList(gson.fromJson(response, RecipeItem[].class));

            mAdapter = new RecipeListAdapter(mRecipeItems);

            mRecycleView.setAdapter(mAdapter);
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            showSnack(false);
        }
    };

    @Override
    public void onNetworkConnectingChanged(boolean connected) {
        showSnack(connected);
    }
}

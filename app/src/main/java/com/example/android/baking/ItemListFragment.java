package com.example.android.baking;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.baking.RecipeData.Ingredient;
import com.example.android.baking.RecipeData.RecipeItem;
import com.example.android.baking.RecipeData.Step;
import com.example.android.baking.RecyclerViewAdapters.RecipeListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemListFragment extends Fragment {

    private String JSON_RECEIPT_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private RequestQueue mRequestQueue;

    private Gson gson;

    private final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

    private RecyclerView mRecycleView;

    private RecipeListAdapter mAdapter;

    private List<RecipeItem> mRecipeItems;

    private List<Ingredient> mIngredient;

    private List<Step> mStep;

    private boolean mTabletDisplay;

    @BindView(R.id.tv_title_receipt)
    TextView tv_receiptTitle;

    private OnFragmentInteractionListener mListener;

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

        if (rootView.findViewById(R.id.receiptGridView_fragment_container) != null) {
            mTabletDisplay = true;
        }


        mRecycleView = (RecyclerView) rootView.findViewById(R.id.rv_receipt_list);
        mRecycleView.setHasFixedSize(true);

        if(mTabletDisplay){
            int numOfCol = 3;
            mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), numOfCol));

        }else{
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecycleView.setLayoutManager(mLayoutManager);
        }

        mRequestQueue = Volley.newRequestQueue(getActivity());
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonbuilder.create();
        fetchPosts();


        return rootView;
    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, JSON_RECEIPT_URL, onPostsLoaded, onPostsError);

        mRequestQueue.add(request);

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
            Log.e("ItemListFragment", error.toString());
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}

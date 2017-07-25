package com.example.android.baking;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.baking.ReceiptData.ReceiptItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MasterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MasterListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String JSON_RECEIPT_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private RequestQueue mRequestQueue;

    private Gson gson;

    private final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

    private RecyclerView mRecycleView;

    private ProgressBar mProgressBar;

    private Toast mToast;

    private ReceiptListAdapter mAdapter;

    private List<ReceiptItem> mReceiptItems;

    @BindView(R.id.tv_title_receipt)
    TextView tv_receiptTitle;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MasterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MasterListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MasterListFragment newInstance(String param1, String param2) {
        MasterListFragment fragment = new MasterListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.receipt_list, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);

        mRecycleView = (RecyclerView) rootView.findViewById(R.id.rv_receipt_list);

        mRecycleView.setHasFixedSize(true);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecycleView.setLayoutManager(mLayoutManager);

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

            mReceiptItems = Arrays.asList(gson.fromJson(response, ReceiptItem[].class));

            mAdapter = new ReceiptListAdapter(mReceiptItems);

            mRecycleView.setAdapter(mAdapter);

            Log.i("MasterListFragment", mReceiptItems.size() + " Loaded");
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MasterListFragment", error.toString());
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}

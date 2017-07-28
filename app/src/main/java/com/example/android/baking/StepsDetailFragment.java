package com.example.android.baking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepsDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsDetailFragment extends Fragment {

    private SimpleExoPlayer mExoplayer;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.video_player_view)
    SimpleExoPlayer mPlayerView;

    @BindView(R.id.tv_step_description)
    TextView mStepDescription;

    @BindView(R.id.btn_previous_step)
    Button mBtnPreciousStep;

    @BindView(R.id.btn_next_step)
    Button mBtnNextStep;

    public StepsDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepsDetailFragment.
     */
    public static StepsDetailFragment newInstance(String param1, String param2) {
        StepsDetailFragment fragment = new StepsDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_list, container,false);

        ButterKnife.bind(this,view);

        return view;
    }

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

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
import android.widget.Toast;

import com.example.android.baking.RecipeData.Step;
import com.example.android.baking.RecyclerViewAdapters.RecipeStepsAdapter;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

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

    public static final String STEP_ID = "step_id";

    public static final String STEP_URL = "step_Url";

    public static final String STEP_DESCRIBE = "step_describe";

    public static final String STEPS_SIZE = "steps_size";

    public static final String STEPS = "steps";

    private SimpleExoPlayer mExoPlayer;

    private RecipeStepsAdapter mStepAdapter;

    OnFragmentInteractionListener mListener;

    private boolean mPlayWhenReady;

    private int currentWindow;
    private long playbackPosition;
    private String mVideoUrl;
    private String mDetailedDescription;
    private int mStepId;
    private int mStepsSize;
    private List<Step> mStepList;
    private Step mSteps;

    @BindView(R.id.video_player_view)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.tv_step_description)
    TextView mtvStepDescription;

    @BindView(R.id.btn_previous_step)
    Button mBtnPreviousStep;

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

        View view = inflater.inflate(R.layout.fragment_steps_list, container, false);

        ButterKnife.bind(this, view);

        mtvStepDescription.setText(mDetailedDescription);

        mBtnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newStepID = mStepId + 1;
                if (newStepID < mStepsSize) {
                    mListener.onFragmentInteraction(newStepID, mStepList);
                } else {
                    Toast.makeText(getContext(), getString(R.string.toast_nextBtn), Toast.LENGTH_SHORT);
                }
            }
        });

        mBtnPreviousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newStepID = mStepId - 1;
                if (newStepID >= 0) {
                    mListener.onFragmentInteraction(newStepID, mStepList);
                } else {
                    Toast.makeText(getContext(), getString(R.string.toast_previousBtn), Toast.LENGTH_SHORT);
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }

    }

    private void initializePlayer() {
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setPlayer(mExoPlayer);

        mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        mExoPlayer.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(mVideoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mediaSource, true, false);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_ID, mStepId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
        void onFragmentInteraction(int stepID, List<Step> steps);
    }

    public void setStepList(List<Step> step){
        mStepList = step;
    }

    public void setVideoUrl(String url) {
        mVideoUrl = url;
    }

    public void setDetailedDescription(String description) {
        mDetailedDescription = description;
    }

    public void setStepId(int id) {
        mStepId = id;
    }

    public void setStepsSize(int size) {
        mStepsSize = size;
    }
}

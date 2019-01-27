package com.rz.movieapp.ui.fragments.upcoming;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rz.movieapp.R;
import com.rz.movieapp.data.model.MovieObject;
import com.rz.movieapp.ui.fragments.nowplaying.NowPlayingContract;
import com.rz.movieapp.utils.MovieListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class UpcomingFragment extends DaggerFragment implements UpcomingContract.View{

    @BindView(R.id.pb_upcoming) ProgressBar mLoading;
    @BindView(R.id.rv_upcoming) RecyclerView mRv;

    @Inject
    UpcomingContract.Presenter mPresenter;

    ArrayList<MovieObject> mList = null;
    MovieListAdapter mRvAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
        if (savedInstanceState != null){
            mList = savedInstanceState.getParcelableArrayList("mList");
            if(mList == null){
                requestData();
            } else {
                setView(mList);
            }

            Log.d("upcoming", "notnull");
        } else {
            requestData();
            Log.d("upcoming", "null");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("upcoming", "state saved");
        outState.putParcelableArrayList("mList", mList);
    }

    private void requestData() {
        mPresenter.getUpcomingMovies();
    }

    private void initAdapter() {
        mRvAdapter = new MovieListAdapter(getActivity());
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setAdapter(mRvAdapter);
    }

    @Override
    public void showLoading(Boolean condition) {
        mLoading.setVisibility(condition? View.VISIBLE: View.INVISIBLE);
    }

    @Override
    public void setView(ArrayList<MovieObject> results) {
        mList = results;
        if(mList != null){
            mRvAdapter.setData(mList);
        }
    }
}

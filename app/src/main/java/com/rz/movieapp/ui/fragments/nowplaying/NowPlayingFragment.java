package com.rz.movieapp.ui.fragments.nowplaying;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rz.movieapp.R;
import com.rz.movieapp.data.model.MovieObject;
import com.rz.movieapp.utils.MovieListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class NowPlayingFragment extends DaggerFragment implements NowPlayingContract.View{

    @BindView(R.id.pb_now_playing) ProgressBar mLoading;
    @BindView(R.id.rv_now_playing) RecyclerView mRv;

    @Inject NowPlayingContract.Presenter mPresenter;

    MovieListAdapter mRvAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
        requestData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRvAdapter = null;
        mPresenter.onDestroyComposite();
    }

    private void requestData() {
        mPresenter.getNowPlayingMovies();
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
        mRvAdapter.setData(results);
    }
}

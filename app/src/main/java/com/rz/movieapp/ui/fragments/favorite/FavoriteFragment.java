package com.rz.movieapp.ui.fragments.favorite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.rz.movieapp.R;
import com.rz.movieapp.utils.FavoriteListAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class FavoriteFragment extends DaggerFragment implements FavoriteContract.View {
    @BindView(R.id.no_result_favorite) RelativeLayout mNotFound;
    @BindView(R.id.pb_favorite) ProgressBar mLoading;
    @BindView(R.id.rv_favorite) RecyclerView mRv;

    @Inject FavoriteContract.Presenter mPresenter;

    FavoriteListAdapter mRvAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setContext(getActivity());
        initAdapter();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRvAdapter = null;
    }

    private void getData() {
        mPresenter.getFavoriteMovies();
    }

    private void initAdapter() {
        mRvAdapter = new FavoriteListAdapter(getActivity());
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setAdapter(mRvAdapter);
    }

    @Override
    public void showLoading(Boolean condition) {
        mLoading.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showNotFound(Boolean condition) {
        mNotFound.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setView(Cursor results) {
        mRvAdapter.setData(results);
    }
}

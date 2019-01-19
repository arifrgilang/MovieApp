package com.rz.movieapp.ui.fragments.search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.rz.movieapp.R;
import com.rz.movieapp.data.model.MovieObject;
import com.rz.movieapp.ui.fragments.upcoming.UpcomingContract;
import com.rz.movieapp.utils.MovieListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;

public class SearchFragment extends DaggerFragment implements SearchContract.View{

    @BindView(R.id.no_result_search) RelativeLayout mNotFoundLayout;
    @BindView(R.id.pb_search) ProgressBar mLoading;
    @BindView(R.id.rv_search) RecyclerView mRv;
    @BindView(R.id.et_search) EditText mSearchEditText;
    @BindView(R.id.bt_search) ImageButton mSearchButton;

    @Inject
    SearchContract.Presenter mPresenter;

    MovieListAdapter mRvAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
        requestData("a");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRvAdapter = null;
        mPresenter.onDestroyComposite();
    }

    private void requestData(String query) {
        mPresenter.getListMovie(query);
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
    public void showNotFound(Boolean condition) {
        mNotFoundLayout.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setView(ArrayList<MovieObject> results) {
        mRvAdapter.setData(results);
        showNotFound(results.size() == 0);
    }

    @OnClick(R.id.bt_search)
    public void onClick(){
        if(!TextUtils.isEmpty(mSearchEditText.getText().toString())){
            showNotFound(false);

            String query = mSearchEditText.getText().toString();
            requestData(query);
        } else {
            mSearchEditText.setError(getString(R.string.fill_query));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchBtn = menu.findItem(R.id.search_btn);
        searchBtn.setVisible(true);
    }
}


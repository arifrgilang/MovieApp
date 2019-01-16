package com.rz.movieapp.view.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.rz.movieapp.R;
import com.rz.movieapp.api.MovieDBClient;
import com.rz.movieapp.di.App;
import com.rz.movieapp.model.MovieObject;
import com.rz.movieapp.utils.MovieListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.no_result_search) RelativeLayout mNotFoundLayout;
    @BindView(R.id.pb_search) ProgressBar mLoadingView;
    @BindView(R.id.rv_search) RecyclerView mMovieRv;
    @BindView(R.id.et_search) EditText mSearchEditText;
    @BindView(R.id.bt_search) ImageButton mSearchButton;

    @Inject MovieDBClient movieDbclient;
    MainContract.Presenter mPresenter;

    MovieListAdapter mRvAdapter;
    ArrayList<MovieObject> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initPresenter();
        initAdapter();

        requestData("a");
    }

    private void initAdapter() {
        mRvAdapter = new MovieListAdapter(mList, this);
        mMovieRv.setLayoutManager(new LinearLayoutManager(this));
        mMovieRv.setAdapter(mRvAdapter);
    }

    @Override
    public void initPresenter() {
        //dagger inject
        App.app().appComponent().inject(this);
        mPresenter = new MainPresenter(this, movieDbclient);
    }

    private void requestData(String query) {
        mPresenter.getListMovie(query);
    }

    @Override
    public void showLoading(Boolean condition) {
        mLoadingView.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showNotFound(Boolean condition) {
        mNotFoundLayout.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setView(ArrayList<MovieObject> results) {
        mList.clear();
        mList.addAll(results);
        mRvAdapter.notifyDataSetChanged();

        showNotFound(results.size() == 0);
    }

    @OnClick(R.id.bt_search)
    public void onClick(){
        if(!TextUtils.isEmpty(mSearchEditText.getText().toString())){
            showNotFound(false);

            String query = mSearchEditText.getText().toString();
            requestData(query);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroyComposite();
    }
}

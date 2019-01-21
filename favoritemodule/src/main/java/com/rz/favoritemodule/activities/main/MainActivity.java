package com.rz.favoritemodule.activities.main;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.rz.favoritemodule.R;
import com.rz.favoritemodule.db.FavoriteListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    @BindView(R.id.no_result_favorite) RelativeLayout mNotFound;
    @BindView(R.id.pb_favorite) ProgressBar mLoading;
    @BindView(R.id.rv_favorite) RecyclerView mRv;

    MainContract.Presenter mPresenter;
    FavoriteListAdapter mRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenter(this, this);
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
        mRvAdapter = new FavoriteListAdapter(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
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


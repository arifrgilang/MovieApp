package com.rz.movieapp.view.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.rz.movieapp.R;
import com.rz.movieapp.api.MovieDBClient;
import com.rz.movieapp.api.ServiceGenerator;
import com.rz.movieapp.model.MovieObject;
import com.rz.movieapp.utils.MovieListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView{

    RelativeLayout mNotFoundLayout;
    ProgressBar mLoadingView;
    RecyclerView mMovieRv;
    EditText mSearchEditText;
    ImageButton mSearchButton;

    MainPresenter mPresenter;
    MovieListAdapter mRvAdapter;
    ArrayList<MovieObject> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPresenter();
        initAdapter();
        mPresenter.getListMovie("a");

    }

    private void initAdapter() {
        mRvAdapter = new MovieListAdapter(mList, this);
        mMovieRv.setLayoutManager(new LinearLayoutManager(this));
        mMovieRv.setAdapter(mRvAdapter);
    }

    private void initPresenter() {
        MovieDBClient client = ServiceGenerator.createService(MovieDBClient.class);
        mPresenter = new MainPresenter(this, client);
    }

    private void initView() {
        mNotFoundLayout = findViewById(R.id.no_result_search);
        mLoadingView = findViewById(R.id.pb_search);
        mMovieRv = findViewById(R.id.rv_search);
        mSearchEditText = findViewById(R.id.et_search);
        mSearchButton = findViewById(R.id.bt_search);
        mSearchButton.setOnClickListener(this);
        mLoadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search :
                if(!TextUtils.isEmpty(mSearchEditText.getText().toString())){
                    Log.d("button", "button clicked");
                    mLoadingView.setVisibility(View.INVISIBLE);
                    String query = mSearchEditText.getText().toString();
                    mPresenter.getListMovie(query);
                }
                break;
        }
    }

    @Override
    public void showLoading(Boolean condition) {
        mLoadingView.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setView(ArrayList<MovieObject> results) {
        mList.clear();
        mList.addAll(results);
        mRvAdapter.notifyDataSetChanged();
        mNotFoundLayout.setVisibility(results.size() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroyComposite();
    }
}

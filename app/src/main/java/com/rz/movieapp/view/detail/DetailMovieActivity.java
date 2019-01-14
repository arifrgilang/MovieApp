package com.rz.movieapp.view.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rz.movieapp.R;
import com.rz.movieapp.api.MovieDBClient;
import com.rz.movieapp.api.ServiceGenerator;
import com.rz.movieapp.model.MovieObject;

public class DetailMovieActivity extends AppCompatActivity implements DetailMovieView {

    final public static String MOVIE_ID = "ID";
    String id;

    RelativeLayout mLoadingView;
    CircularImageView mImg;
    TextView mTitle;
    TextView mReleaseDate;
    TextView mRating;
    TextView mLanguage;
    TextView mOverview;

    DetailMoviePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        id = getIntent().getStringExtra(MOVIE_ID);

        initView();
        initPresenter();
        mPresenter.getMovieDetail(id);
    }

    private void initPresenter() {
        MovieDBClient client = ServiceGenerator.createService(MovieDBClient.class);
        mPresenter = new DetailMoviePresenter(this, client);
    }

    private void initView() {
        mLoadingView = findViewById(R.id.detail_loading);
        mImg = findViewById(R.id.detail_img);
        mTitle = findViewById(R.id.detail_title);
        mReleaseDate = findViewById(R.id.detail_release_date);
        mRating = findViewById(R.id.detail_rating);
        mLanguage = findViewById(R.id.detail_language);
        mOverview = findViewById(R.id.detail_overview);
    }

    @Override
    public void showLoading(Boolean condition) {
        mLoadingView.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setView(MovieObject results) {
        mTitle.setText(results.getOriginal_title());
        String releaseDate = "Release Date: " + results.getRelease_date();
        mReleaseDate.setText(releaseDate);
        mRating.setText(results.getVote_average());
        mLanguage.setText(results.getOriginal_language().toUpperCase());
        mOverview.setText(results.getOverview());

        String url = "https://image.tmdb.org/t/p/w342" + results.getPoster_path();
        Glide.with(this)
                .load(url)
                .into(mImg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroyComposite();
    }
}

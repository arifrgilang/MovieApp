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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity implements DetailMovieView {

    final public static String MOVIE_ID = "ID";

    @BindView(R.id.detail_loading) RelativeLayout mLoadingView;
    @BindView(R.id.detail_img) CircularImageView mImg;
    @BindView(R.id.detail_title) TextView mTitle;
    @BindView(R.id.detail_release_date) TextView mReleaseDate;
    @BindView(R.id.detail_rating) TextView mRating;
    @BindView(R.id.detail_language) TextView mLanguage;
    @BindView(R.id.detail_overview) TextView mOverview;

    DetailMoviePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        String id = getIntent().getStringExtra(MOVIE_ID);

        ButterKnife.bind(this);
        initPresenter();

        mPresenter.getMovieDetail(id);
    }

    private void initPresenter() {
        MovieDBClient client = ServiceGenerator.createService(MovieDBClient.class);
        mPresenter = new DetailMoviePresenter(this, client);
    }

    @Override
    public void showLoading(Boolean condition) {
        mLoadingView.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setView(MovieObject results) {
        String releaseDate = "Release Date: " + results.getRelease_date();
        String url = "https://image.tmdb.org/t/p/w342" + results.getPoster_path();

        mTitle.setText(results.getOriginal_title());
        mReleaseDate.setText(releaseDate);
        mRating.setText(results.getVote_average());
        mLanguage.setText(results.getOriginal_language().toUpperCase());
        mOverview.setText(results.getOverview());

        Glide.with(this).load(url).into(mImg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroyComposite();
    }
}

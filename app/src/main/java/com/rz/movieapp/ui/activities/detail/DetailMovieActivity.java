package com.rz.movieapp.ui.activities.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rz.movieapp.R;
import com.rz.movieapp.data.model.MovieObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.rz.movieapp.db.DbContract.FavColumns.CONTENT_URI;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_ID;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_LANGUAGE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_OVERVIEW;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_POSTER;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_RATING;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_R_DATE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_TITLE;

public class DetailMovieActivity extends DaggerAppCompatActivity implements DetailMovieContract.View {

    final private static String TAG = "Detail activity";

    @BindView(R.id.detail_loading) RelativeLayout mLoadingView;
    @BindView(R.id.detail_img) CircularImageView mImg;
    @BindView(R.id.detail_title) TextView mTitle;
    @BindView(R.id.detail_release_date) TextView mReleaseDate;
    @BindView(R.id.detail_rating) TextView mRating;
    @BindView(R.id.detail_language) TextView mLanguage;
    @BindView(R.id.detail_overview) TextView mOverview;

    Menu menuItem = null;
    boolean isFavorite;
    MovieObject movieObject;
    String id;

    @Inject DetailMovieContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        id = getIntent().getStringExtra(MOVIE_ID);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mPresenter.setContext(this);
        mPresenter.getMovieDetail(id);
        mPresenter.checkFavorite(id);
    }

    @Override
    public void showLoading(Boolean condition) {
        mLoadingView.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setView(MovieObject results) {
        movieObject = results;

        mTitle.setText(movieObject.getOriginal_title());
        mReleaseDate.setText(movieObject.getRelease_date());
        mRating.setText(movieObject.getVote_average());
        mLanguage.setText(movieObject.getOriginal_language());
        mOverview.setText(movieObject.getOverview());

        Glide.with(this).load(movieObject.getPoster_path()).into(mImg);
    }

    @Override
    public void setFavorite(Boolean condition) {
        isFavorite = condition;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroyComposite();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        menuItem = menu;
        setFavoriteState();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setFavoriteState() {
        if(isFavorite){
            menuItem.getItem(0).setIcon(ContextCompat
                    .getDrawable(this, R.drawable.ic_favorite_pink_24dp));
        } else {
            menuItem.getItem(0).setIcon(ContextCompat
                    .getDrawable(this, R.drawable.ic_favorite_border_pink_24dp));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.fav_button:
                if(isFavorite){
                    mPresenter.removeFromFavorite(movieObject);
                } else {
                    mPresenter.addToFavorite(movieObject);
                    setResult(101);
                }
                isFavorite = !isFavorite;
                setFavoriteState();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

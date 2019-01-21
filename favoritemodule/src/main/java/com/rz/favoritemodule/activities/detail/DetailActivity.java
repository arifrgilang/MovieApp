package com.rz.favoritemodule.activities.detail;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rz.favoritemodule.R;
import com.rz.favoritemodule.model.MovieObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rz.favoritemodule.db.DbContract.FavColumns.MOVIE_ID;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    @BindView(R.id.detail_img) CircularImageView mImg;
    @BindView(R.id.detail_title) TextView mTitle;
    @BindView(R.id.detail_release_date) TextView mReleaseDate;
    @BindView(R.id.detail_rating) TextView mRating;
    @BindView(R.id.detail_language) TextView mLanguage;
    @BindView(R.id.detail_overview) TextView mOverview;

    MovieObject movieObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle data = getIntent().getExtras();
        movieObject = data.getParcelable(MOVIE_ID);

        ButterKnife.bind(this);
        setView(movieObject);
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
}

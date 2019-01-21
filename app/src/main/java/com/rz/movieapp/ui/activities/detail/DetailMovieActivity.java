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
import com.rz.movieapp.db.DbContract;
import com.rz.movieapp.ui.fragments.favorite.FavoriteContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.rz.movieapp.db.DbContract.FavColumns.CONTENT_URI;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_LANGUAGE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_OVERVIEW;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_POSTER;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_RATING;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_R_DATE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_TITLE;

public class DetailMovieActivity extends DaggerAppCompatActivity implements DetailMovieContract.View {

    final public static String MOVIE_ID = "ID";
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
    long idCursor;
    String id;
    String title;
    String rating;
    String poster;
    String language;
    String releaseDate;
    String overview;

    @Inject DetailMovieContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        id = getIntent().getStringExtra(MOVIE_ID);

        ButterKnife.bind(this);
        mPresenter.getMovieDetail(id);
        checkFavorite();
    }

    private void checkFavorite() {
        Uri uri = Uri.parse(CONTENT_URI+"");
        boolean fav = false;
        String selection = MOVIE_ID +" =?";
        String[] selectionArgs = new String[]{id};
        Cursor cursor = getContentResolver().query(uri,
                null,
                selection,
                selectionArgs,
                null);

        String getMovieId = null;
//        Log.d(TAG, "chekc fav, cursor " + cursor.getString(cursor.getColumnIndex(MOVIE_ID)));
//        if(cursor!= null && cursor.getCount()>0){
//            if (cursor.moveToFirst()){
//                getMovieId = cursor.getString(cursor.getColumnIndex(MOVIE_ID));
//                if(id.equals(getMovieId)){
//                    isFavorite = true;
//                    setFavorite();
//                }
//            }
//            cursor.close();
//        }
        //newnwenwnenwe
        if(cursor.getCount() > 0 ){
            isFavorite = true;
        }
//        if(cursor.moveToFirst()){
//            do{
//                idCursor = cursor.getLong(0);
//                Log.d(TAG, "check loop fav cursor id " + idCursor);
//                getMovieId = cursor.getString(1);
//                Log.d(TAG, "check loop fav movie id " + getMovieId);
//                if(getMovieId.equals(id)){
//                    isFavorite = true;
//                }
//                Log.d(TAG, "check isfav after loop " + isFavorite);
////                setFavorite();
//            } while (cursor.moveToNext());
//        }
        Log.d(TAG, "chekc fav intent id " + id);
        Log.d(TAG, "chekc fav movie id " + getMovieId);
    }

    @Override
    public void showLoading(Boolean condition) {
        mLoadingView.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setView(MovieObject results) {
        String release = getString(R.string.release_date) + " : " + results.getRelease_date();
        String url = "https://image.tmdb.org/t/p/w342" + results.getPoster_path();

        title = results.getOriginal_title();
        rating = results.getVote_average();
        poster = url;
        language = results.getOriginal_language().toUpperCase();
        releaseDate = release;
        overview = results.getOverview();

        mTitle.setText(title);
        mReleaseDate.setText(releaseDate);
        mRating.setText(rating);
        mLanguage.setText(language);
        mOverview.setText(overview);

        Glide.with(this).load(url).into(mImg);
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
        setFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    private void setFavorite() {
//        if(menuItem!= null){
            if(isFavorite){
                menuItem.getItem(0).setIcon(ContextCompat
                        .getDrawable(this, R.drawable.ic_favorite_pink_24dp));
            } else {
                menuItem.getItem(0).setIcon(ContextCompat
                        .getDrawable(this, R.drawable.ic_favorite_border_pink_24dp));
            }
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.fav_button:
                if(isFavorite){
                    Log.d(TAG, "removed from fav");
                    removeFromFavorite();
                } else {
                    Log.d(TAG, "added to fav");
                    addToFavorite();
                }
                isFavorite = !isFavorite;
                setFavorite();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToFavorite() {
        ContentValues values = new ContentValues();
        values.put(DbContract.FavColumns.MOVIE_ID, id);
        values.put(MOVIE_TITLE, title);
        values.put(MOVIE_RATING, rating);
        values.put(MOVIE_POSTER, poster);
        values.put(MOVIE_LANGUAGE, language);
        values.put(MOVIE_R_DATE, releaseDate);
        values.put(MOVIE_OVERVIEW, overview);

        getContentResolver().insert(CONTENT_URI, values);
        setResult(101);
    }

    private void removeFromFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        Log.d(TAG, uri.toString());
        getContentResolver().delete(uri, MOVIE_ID, new String[]{id});
    }
}

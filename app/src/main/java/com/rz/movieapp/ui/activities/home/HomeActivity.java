package com.rz.movieapp.ui.activities.home;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rz.movieapp.R;
import com.rz.movieapp.ui.fragments.favorite.FavoriteFragment;
import com.rz.movieapp.ui.fragments.nowplaying.NowPlayingFragment;
import com.rz.movieapp.ui.fragments.search.SearchFragment;
import com.rz.movieapp.ui.fragments.upcoming.UpcomingFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class HomeActivity extends DaggerAppCompatActivity implements HomeContract.View {

    @Inject HomeContract.Presenter presenter;
    @BindView(R.id.bottom_nav) BottomNavigationView bottomNavigationView;
    String currentFragment = null;
    String currentTitle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initBottomNav();

        if (savedInstanceState != null){
            currentTitle = savedInstanceState.getString("currentTitle");
            currentFragment = savedInstanceState.getString("currentFragment");
        } else {
            if(currentFragment == null && currentTitle == null){
                currentTitle = getString(R.string.now_playing);
                currentFragment = HomePresenter.F_NOW_PLAYING;
            }
        }

        changeFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentTitle", currentTitle);
        outState.putString("currentFragment", currentFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting_button){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.home_frame_layout, fragment)
                .commit();
    }

    @Override
    public void initBottomNav() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_nowplaying:
                        if(!(getSupportFragmentManager().findFragmentById(R.id.home_frame_layout) instanceof NowPlayingFragment)){
                            currentTitle = getString(R.string.now_playing);
                            currentFragment = HomePresenter.F_NOW_PLAYING;
                            changeFragment();
                        }
                        return true;
                    case R.id.menu_upcoming:
                        if(!(getSupportFragmentManager().findFragmentById(R.id.home_frame_layout) instanceof UpcomingFragment)) {
                            currentFragment = HomePresenter.F_UPCOMING;
                            currentTitle = getString(R.string.upcoming);
                            changeFragment();
                        }
                        return true;
                    case R.id.menu_search:
                        if(!(getSupportFragmentManager().findFragmentById(R.id.home_frame_layout) instanceof SearchFragment)) {
                            currentFragment = HomePresenter.F_SEARCH;
                            currentTitle = getString(R.string.search);
                            changeFragment();
                        }
                        return true;
                    case R.id.menu_favorite:
                        if(!(getSupportFragmentManager().findFragmentById(R.id.home_frame_layout) instanceof FavoriteFragment)) {
                            currentFragment = HomePresenter.F_FAVORITE;
                            currentTitle = getString(R.string.favorite_movies);
                            changeFragment();
                        }
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_nowplaying);
    }

    @Override
    public void setActionBarTitle(String title) {
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    private void changeFragment(){
        setActionBarTitle(currentTitle);
        presenter.changeFragment(currentFragment);
    }
}

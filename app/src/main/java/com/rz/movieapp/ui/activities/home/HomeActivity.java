package com.rz.movieapp.ui.activities.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.rz.movieapp.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class HomeActivity extends DaggerAppCompatActivity implements HomeContract.View {

    @Inject HomeContract.Presenter presenter;
    @BindView(R.id.bottom_nav) BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setActionBarTitle(getString(R.string.now_playing));
        initBottomNav();
        presenter.changeFragment(HomePresenter.F_NOW_PLAYING);
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
                        setActionBarTitle(getString(R.string.now_playing));
                        presenter.changeFragment(HomePresenter.F_NOW_PLAYING);
                        return true;
                    case R.id.menu_upcoming:
                        setActionBarTitle(getString(R.string.upcoming));
                        presenter.changeFragment(HomePresenter.F_UPCOMING);
                        return true;
                    case R.id.menu_search:
                        setActionBarTitle(getString(R.string.search));
                        presenter.changeFragment(HomePresenter.F_SEARCH);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void setActionBarTitle(String title) {
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }
}

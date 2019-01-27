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
import com.rz.movieapp.ui.activities.setting.SettingActivity;
import com.rz.movieapp.ui.fragments.favorite.FavoriteFragment;
import com.rz.movieapp.ui.fragments.nowplaying.NowPlayingFragment;
import com.rz.movieapp.ui.fragments.search.SearchFragment;
import com.rz.movieapp.ui.fragments.upcoming.UpcomingFragment;
import com.rz.movieapp.utils.viewpager.CustomViewPager;
import com.rz.movieapp.utils.viewpager.CustomViewPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class HomeActivity extends DaggerAppCompatActivity implements HomeContract.View {

    @Inject HomeContract.Presenter presenter;
    @BindView(R.id.bottom_nav) BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewpager1) CustomViewPager viewPager;
    CustomViewPagerAdapter adapter;
    Fragment current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initBottomNav();

        adapter = new CustomViewPagerAdapter(HomeActivity.this.getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), getString(R.string.now_playing));
        adapter.addFragment(new UpcomingFragment(), getString(R.string.upcoming));
        adapter.addFragment(new FavoriteFragment(), getString(R.string.favorite_movies));
        adapter.addFragment(new SearchFragment(), getString(R.string.search));

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting_button:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;

            case R.id.setting_reminder_button:
                Intent mIntent2 = new Intent(this, SettingActivity.class);
                startActivity(mIntent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFragment(Fragment fragment) {
        current = fragment;
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .replace(R.id.home_frame_layout, current)
//                .commit();
    }

    @Override
    public void initBottomNav() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_nowplaying:
                        viewPager.setCurrentItem(0);
//                        setActionBarTitle(adapter.getPageTitle(0).toString());
                        return true;
                    case R.id.menu_upcoming:
                        viewPager.setCurrentItem(1);
//                        setActionBarTitle(adapter.getPageTitle(1).toString());
                        return true;
                    case R.id.menu_search:
                        viewPager.setCurrentItem(3);
//                        setActionBarTitle(adapter.getPageTitle(3).toString());
                        return true;
                    case R.id.menu_favorite:
                        viewPager.setCurrentItem(2);
//                        setActionBarTitle(adapter.getPageTitle(2).toString());
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
}

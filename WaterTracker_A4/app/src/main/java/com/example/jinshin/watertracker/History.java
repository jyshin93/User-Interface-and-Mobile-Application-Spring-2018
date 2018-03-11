package com.example.jinshin.watertracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by jinshin on 04/03/2018.
 */

public class History extends AppCompatActivity implements DailyTotal.OnFragmentInteractionListener, DailyDetail.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    TabLayout tablayout;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    android.support.v7.widget.Toolbar action_bar;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_main);

        action_bar = (android.support.v7.widget.Toolbar) findViewById(R.id.history_bar);
        setSupportActionBar(action_bar);

        //initialize drawer here
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, action_bar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tablayout = (TabLayout) findViewById(R.id.tab_layout);
        tablayout.addTab(tablayout.newTab().setText("Daily Detail"));
        tablayout.addTab(tablayout.newTab().setText("Daily Total"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        int id = item.getItemId();

        if (id == R.id.settings) {
            intent = new Intent(History.this, Setting.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.plus) {
            intent = new Intent(History.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            intent = new Intent(History.this, MainActivity.class);
            startActivity(intent);
            mDrawerLayout.closeDrawers();
        } else if (id == R.id.history) {
            mDrawerLayout.closeDrawers();
        } else if (id == R.id.notification) {
            intent = new Intent(History.this, Notification.class);
            startActivity(intent);
            mDrawerLayout.closeDrawers();
        }
        return false;
    }
}

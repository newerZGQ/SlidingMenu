package com.example.zgq.lovebuy.ui.activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.zgq.lovebuy.R;
import com.example.zgq.lovebuy.adapter.DrawerListviewAdapter;
import com.example.zgq.lovebuy.ui.fragment.Fragment1;
import com.example.zgq.lovebuy.ui.fragment.Fragment2;
import com.example.zgq.lovebuy.ui.fragment.Fragment3;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{

    private static final String TAG = "HOMEACTIVITY";

    private Toolbar toolbar;
    private TextView statusbar;
    private FragmentManager fm;
    private ArrayList<String> fragmentTitleList;

    private ListView drawerLVi;
    private DrawerListviewAdapter drawerAdapter;

    private DrawerLayout drawerLayout;
    private LinearLayout drawerLv;
    private ActionBarDrawerToggle mDrawerToggle;

    //indicators of three fragments
    public int fragmentIndicator;
    public static final int PAGE_HOME = 0;
    public static final int PAGE_CHART = 1;
    public static final int PAGE_DESIRE = 2;

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            initFragmentIndicator();
        }else{
            fragmentIndicator = savedInstanceState.getInt("fragmentIndicator");
        }
        setContentView(R.layout.activity_home);
        initView();
        setViewContent();
        getMyFragmentManager();
        initFragments();
    }
    private void getMyFragmentManager(){
        fm = getSupportFragmentManager();
    }
    private void initFragmentIndicator(){
        fragmentIndicator = 0;
    }

    private void initView() {
        statusbar = (TextView) findViewById(R.id.status_bar);
        if (Build.VERSION.SDK_INT<21){
            statusbar.setVisibility(View.GONE);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLv = (LinearLayout) findViewById(R.id.lv_drawer_view);
        drawerLVi = (ListView) findViewById(R.id.lVi_left_drawer);
    }
    private void initFragments(){
        initLastConsumFragment();
        initChartFragment();
        initDesireFragment();
    }
    private void initLastConsumFragment(){
        fragment1 = Fragment1.newInstance("this is", "Fragment1");
        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.add(R.id.fragment_container, fragment1).hide(fragment1).commit();
    }
    private void initChartFragment(){
        fragment2 = Fragment2.newInstance("this is", "Fragment2");
        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.add(R.id.fragment_container, fragment2).hide(fragment2).commit();
    }
    private void initDesireFragment(){
        fragment3 = Fragment3.newInstance("this is", "Fragment3");
        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.add(R.id.fragment_container, fragment3).hide(fragment3).commit();
    }

    private void setViewContent() {
        fragmentTitleList = new ArrayList<>();
        fragmentTitleList.add(getResources().getString(R.string.navigation_home));
        fragmentTitleList.add(getResources().getString(R.string.navigation_chart));
        fragmentTitleList.add(getResources().getString(R.string.navigation_desire));
        drawerAdapter = new DrawerListviewAdapter(this, fragmentTitleList);
        drawerLVi.setAdapter(drawerAdapter);
        drawerLVi.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.action_settings, R.string.action_settings) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        switch (position) {
            case PAGE_HOME:
                transaction.hide(getCurrentFragment(fragmentIndicator)).show(fragment1).commit();
                toolbar.setTitle(getResources().getString(R.string.app_name));
                fragmentIndicator = PAGE_HOME;
                break;
            case PAGE_CHART:
                transaction.hide(getCurrentFragment(fragmentIndicator)).show(fragment2).commit();
                toolbar.setTitle(getResources().getString(R.string.navigation_chart));
                fragmentIndicator = PAGE_CHART;
                break;
            case PAGE_DESIRE:
                transaction.hide(getCurrentFragment(fragmentIndicator)).show(fragment3).commit();
                toolbar.setTitle(getResources().getString(R.string.navigation_desire));
                fragmentIndicator = PAGE_DESIRE;
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(drawerLv);
    }
    public Fragment getCurrentFragment(int fragmentIndicator){
        if (fragmentIndicator == PAGE_HOME) return fragment1;
        if (fragmentIndicator == PAGE_CHART) return fragment2;
        if (fragmentIndicator == PAGE_DESIRE) return fragment3;
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_activity_menu, menu);
        return true;
    }
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){

            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), getString(R.string.more_action_down_exit_application), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

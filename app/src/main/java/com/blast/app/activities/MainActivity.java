package com.blast.app.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blast.app.R;
import com.blast.app.events.DrawerNavigationItemClickedEvent;
import com.blast.app.fragments.BrickGroupFragment;
import com.blast.app.fragments.BrickLineFragment;
import com.blast.app.fragments.FocusWoodFragment;
import com.blast.app.fragments.SoilFragment;
import com.blast.app.fragments.SteelBridgeGroupContactlessFragment;
import com.blast.app.fragments.SteelBridgeGroupFragment;
import com.blast.app.fragments.SteelBridgeLineFragment;
import com.blast.app.fragments.SteelFragment;
import com.blast.app.fragments.StoneBridgeFragment;
import com.blast.app.fragments.TrussBridgeFourFragment;
import com.blast.app.fragments.TrussBridgeOneFragment;
import com.blast.app.fragments.TrussBridgeSixFragment;
import com.blast.app.fragments.WoodFragment;
import com.blast.app.fragments.WoodenBridgeGroupFragment;
import com.blast.app.fragments.WoodenBridgeLineFragment;
import com.blast.app.utils.NavigationBus;
import com.blast.app.utils.SettingsUtil;
import com.blast.app.views.DrawerListView;
import com.squareup.otto.Subscribe;

import java.util.Locale;


public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private DrawerListView mNavList;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationBus mNavigationBus;
    private String mCurFragmentTitle;
    private boolean opened = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SettingsUtil.init(this);
        mNavigationBus = NavigationBus.getInstance();
        initActionBar();
        initViews();
        initFragment();
        initDrawer();



        // open drawerlayout on default
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (opened) {
                    mDrawerLayout.openDrawer(mNavList);
                }
            }
        }).start();

        // =========================
        Configuration config = getResources().getConfiguration();
        if (SettingsUtil.getLang()) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else {
            config.locale = Locale.ENGLISH;
        }
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        // =========================

        getActionBar().setTitle(R.string.app_name);
        initFragment();
        initDrawer();
    }

    private void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_navigation_drawer, R.string.drawer_open_title, R.string.drawer_close_title) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (getActionBar() == null)
                    return;
//                getActionBar().setTitle(R.string.drawer_close_title);
                getActionBar().setTitle(mCurFragmentTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getActionBar() == null)
                    return;
                getActionBar().setTitle(R.string.drawer_open_title);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initFragment() {
        replace(SoilFragment.getInstance());
        mCurFragmentTitle = getString(R.string.app_name);
    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavList = (DrawerListView) findViewById(R.id.navList);
        getFragmentManager().beginTransaction().replace(R.id.container, WoodFragment.getInstacne()).commit();
    }

    private void initActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (mDrawerLayout != null && mNavList != null) {
//            MenuItem item = menu.findItem(R.id.action_settings);
//            if (item != null) {
//                item.setVisible(!mDrawerLayout.isDrawerOpen(mNavList));
//            }
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNavigationBus.register(this);
    }

    @Override
    protected void onStop() {
        mNavigationBus.unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onDrawerNavigationClickedEvent(DrawerNavigationItemClickedEvent event) {
        if (mCurFragmentTitle.equalsIgnoreCase(event.section)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        mCurFragmentTitle = event.section;
        getActionBar().removeAllTabs();
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        getActionBar().setTitle(mCurFragmentTitle);
        switch (event.pos) {
            case 0:
                replace(SoilFragment.getInstance());
                break;
            case 1:
                replace(WoodFragment.getInstacne());
                break;
            case 2:
                replace(FocusWoodFragment.getInstance());
                break;
            case 3:
                replace(SteelFragment.getInstance());
                break;
            case 4:
                setBrickTabNav(getActionBar());
//                replace(BrickFragment.getInstance());
                break;
            case 5:
                setWoodenBridgeTabNav(getActionBar());
                break;
            case 6:
                replace(StoneBridgeFragment.getInstance());
                break;
            case 7:
                setSteelBridgeTabNav(getActionBar());
                break;
            case 8:
                setTrussBridgeTabNav(getActionBar());
                break;
//            case 5:
//                replace(BridgeFragment.getInstance());
//                break;
        }
        mDrawerLayout.closeDrawers();
    }

    private void setWoodenBridgeTabNav(ActionBar actionBar) {
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.wooden_bridge_line_tab_title)
//                .setTabListener(new MyTabListener(this, WoodFragment.class.getName()));
                .setTabListener(new MyTabListener(this, WoodenBridgeLineFragment.class.getName()));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.wooden_bridge_group_tab_title)
//                .setTabListener(new MyTabListener(this, FocusWoodFragment.class.getName()));
                .setTabListener(new MyTabListener(this, WoodenBridgeGroupFragment.class.getName()));
        actionBar.addTab(tab);
    }

    private void setSteelBridgeTabNav(ActionBar actionBar) {
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.steel_bridge_line_tab_title)
                .setTabListener(new MyTabListener(this, SteelBridgeLineFragment.class.getName()));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.steel_bridge_group_tab_title)
                .setTabListener(new MyTabListener(this, SteelBridgeGroupFragment.class.getName()));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.steel_bridge_group_contactless_tab_title)
                .setTabListener(new MyTabListener(this, SteelBridgeGroupContactlessFragment.class.getName()));
        actionBar.addTab(tab);
    }

    private void setTrussBridgeTabNav(ActionBar actionBar) {
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.truss_bridge_one_tab_title)
                .setTabListener(new MyTabListener(this, TrussBridgeOneFragment.class.getName()));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.truss_bridge_four_tab_title)
                .setTabListener(new MyTabListener(this, TrussBridgeFourFragment.class.getName()));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.truss_bridge_six_tab_title)
                .setTabListener(new MyTabListener(this, TrussBridgeSixFragment.class.getName()));
        actionBar.addTab(tab);
    }

    private void setBrickTabNav(ActionBar actionBar) {
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.brick_group_tab_title)
                .setTabListener(new MyTabListener(this, BrickGroupFragment.class.getName()));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.brick_line_tab_title)
                .setTabListener(new MyTabListener(this, BrickLineFragment.class.getName()));
        actionBar.addTab(tab);

    }

    private void replace(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
//        Fragment.instantiate(this, "class");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

        Configuration config = getResources().getConfiguration();
        switch (item.getItemId()) {
            case R.id.action_china:
                config.locale = Locale.SIMPLIFIED_CHINESE;
                SettingsUtil.setLang(true);
//                AppUtil.toast(this, "Action_china");
                break;
            case R.id.action_english:
                config.locale = Locale.ENGLISH;
                SettingsUtil.setLang(false);
//                AppUtil.toast(this, "Action_english");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // 如果要当前显示语言切换效果，则要重启当前页：
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mDrawerLayout.isDrawerOpen(mNavList)) {
                    mDrawerLayout.closeDrawers();
                    return true;
                }
        }
        return super.onKeyDown(keyCode, event);
    }

    // inner class ActionBar.TabListener
    private class MyTabListener implements ActionBar.TabListener {
        private Fragment mFragment;
        private final Activity mActivity;
        private final String mFragName;

        private MyTabListener(Activity mActivity, String mFragName) {
            this.mActivity = mActivity;
            this.mFragName = mFragName;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            mFragment = Fragment.instantiate(mActivity, mFragName);
            ft.replace(R.id.container, mFragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.remove(mFragment);
            mFragment = null;
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    }
}

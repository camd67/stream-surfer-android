package com.streamsurfer.surfers.streamsurfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "surfers.BaseActivity";
    public static final String EXTRA_SELECTED_NAV_ITEM = "com.streamsurfer.surfers.SelectedNavItem";

    private DrawerLayout drawer;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID)
    {
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) drawer.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(drawer);

        navView = (NavigationView)findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavMenuItemSelect(item);
            }
        });
        int selectedItem = getIntent().getIntExtra(EXTRA_SELECTED_NAV_ITEM, -1);
        if(selectedItem != -1){
            navView.setCheckedItem(selectedItem);
        }
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_menu);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStop() {
        EntriesApp.getInstance().getMyListManager(this).saveMyListData(this);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_icons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // Left hamburger icon
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                return true;
            case R.id.search_icon:
                Intent search = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(search);
                return true;
            case R.id.my_list_icon:
                Intent iList = new Intent(this, MyListActivity.class);
                startActivity(iList);
                return true;
            default:
                // keep this, we need it if we don't know how to handle it
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean handleNavMenuItemSelect(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //navView.setCheckedItem(id);

        switch (id){
            case R.id.nav_search:
                Intent search = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(search);
                break;
            case R.id.nav_advanced_search:
                Intent advanced = new Intent(BaseActivity.this, AdvancedSearch.class);
                startActivity(advanced);
                break;
            case R.id.nav_genres:
                Intent genre = new Intent(BaseActivity.this, BrowseActivity.class);
                genre.putExtra(MainActivity.OPTION, "genres");
                startActivity(genre);
                break;
            case R.id.nav_recently_updated:
                Intent updated = new Intent(BaseActivity.this, UpdatedActivity.class);
                startActivity(updated);
                break;
            case R.id.nav_services:
                Intent service = new Intent(BaseActivity.this, BrowseActivity.class);
                service.putExtra(MainActivity.OPTION, "services");
                startActivity(service);
                break;
            case R.id.nav_my_list:
                Intent i = new Intent(this, MyListActivity.class);
                i.putExtra(BaseActivity.EXTRA_SELECTED_NAV_ITEM, id);
                startActivity(i);
                break;
            case R.id.nav_settings:
                Intent settings = new Intent(BaseActivity.this, SettingsActivity.class);
                startActivity(settings);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        // return false to not change color
        return false;
    }
}

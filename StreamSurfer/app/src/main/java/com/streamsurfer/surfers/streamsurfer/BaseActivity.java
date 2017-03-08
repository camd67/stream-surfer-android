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
                Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.my_list_icon:
                Toast.makeText(this, "My list", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_advanced_search:
                Toast.makeText(this, "Adv. Searching....", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_genres:
                Toast.makeText(this, "Genre category", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_popular:
                Toast.makeText(this, "Popular category", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_recently_updated:
                Toast.makeText(this, "Recently updated category", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_services:
                Toast.makeText(this, "Services category", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_my_list:
                Intent i = new Intent(this, MyListActivity.class);
                i.putExtra(BaseActivity.EXTRA_SELECTED_NAV_ITEM, id);
                startActivity(i);
                Toast.makeText(this, "My List", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        // return false to not change color
        return false;
    }
}

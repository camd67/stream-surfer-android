package com.streamsurfer.surfers.streamsurfer;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "surfers.BaseActivity";

    @Override
    public void setContentView(int layoutResID)
    {
        //DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        //FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        //getLayoutInflater().inflate(layoutResID, activityContainer, true);
        //super.setContentView(fullView);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        setSupportActionBar(toolbar);
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
                Toast.makeText(this, "Opening nav menu", Toast.LENGTH_SHORT).show();
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
}

package com.streamsurfer.surfers.streamsurfer;

import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "surfers.BaseActivity";

    private static final String[] drawerItems = {"Search", "Advanced Search", "Popular", "Recently Updated",
            "Genres", "Services", "MyList", "Settings", "Logout"};
    private DrawerLayout drawer;

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

        ListView drawerList = (ListView)findViewById(R.id.drawer_list);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerItems));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
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
                if(drawer.isDrawerOpen(Gravity.START)){
                    drawer.closeDrawer(Gravity.START);
                } else {
                    drawer.openDrawer(Gravity.START);
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

    private void selectItem(int position){
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }
}

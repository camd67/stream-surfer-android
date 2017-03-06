package com.streamsurfer.surfers.streamsurfer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String RESULTS = "results";
    public static final String OPTION = "option";
    private Entries entries = Entries.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fix so it waits while user gives permission
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        final Map<String, Entry> entryList = entries.getEntries();
        final Map<String, List<Entry>> genreMap = entries.getGenreMap();
        final Set<String> entryKeys = entryList.keySet();

        //todo not done with drawer... moved on because it was taking too long
        final DrawerLayout layout = (DrawerLayout) findViewById(R.id.activity_main);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                layout,
                //R.mipmap.ic_drawer, /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        layout.addDrawerListener(toggle);

        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        final ListView drawerList = (ListView) findViewById(R.id.left_drawer);
        final TextView searchInput = (TextView) findViewById(R.id.search_input);
        Button searchButton = (Button) findViewById(R.id.search_button);
        String[] layoutValues = {"Search", "Advanced Search", "Popular", "Recently Updated",
        "Genres", "Services", "MyList", "Settings", "Logout"};

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, layoutValues));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchInput.getText().toString().toLowerCase();
                Intent activity = new Intent(MainActivity.this, Results.class);
                activity.putExtra(RESULTS, search);

                Intent test = new Intent(MainActivity.this, Browse.class);
                test.putExtra(OPTION, "services");
                startActivity(test);
                //startActivity(activity);
            }
        });
    }
}

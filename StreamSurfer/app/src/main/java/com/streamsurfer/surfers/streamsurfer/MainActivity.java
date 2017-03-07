package com.streamsurfer.surfers.streamsurfer;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String RESULTS = "results";
    public static final String OPTION = "option";
    private Entries entries = Entries.getInstance();
    private static DownloadManager dm = null;
    private Map<String, Entry> entryList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        //fix so it waits while user gives permission
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        dm.enqueue(new DownloadManager.Request(Uri.parse("http://moebot.net/sampleData.json"))
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                        DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        "sampleData.json"));



        //todo not done with drawer... moved on because it was taking too long
        /*final DrawerLayout layout = (DrawerLayout) findViewById(R.id.activity_main);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                layout,
                //R.mipmap.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        layout.addDrawerListener(toggle);

        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        final ListView drawerList = (ListView) findViewById(R.id.left_drawer);

        String[] layoutValues = {"Search", "Advanced Search", "Popular", "Recently Updated",
        "Genres", "Services", "MyList", "Settings", "Logout"};

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, layoutValues));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/


    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager.Query query = new DownloadManager.Query();
            Cursor c = dm.query(query);
            query.setFilterByStatus(DownloadManager.STATUS_FAILED|DownloadManager.STATUS_PAUSED|DownloadManager.STATUS_SUCCESSFUL|DownloadManager.STATUS_RUNNING|DownloadManager.STATUS_PENDING);
            if(c.moveToFirst()) {
                int status =c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (status==DownloadManager.STATUS_SUCCESSFUL) {
                    entryList = entries.getEntries();
                    final Map<String, List<Entry>> genreMap = entries.getGenreMap();
                    final Set<String> entryKeys = entryList.keySet();

                    final TextView searchInput = (TextView) findViewById(R.id.search_input);
                    Button searchButton = (Button) findViewById(R.id.search_button);
                    Button advanced = (Button) findViewById(R.id.advanced_search_button);

                    searchButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String search = searchInput.getText().toString().toLowerCase();
                            Intent activity = new Intent(MainActivity.this, Results.class);
                            activity.putExtra(RESULTS, search);

                            Intent test = new Intent(MainActivity.this, Browse.class);
                            test.putExtra(OPTION, "services");
                            //startActivity(test);
                            startActivity(activity);
                        }
                    });

                    advanced.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent advanced = new Intent(MainActivity.this, AdvancedSearch.class);
                            startActivity(advanced);
                        }
                    });
                }
            }
            unregisterReceiver(onComplete);
        }
    };
}

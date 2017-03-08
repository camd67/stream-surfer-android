package com.streamsurfer.surfers.streamsurfer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends BaseActivity {

    public static final String RESULTS = "results";
    public static final String OPTION = "option";
    private EntriesApp entries = EntriesApp.getInstance();

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
        final TextView searchInput = (TextView) findViewById(R.id.search_input);
        Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchInput.getText().toString().toLowerCase();
                Intent activity = new Intent(MainActivity.this, ResultsActivity.class);
                activity.putExtra(RESULTS, search);

                Intent test = new Intent(MainActivity.this, BrowseActivity.class);
                test.putExtra(OPTION, "services");
                startActivity(test);
                //startActivity(activity);
            }
        });
    }
}

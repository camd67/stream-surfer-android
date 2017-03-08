package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdvancedSearch extends BaseActivity {

    private EntriesApp entries = EntriesApp.getInstance();
    private Map<String, Entry> entryMap;
    public static final String GENRELIST = "genre";
    public static final String SERVICELIST = "service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        entryMap = entries.getEntries(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        final ListView genres = (ListView) findViewById(R.id.advanced_genre_list);
        final ListView services = (ListView) findViewById(R.id.advanced_service_list);
        final Button search = (Button) findViewById(R.id.advanced_button);
        final EditText input = (EditText) findViewById(R.id.advanced_input);
        final AdvancedAdapter genreAdapter = new AdvancedAdapter(this, entries.getGenreList());
        final AdvancedAdapter serviceAdapter = new AdvancedAdapter(this, entries.getServiceList());

        genres.setAdapter(genreAdapter);
        services.setAdapter(serviceAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(AdvancedSearch.this, ResultsActivity.class);
                activity.putStringArrayListExtra(GENRELIST, (ArrayList<String>) genreAdapter.getResults());
                activity.putStringArrayListExtra(SERVICELIST, (ArrayList<String>) serviceAdapter.getResults());
                activity.putExtra(MainActivity.RESULTS, input.getText().toString().toLowerCase());
                startActivity(activity);
            }
        });
    }
}

package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResultsActivity extends BaseActivity {

    private EntriesApp entries = EntriesApp.getInstance();
    public static final String SELECTED = "selected";
    private Map<String, Entry> entryMap = entries.getEntries();
    private List<Entry> results;
    private SetAdapter setAdapter = new SetAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String search = getIntent().getStringExtra(MainActivity.RESULTS);
        final ArrayList<String> genreList = getIntent().getStringArrayListExtra(AdvancedSearch.GENRELIST);
        final ArrayList<String> serviceList = getIntent().getStringArrayListExtra(AdvancedSearch.SERVICELIST);

        getAdvancedResults(search, genreList, serviceList);

        final ListView resultList = (ListView) findViewById(R.id.result_list);
        TextView title = (TextView) findViewById(R.id.results_title);
        title.setText(search);
        setAdapter.customAdapterSet(resultList, results, this);
    }

    private void getAdvancedResults(String search, ArrayList<String> genreList, ArrayList<String> serviceList) {
        results = new ArrayList<>();
        List<Entry> temporaryResults = new ArrayList<>();
        Set<String> entryKeys = entryMap.keySet();
        for (String s : entryKeys) {
            if (s.contains(search)) {
                temporaryResults.add(entryMap.get(s));
            }
        }
        for (Entry e : temporaryResults) {
            Boolean removeGenre = false;
            Boolean removeService = false;
            List<String> genres = e.getGenres();
            if (genreList != null) {
                for (String genre : genreList) {
                    if (!genres.contains(genre.toLowerCase())) {
                        removeGenre = true;
                        break;
                    }
                }
            }
            List<Service> services = e.getServices();
            if (serviceList != null) {
                for (String service : serviceList) {
                    if (!services.contains(new Service(service, null, null))) {
                        removeService = true;
                        break;
                    }
                }
            }
            if (!removeGenre && !removeService) {
                results.add(e);
            }
        }
    }

    public void clickHandlerLeft(View v) {
        clickHandler(v, R.id.title_left);
    }

    public void clickHandlerRight(View v) {
        clickHandler(v, R.id.title_right);
    }

    private void clickHandler(View v, int id) {
        LinearLayout row = (LinearLayout) v.getParent();
        TextView title = (TextView) row.findViewById(id);
        String selected = title.getText().toString().toLowerCase();
        Intent detail = new Intent(ResultsActivity.this, DetailsActivity.class);
        detail.putExtra(SELECTED, selected);
        startActivity(detail);
    }
}

package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Results extends AppCompatActivity {

    private Entries entries = Entries.getInstance();
    public static final String SELECTED = "selected";
    private Map<String, Entry> entryMap = entries.getEntries();
    private List<Entry> results = new ArrayList<>();
    private SetAdapter setAdapter = new SetAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String search = getIntent().getStringExtra(MainActivity.RESULTS);
        ArrayList<String> genreList = getIntent().getStringArrayListExtra(AdvancedSearch.GENRELIST);
        ArrayList<String> serviceList = getIntent().getStringArrayListExtra(AdvancedSearch.SERVICELIST);

        //get results
        getAdvancedResults(search, genreList, serviceList);

        final ListView resultList = (ListView) findViewById(R.id.result_list);
        final EditText input = (EditText) findViewById(R.id.results_input);
        Button searchButton = (Button) findViewById(R.id.results_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newSearch = input.getText().toString().toLowerCase();
                results = getResults(newSearch);
                setAdapter.customAdapterSet(resultList, results, Results.this);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        setAdapter.customAdapterSet(resultList, results, this);
    }

    private void getAdvancedResults(String search, ArrayList<String> genreList, ArrayList<String> serviceList) {
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
            for (String genre : genreList) {
                if (!genres.contains(genre.toLowerCase())) {
                    removeGenre = true;
                    break;
                }
            }
            List<Service> services = e.getServices();
            for (String service : serviceList) {
                if (!services.contains(new Service(service, null, null))) {
                    removeService = true;
                    break;
                }
            }
            if (!removeGenre && !removeService) {
                results.add(e);
            }
        }
    }

    private List<Entry> getResults(String search) {
        List<Entry> results = new ArrayList<>();

        return results;
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
        Intent detail = new Intent(Results.this, Details.class);
        detail.putExtra(SELECTED, selected);
        startActivity(detail);
    }
}

package com.streamsurfer.surfers.streamsurfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class BrowseActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private EntriesApp entries = EntriesApp.getInstance();
    private Map<String, List<Entry>> results;
    private SetAdapter setAdapter = new SetAdapter();
    private ListView specificResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        String option = getIntent().getStringExtra(MainActivity.OPTION);
        specificResultList = (ListView) findViewById(R.id.specific_results_list);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        if (option.equals("genres")) {
            results = entries.getGenreMap(this);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, entries.getGenreList());
            spinner.setAdapter(adapter);
        } else if (option.equals("services")) {
            results = entries.getServiceMap(this);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, entries.getServiceList());
            spinner.setAdapter(adapter);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String name = parent.getItemAtPosition(pos).toString();
        setAdapter.customAdapterSet(specificResultList, results.get(name.toUpperCase()), this);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
        Intent detail = new Intent(BrowseActivity.this, DetailsActivity.class);
        detail.putExtra(ResultsActivity.SELECTED, selected);
        startActivity(detail);
    }
}

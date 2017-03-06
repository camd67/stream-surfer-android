package com.streamsurfer.surfers.streamsurfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class Browse extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Entries entries = Entries.getInstance();
    private Map<String, List<Entry>> results;
    private SetAdapter setAdapter = new SetAdapter();
    private TextView title;
    private ListView specificResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        String option = getIntent().getStringExtra(MainActivity.OPTION);
        specificResultList = (ListView) findViewById(R.id.specific_results_list);
        title = (TextView) findViewById(R.id.search_title);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        if (option.equals("genres")) {
            results = entries.getGenreMap();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, entries.getGenreList());
            spinner.setAdapter(adapter);
        } else if (option.equals("services")) {
            results = entries.getServiceMap();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, entries.getServiceList());
            spinner.setAdapter(adapter);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String name = parent.getItemAtPosition(pos).toString();
        title.setText(name.toUpperCase());
        setAdapter.customAdapterSet(specificResultList, results.get(name.toUpperCase()), this);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}

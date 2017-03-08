package com.streamsurfer.surfers.streamsurfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UpdatedActivity extends AppCompatActivity {
    private EntriesApp entries = EntriesApp.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated);

        final Map<Date, Entry> updated = entries.getUpdated();
        final List<Date> sorted = entries.getSortedList();
        ListView updatedList = (ListView) findViewById(R.id.updated_list);

        List<Entry> results = new ArrayList<>();
        for (Date d : sorted) {
            results.add(updated.get(d));
        }


    }
}

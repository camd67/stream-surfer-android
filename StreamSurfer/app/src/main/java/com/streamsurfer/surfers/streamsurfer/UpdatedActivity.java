package com.streamsurfer.surfers.streamsurfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UpdatedActivity extends AppCompatActivity {
    private EntriesApp entries = EntriesApp.getInstance();
    private Map<Date, Entry> updated = entries.getUpdated();
    private List<Date> sorted = entries.getSortedList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated);

        ListView updatedList = (ListView) findViewById(R.id.updated_list);

        List<Entry> results = new ArrayList<>();
        for (Date d : sorted) {
            results.add(updated.get(d));
        }

        List<String> updated = getUpdatedArray();
    }

    private List<String> getUpdatedArray() {
        List<String> results = new ArrayList<>();
        Calendar current = Calendar.getInstance();
        for (Date d : sorted) {
            Calendar check = Calendar.getInstance();
            check.setTime(d);
            if (current.get(Calendar.YEAR) == check.get(Calendar.YEAR)) {
                if (current.get(Calendar.MONTH) == check.get(Calendar.MONTH)) {
                    if (current.get(Calendar.DATE) == check.get(Calendar.DATE)) {

                    }
                }
            }
        }
        return results;
    }
}

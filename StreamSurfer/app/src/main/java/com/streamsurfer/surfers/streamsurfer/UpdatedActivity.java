package com.streamsurfer.surfers.streamsurfer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UpdatedActivity extends BaseActivity {
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

        int size = results.size();
        String[] titlesLeft = new String[size / 2 + size % 2];
        String[] titlesRight = new String[size / 2 + size % 2];
        int[] imagesLeft = new int[size / 2 + size % 2];
        int[] imagesRight = new int[size / 2 + size % 2];
        String[] updatedLeft = new String[size / 2 + size % 2];
        String[] updatedRight = new String[size / 2 + size % 2];
        boolean even = false;
        for (int i = 0; i < results.size(); i ++) {
            if (even) {
                titlesRight[i / 2] = results.get(i).getTitle();
                //todo replace
                imagesRight[i / 2] = R.mipmap.ic_launcher;
                updatedRight[i / 2] = updated.get(i);
            } else {
                titlesLeft[i / 2] = results.get(i).getTitle();
                //todo replace
                imagesLeft[i / 2] = R.mipmap.ic_launcher;
                updatedLeft[i / 2] = updated.get(i);
            }
            even = !even;
        }
        updatedList.setAdapter(new UpdatedAdapter(this, titlesLeft, titlesRight, imagesLeft, imagesRight, updatedLeft, updatedRight));
    }

    private List<String> getUpdatedArray() {
        List<String> results = new ArrayList<>();
        Calendar current = Calendar.getInstance();
        for (Date d : sorted) {
            String updatedString = "(updated ";
            Calendar check = Calendar.getInstance();
            check.setTime(d);
            long milliseconds1 = current.getTimeInMillis();
            long milliseconds2 = check.getTimeInMillis();
            long diff = milliseconds1 - milliseconds2;
            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
            if (diffDays == 0) {
                updatedString += "today)";
            } else if (diffDays == 1) {
                updatedString += "a day ago)";
            } else if (diffDays < 30) {
                updatedString += diffDays + " days ago)";
            } else if (diffDays < 60) {
                updatedString += "a month ago)";
            } else if (diffDays < 365) {
                updatedString += (diffDays / 30) + " months ago)";
            } else if (diffDays < 730) {
                updatedString += "a year ago)";
            } else {
                updatedString += (diffDays / 365) + " years ago)";
            }
            results.add(updatedString);
        }
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
        Intent detail = new Intent(UpdatedActivity.this, DetailsActivity.class);
        detail.putExtra(ResultsActivity.SELECTED, selected);
        startActivity(detail);
    }
}

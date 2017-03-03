package com.streamsurfer.surfers.streamsurfer;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Results extends AppCompatActivity {

    private Entries entries = Entries.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        List<Entry> results = entries.getResults();
        int size = results.size();
        String[] titlesLeft = new String[size / 2 + size % 2];
        String[] titlesRight = new String[size / 2 + size % 2];
        int[] imagesLeft = new int[size / 2 + size % 2];
        int[] imagesRight = new int[size / 2 + size % 2];
        boolean even = false;
        for (int i = 0; i < results.size(); i ++) {
            if (even) {
                titlesRight[i / 2] = results.get(i).getTitle();
                //replace
                imagesRight[i / 2] = R.mipmap.ic_launcher;
            } else {
                titlesLeft[i / 2] = results.get(i).getTitle();
                //replace
                imagesLeft[i / 2] = R.mipmap.ic_launcher;
            }
            even = !even;

        }
        ListView resultList = (ListView) findViewById(R.id.result_list);
        resultList.setAdapter(new ResultsAdapter(Results.this, titlesLeft, titlesRight, imagesLeft, imagesRight));
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
        Entry selected = entries.getEntries().get(title.getText().toString().toLowerCase());
        entries.setSelected(selected);
        Intent detail = new Intent(Results.this, Details.class);
        startActivity(detail);
    }
}

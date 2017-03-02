package com.streamsurfer.surfers.streamsurfer;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class Results extends AppCompatActivity {

    public Entries entries = Entries.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        List<Entry> results = entries.getResults();
        int size = results.size();
        String[] titlesLeft = new String[size / 2 + size % 2];
        String[] titlesRight = new String[size / 2];
        int[] imagesLeft = new int[size / 2 + size % 2];
        int[] imagesRight = new int[size / 2];
        boolean even = false;
        for (int i = 0; i < results.size(); i ++) {
            if (even) {
                titlesRight[i / 2] = results.get(i).getTitle();
                imagesRight[i / 2] = R.mipmap.ic_launcher;
            } else {
                titlesLeft[i / 2] = results.get(i).getTitle();
                imagesLeft[i / 2] = R.mipmap.ic_launcher;
            }
            even = !even;
        }
        ListView resultListLeft = (ListView) findViewById(R.id.result_list_left);
        resultListLeft.setAdapter(new ResultsAdapter(Results.this, titlesLeft, imagesLeft));

        ListView resultListRight = (ListView) findViewById(R.id.result_list_right);
        resultListRight.setAdapter(new ResultsAdapter(Results.this, titlesRight, imagesRight));
    }
}

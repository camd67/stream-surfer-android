package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Jack on 3/5/2017.
 */

public class SetAdapter {

    public SetAdapter() {}

    public void customAdapterSet(ListView resultList, List<Entry> results, Context context) {
        int size = results.size();
        String[] titlesLeft = new String[size / 2 + size % 2];
        String[] titlesRight = new String[size / 2 + size % 2];
        int[] imagesLeft = new int[size / 2 + size % 2];
        int[] imagesRight = new int[size / 2 + size % 2];
        boolean even = false;
        for (int i = 0; i < results.size(); i ++) {
            if (even) {
                titlesRight[i / 2] = results.get(i).getTitle();
                //todo replace
                imagesRight[i / 2] = R.mipmap.ic_launcher;
            } else {
                titlesLeft[i / 2] = results.get(i).getTitle();
                //todo replace
                imagesLeft[i / 2] = R.mipmap.ic_launcher;
            }
            even = !even;
        }
        resultList.setAdapter(new ResultsAdapter(context, titlesLeft, titlesRight, imagesLeft, imagesRight));
    }
}

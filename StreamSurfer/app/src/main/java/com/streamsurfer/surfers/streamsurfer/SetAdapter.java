package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

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
                String uri = results.get(i).getThumbnail().replace('/', '_');
                uri = uri.substring(0, uri.length() - 4);
                uri = "drawable/" + uri;
                int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
                if (imageResource != 0) {
                    imagesRight[i / 2] = imageResource;
                } else {
                    imagesRight[i / 2] = R.mipmap.ic_launcher;
                }
            } else {
                titlesLeft[i / 2] = results.get(i).getTitle();
                String uri = results.get(i).getThumbnail().replace('/', '_');
                uri = uri.substring(0, uri.length() - 4);
                uri = "drawable/" + uri;
                int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
                if (imageResource != 0) {
                    imagesLeft[i / 2] = imageResource;
                } else {
                    imagesLeft[i / 2] = R.mipmap.ic_launcher;
                }
            }
            even = !even;
        }
        resultList.setAdapter(new ResultsAdapter(context, titlesLeft, titlesRight, imagesLeft, imagesRight));
    }

    public void updatedAdapterSet(ListView resultList, List<Entry> results, Context context) {

    }
}

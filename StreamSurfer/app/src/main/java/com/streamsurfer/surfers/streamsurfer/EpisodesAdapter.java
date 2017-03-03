package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jack on 3/2/2017.
 */

public class EpisodesAdapter extends BaseAdapter {
    private Context context;
    private int[] episodeNumber;
    private int[] seasonNumber;
    private String[] episodeTitle;
    private static LayoutInflater inflater = null;

    public EpisodesAdapter(Details activity, int[] episodeNumber, int[] seasonNumber, String[] episodeTitle) {
        this.context = activity;
        this.episodeNumber = episodeNumber;
        this.seasonNumber = seasonNumber;
        this.episodeTitle = episodeTitle;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return episodeTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(R.layout.episodes_list, null);
        TextView tv = (TextView) rowView.findViewById(R.id.episode_list_title);
        tv.setText("Season " + seasonNumber[position] + " Ep. " + episodeNumber[position] +
        " - " + episodeTitle[position]);
        return rowView;
    }
}

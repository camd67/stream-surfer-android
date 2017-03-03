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

public class ServicesAdapter extends BaseAdapter {
    private String[] titles;
    private Context context;
    private int[] images;
    private static LayoutInflater inflater = null;

    public ServicesAdapter(Details activity, String[] titles, int[] images) {
        this.titles = titles;
        context = activity;
        this.images = images;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return titles.length;
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
        rowView = inflater.inflate(R.layout.services_list, null);
        TextView tv = (TextView) rowView.findViewById(R.id.service_title);
        ImageView img = (ImageView) rowView.findViewById(R.id.service_image);
        LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.linear_layout);
        tv.setText(titles[position]);
        img.setImageResource(images[position]);
        return rowView;
    }
}

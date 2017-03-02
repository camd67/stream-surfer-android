package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jack on 3/1/2017.
 */

public class ResultsAdapter extends BaseAdapter {
    private String [] result;
    private Context context;
    private int [] imageId;
    private static LayoutInflater inflater = null;

    public ResultsAdapter(Results activity, String[] titles, int[] images) {
        result = titles;
        context = activity;
        imageId = images;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        TextView ttv=(TextView) rowView.findViewById(R.id.title_text);
        ImageView img=(ImageView) rowView.findViewById(R.id.imageView1);
        ttv.setText(result[position]);
        img.setImageResource(imageId[position]);
        return rowView;
    }

}

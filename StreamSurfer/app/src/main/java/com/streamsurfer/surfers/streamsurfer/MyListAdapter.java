package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends BaseAdapter {
    private Context context;
    private List<ListEntry> entries;
    private static LayoutInflater inflater;

    public MyListAdapter(Context context, ListEntry[] entries) {
        this.entries = new ArrayList<>();
        this.context = context;
        for (int i = 0; i < entries.length; i++) {
            this.entries.add(entries[i]);
        }
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return entries.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListEntry entry = entries.get(position);
        View row = convertView;
        if(row == null){
            row = inflater.inflate(R.layout.item_my_list, null);
        }

        ImageView thumbnail = (ImageView)row.findViewById(R.id.my_list_thumbnail);
        TextView title = (TextView)row.findViewById(R.id.my_list_title_header);
        TextView status = (TextView)row.findViewById(R.id.my_list_status);
        TextView rating = (TextView)row.findViewById(R.id.my_list_rating);
        Button plusOne = (Button)row.findViewById(R.id.my_list_plus_one);
        Button edit = (Button)row.findViewById(R.id.my_list_edit);

        Resources res = context.getResources();
        setText(entry.getTitle(), title);
        setText(res.getString(R.string.my_list_rating, entry.getRating(), ListEntry.MAX_RATING), rating);

        if(entry.getStatus() == ShowStatus.COMPLETE){
            setText(res.getString(R.string.my_list_complete), status);
        } else if (entry.getStatus() == ShowStatus.DROPPED){
            setText(res.getString(R.string.my_list_dropped, entry.getEpsSeen(), entry.getTotalEps()), status);
        } else if (entry.getStatus() == ShowStatus.WATCHING){
            setText(res.getString(R.string.my_list_watching, entry.getEpsSeen(), entry.getTotalEps()), status);
        } else {
            // default to PTW
            setText(res.getString(R.string.my_list_plan_to_watch), status);
        }

        plusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry.incrementEps();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "To implement edit....", Toast.LENGTH_SHORT).show();
            }
        });
        return  row;
    }

    private void setText(String text, TextView v){
        if(v != null){
            v.setText(text);
        }
    }
}

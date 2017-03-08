package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 3/6/2017.
 */

public class AdvancedAdapter extends BaseAdapter {
    private String[] titles;
    private List<String> results;
    private Context context;
    private static LayoutInflater inflater = null;

    public AdvancedAdapter(Context activity, String[] titles) {
        results = new ArrayList<>();
        this.titles = titles;
        context = activity;
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
        rowView = inflater.inflate(R.layout.advanced_list, null);
        final CheckBox cb = (CheckBox) rowView.findViewById(R.id.advanced_checkbox);
        cb.setText(titles[position]);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    results.add(cb.getText().toString());
                } else {
                    results.remove(cb.getText().toString());
                }
            }
        });
        return rowView;
    }

    public ArrayList<String> getResults() {
        return new ArrayList<>(results);
    }
}

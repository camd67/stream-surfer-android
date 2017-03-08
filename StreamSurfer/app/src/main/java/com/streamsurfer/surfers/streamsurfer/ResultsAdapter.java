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
import android.widget.Toast;


public class ResultsAdapter extends BaseAdapter {
    private String[] resultLeft;
    private String[] resultRight;
    private Context context;
    private int[] imageIdLeft;
    private int[] imageIdRight;
    private static LayoutInflater inflater = null;

    public ResultsAdapter(Context activity, String[] titlesLeft, String[] titlesRight, int[] imagesLeft, int[] imagesRight) {
        resultLeft = titlesLeft;
        resultRight = titlesRight;
        context = activity;
        imageIdLeft = imagesLeft;
        imageIdRight = imagesRight;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return resultLeft.length;
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
        rowView = inflater.inflate(R.layout.results_list, null);
        TextView tvl = (TextView) rowView.findViewById(R.id.title_left);
        TextView tvr = (TextView) rowView.findViewById(R.id.title_right);
        ImageView imgl = (ImageView) rowView.findViewById(R.id.image_left);
        ImageView imgr = (ImageView) rowView.findViewById(R.id.image_right);
        final Button butl = (Button) rowView.findViewById(R.id.button_add_to_list1);
        final Button butr = (Button) rowView.findViewById(R.id.button_add_to_list2);

        final MyListManager manager = EntriesApp.getInstance().getMyListManager(context);

        // setup left always
        if(manager.listContainsTitle(resultLeft[position])){
            butl.setEnabled(false);
        } else {
            final Entry selected = EntriesApp.getInstance().getEntries(context).get(resultLeft[position].toLowerCase());
            butl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    butl.setEnabled(false);
                    manager.addEntryToList(new ListEntry(selected.getTitle(), ShowStatus.PLAN_TO_WATCH, 0, selected.getEpisodes().size(), 0, selected.getThumbnail()));
                    Toast.makeText(context, "Added " + resultLeft[position] + " to your list!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        tvl.setText(resultLeft[position]);
        imgl.setImageResource(imageIdLeft[position]);

        // setup right if available
        if (resultRight[position] != null) {
            tvr.setText(resultRight[position]);
            imgr.setImageResource(imageIdRight[position]);
            if(manager.listContainsTitle(resultRight[position])){
                butr.setEnabled(false);
            } else {
                final Entry selected = EntriesApp.getInstance().getEntries(context).get(resultRight[position].toLowerCase());
                butr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        butr.setEnabled(false);
                        manager.addEntryToList(new ListEntry(selected.getTitle(), ShowStatus.PLAN_TO_WATCH, 0, selected.getEpisodes().size(), 0, selected.getThumbnail()));
                        Toast.makeText(context, "Added " + resultRight[position] + " to your list!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            LinearLayout right = (LinearLayout) rowView.findViewById(R.id.right_layout_click);
            right.setVisibility(View.GONE);
            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        }
        return rowView;
    }
}

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
 * Created by Jack on 3/1/2017.
 */

public class ResultsAdapter extends BaseAdapter {
    private String[] resultLeft;
    private String[] resultRight;
    private Context context;
    private int[] imageIdLeft;
    private int[] imageIdRight;
    private static LayoutInflater inflater = null;

    public ResultsAdapter(Results activity, String[] titlesLeft, String[] titlesRight, int[] imagesLeft, int[] imagesRight) {
        resultLeft = titlesLeft;
        resultRight = titlesRight;
        context = activity;
        imageIdLeft = imagesLeft;
        imageIdRight = imagesRight;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return resultLeft.length;
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
        TextView tvl = (TextView) rowView.findViewById(R.id.title_left);
        TextView tvr = (TextView) rowView.findViewById(R.id.title_right);
        ImageView imgl = (ImageView) rowView.findViewById(R.id.image_left);
        ImageView imgr = (ImageView) rowView.findViewById(R.id.image_right);
        LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.linear_layout);
        tvl.setText(resultLeft[position]);
        imgl.setImageResource(imageIdLeft[position]);
        if (resultRight[position] != null) {
            tvr.setText(resultRight[position]);
            imgr.setImageResource(imageIdRight[position]);
        } else {
            imgr.setVisibility(View.GONE);
            Button br = (Button) rowView.findViewById(R.id.button_right);
            br.setVisibility(View.GONE);
        }
        return rowView;
    }

}

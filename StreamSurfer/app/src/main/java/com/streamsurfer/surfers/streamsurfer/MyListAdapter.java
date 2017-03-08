package com.streamsurfer.surfers.streamsurfer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.Rating;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
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
        // TODO: Jack, insert image of show here (not episode)
        // ListEntry (entry here) has a getFilename method that gets the filename
        TextView title = (TextView)row.findViewById(R.id.my_list_title_header);
        final TextView status = (TextView)row.findViewById(R.id.my_list_status);
        RatingBar rating = (RatingBar) row.findViewById(R.id.my_list_rating);
        ImageButton plusOne = (ImageButton)row.findViewById(R.id.my_list_plus_one);
        ImageButton play = (ImageButton)row.findViewById(R.id.my_list_watch);
        Button delete = (Button)row.findViewById(R.id.my_list_delete);

        final Resources res = context.getResources();
        setText(entry.getTitle(), title);
        rating.setNumStars(ListEntry.MAX_RATING);
        rating.setStepSize(1);
        rating.setRating(entry.getRating());
        setStatusText(entry, status, res);
        //TODO getFileName is null right now. Make sure that it gets the thumbnail name from the
        //parsed json (unless I'm understanding this wrong)
        String uri = entry.getFilename().replace('/', '_');
        uri = uri.substring(0, uri.length() - 4);
        uri = "drawable/" + uri;
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        if (imageResource != 0) {
            thumbnail.setImageResource(imageResource);
        } else {
            thumbnail.setImageResource(R.mipmap.ic_launcher);
        }

        if(entry.getStatus() == ShowStatus.COMPLETE){
            plusOne.setVisibility(View.INVISIBLE);
        }

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser){
                    entry.setRating((int)rating);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entries.remove(entry);
                EntriesApp.getInstance().getMyListManager(context).removeEntry(entry);
                notifyDataSetChanged();
            }
        });
        plusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry.incrementEps();
                notifyDataSetChanged();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra(ResultsActivity.SELECTED, entry.getTitle().toLowerCase());
                context.startActivity(i);
            }
        });
        return  row;
    }

    private void setStatusText(ListEntry entry, TextView status, Resources res) {
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
    }

    private void setText(String text, TextView v){
        if(v != null){
            v.setText(text);
        }
    }
}

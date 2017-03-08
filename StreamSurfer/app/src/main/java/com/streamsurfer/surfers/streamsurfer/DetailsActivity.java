package com.streamsurfer.surfers.streamsurfer;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DetailsActivity extends BaseActivity {

    private EntriesApp entries = EntriesApp.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String selectedString = getIntent().getStringExtra(ResultsActivity.SELECTED);
        final Entry selected = entries.getEntries().get(selectedString);
        final List<Service> serviceList = selected.getServices();
        int serviceSize = serviceList.size();
        String[] services = new String[serviceSize];
        int[] images = new int[serviceSize];
        final String[] url = new String[serviceSize];
        for (int i = 0; i < serviceSize; i++) {
            services[i] = serviceList.get(i).getName();
            images[i] = R.mipmap.ic_launcher;
            url[i] = serviceList.get(i).getBaseUrl();
        }
        List<Episode> episodes = selected.getEpisodes();
        int episodeSize = episodes.size();
        String[] episodeTitles = new String[episodeSize];
        int[] seasonNumber = new int[episodeSize];
        int[] episodeNumber = new int[episodeSize];
        for (int i = 0; i < episodeSize; i++) {
            episodeTitles[i] = episodes.get(i).getTitle();
            seasonNumber[i] = episodes.get(i).getSeasonNumber();
            episodeNumber[i] = episodes.get(i).getEpisodeNumber();
        }

        TextView title = (TextView) findViewById(R.id.entry_title);
        TextView desc = (TextView) findViewById(R.id.entry_description);
        final ListView serviceView = (ListView) findViewById(R.id.services_list);
        ListView episodesView = (ListView) findViewById(R.id.episodes_list);

        final MyListManager manager = entries.getMyListManager(this);
        Button addToList = (Button)findViewById(R.id.button_add_to_list);
        if(manager.listContainsTitle(selected.getTitle())){
            addToList.setEnabled(false);
        } else {
            addToList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager.addEntryToList(new ListEntry(selected.getTitle(), ShowStatus.PLAN_TO_WATCH, 0, selected.getEpisodes().size(), 0, selected.getThumbnail()));
                }
            });
        }

        title.setText(selected.getTitle());
        desc.setText(selected.getSynopsis());

        serviceView.setAdapter(new ServicesAdapter(DetailsActivity.this, services, images));
        episodesView.setAdapter(new EpisodesAdapter(DetailsActivity.this, episodeNumber, seasonNumber, episodeTitles));

        serviceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String urlName = url[position];
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlName));
                startActivity(browserIntent);
            }
        });
    }
}

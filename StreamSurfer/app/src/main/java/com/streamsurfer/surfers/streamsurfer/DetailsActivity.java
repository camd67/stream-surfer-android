package com.streamsurfer.surfers.streamsurfer;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DetailsActivity extends BaseActivity {

    private EntriesApp entries = EntriesApp.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String selectedString = getIntent().getStringExtra(ResultsActivity.SELECTED);
        Entry selected = entries.getEntries().get(selectedString);
        List<Service> serviceList = selected.getServices();
        int serviceSize = serviceList.size();
        String[] services = new String[serviceSize];
        int[] images = new int[serviceSize];
        String[] url = new String[serviceSize];
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
        ListView serviceView = (ListView) findViewById(R.id.services_list);
        ListView episodesView = (ListView) findViewById(R.id.episodes_list);

        title.setText(selected.getTitle());
        desc.setText(selected.getSynopsis());
        serviceView.setAdapter(new ServicesAdapter(DetailsActivity.this, services, images));
        episodesView.setAdapter(new EpisodesAdapter(DetailsActivity.this, episodeNumber, seasonNumber, episodeTitles));
    }
}

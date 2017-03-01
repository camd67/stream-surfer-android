package com.streamsurfer.surfers.streamsurfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jack on 3/1/2017.
 */

public class Episode {
    private String title;
    private int episodeNumber;
    private int seasonNumber;
    private String releaseDate;
    private int length;
    private List<String> synonyms;
    private String thumbnail;

    public Episode(String title, int episodeNumber, int seasonNumber, String releaseDate,
                   int length, List<String> synonyms, String thumbnail) {
        this.title = title;
        this.episodeNumber = episodeNumber;
        this.seasonNumber = seasonNumber;
        this.releaseDate = releaseDate;
        this.length = length;
        this.synonyms = synonyms;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getLength() {
        return length;
    }

    public List<String> getSynonyms() {
        return new ArrayList<>(synonyms);
    }

    public String getThumbnail() {
        return thumbnail;
    }
}

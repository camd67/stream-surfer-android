package com.streamsurfer.surfers.streamsurfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 3/1/2017.
 */

public class Entry implements Serializable {
    private String title;
    private List<String> synonyms;
    private String synopsis;
    private String thumbnail;
    private List<String> genres;
    private List<String> tags;
    private List<Service> services;
    private List<Episode> episodes;

    public Entry(String title, List<String> synonyms, String synopsis, String thumbnail,
                 List<String> genres, List<String> tags, List<Service> services,
                 List<Episode> episodes) {
        this.title = title;
        this.synonyms = synonyms;
        this.synopsis = synopsis;
        this.thumbnail = thumbnail;
        this.genres = genres;
        this.tags = tags;
        this.services = services;
        this.episodes = episodes;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getSynonyms() {
        return new ArrayList<>(synonyms);
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<String> getGenres() {
        return new ArrayList<>(genres);
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    public List<Service> getServices() {
        return services;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }
}

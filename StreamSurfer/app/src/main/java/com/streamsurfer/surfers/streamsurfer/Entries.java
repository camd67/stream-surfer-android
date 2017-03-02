package com.streamsurfer.surfers.streamsurfer;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 3/1/2017.
 */

public class Entries extends android.app.Application {
    private Map<String, Entry> entryMap = new HashMap<>();
    private List<Entry> results;
    private Entry selected;
    private static Entries instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Entries getInstance() {
        if (instance == null) {
            instance = new Entries();
        }
        return instance;
    }

    public Map<String, Entry> getEntries() {
        if (entryMap.isEmpty()) {
            //switch to surfer instead of qd3
            createEntries(new File("/storage/emulated/0/Android/data/edu.washington.jackw117.quizdroid3/files/data.json"));
        }
        return new HashMap<>(entryMap);
    }

    public List<Entry> getResults() {
        return new ArrayList<>(results);
    }

    public void setResults(List<Entry> results) {
        this.results = new ArrayList<>();
        for (Entry e : results) {
            this.results.add(e);
        }
    }

    public Entry getSelected() {
        return new Entry(selected.getTitle(), selected.getSynonyms(), selected.getSynopsis(),
                selected.getThumbnail(), selected.getGenres(), selected.getTags(), selected.getServices(),
                selected.getEpisodes());
    }

    public void setSelected(Entry e) {
        selected = new Entry(e.getTitle(), e.getSynonyms(), e.getSynopsis(), e.getThumbnail(), e.getGenres(),
                e.getTags(), e.getServices(), e.getEpisodes());
    }

    private void createEntries(File file) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                reader.skipValue();
                String title = reader.nextString();
                reader.skipValue();
                List<String> synonyms = new ArrayList<>();
                reader.beginArray();
                while (reader.peek() != JsonToken.END_ARRAY) {
                    synonyms.add(reader.nextString());
                }
                reader.endArray();
                reader.skipValue();
                String synopsis = reader.nextString();
                reader.skipValue();
                String thumbnail = reader.nextString();
                reader.skipValue();
                List<String> genres = new ArrayList<String>();
                reader.beginArray();
                while (reader.peek() != JsonToken.END_ARRAY) {
                    genres.add(reader.nextString());
                }
                reader.endArray();
                reader.skipValue();
                List<String> tags = new ArrayList<String>();
                reader.beginArray();
                while (reader.peek() != JsonToken.END_ARRAY) {
                    tags.add(reader.nextString());
                }
                reader.endArray();
                reader.skipValue();
                reader.beginArray();
                List<Service> services = new ArrayList<Service>();
                while (reader.peek() != JsonToken.END_ARRAY) {
                    reader.beginObject();
                    reader.skipValue();
                    String name = reader.nextString();
                    reader.skipValue();
                    String baseUrl = reader.nextString();
                    reader.skipValue();
                    String icon = reader.nextString();
                    reader.endObject();
                    services.add(new Service(name, baseUrl, icon));
                }
                reader.endArray();
                reader.skipValue();
                List<Episode> episodes = new ArrayList<Episode>();
                reader.beginArray();
                while (reader.peek() != JsonToken.END_ARRAY) {
                    reader.beginObject();
                    reader.skipValue();
                    String eTitle = reader.nextString();
                    reader.skipValue();
                    int eNumber = reader.nextInt();
                    reader.skipValue();
                    int sNumber = reader.nextInt();
                    reader.skipValue();
                    String release = reader.nextString();
                    reader.skipValue();
                    int length = reader.nextInt();
                    reader.skipValue();
                    reader.beginArray();
                    List<String> eSynonyms = new ArrayList<String>();
                    while (reader.peek() != JsonToken.END_ARRAY) {
                        eSynonyms.add(reader.nextString());
                    }
                    reader.endArray();
                    reader.skipValue();
                    String eThumbnail = reader.nextString();
                    reader.endObject();
                    episodes.add(new Episode(eTitle, eNumber, sNumber, release, length, eSynonyms, eThumbnail));
                }
                reader.endArray();
                reader.endObject();
                entryMap.put(title.toLowerCase(), new Entry(title, synonyms, synopsis, thumbnail, genres, tags, services, episodes));
            }
            reader.endArray();
        } catch (IOException e) {
            Log.e("JSON", e.toString());
        }
    }
}

package com.streamsurfer.surfers.streamsurfer;

import android.os.Environment;
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
    private Map<String, List<Entry>> genreMap = new HashMap<>();
    private List<String> genreList = new ArrayList<>();
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
            File directory = Environment.getExternalStorageDirectory();
            createEntries(new File(directory + "/data.json"));
        }
        return new HashMap<>(entryMap);
    }

    public String[] getGenreList() {
        return genreList.toArray(new String[genreList.size()]);
    }

    public Map<String, List<Entry>> getGenreMap() {
        if (genreMap.isEmpty()) {
            File directory = Environment.getExternalStorageDirectory();
            createEntries(new File(directory + "/data.json"));
        }
        return new HashMap<>(genreMap);
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
                    String genre = reader.nextString();
                    genres.add(genre);
                    if (!genreList.contains(genre)) {
                        genreList.add(genre);
                    }
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
                for (String genre : genres) {
                    Entry e = new Entry(title, synonyms, synopsis, thumbnail, genres, tags, services, episodes);
                    if (genreMap.get(genre) == null) {
                        List<Entry> newList = new ArrayList<>();
                        newList.add(e);
                        genreMap.put(genre, newList);
                    } else {
                        List<Entry> list = genreMap.get(genre);
                        list.add(e);
                        genreMap.put(genre, list);
                    }
                }
            }
            reader.endArray();
        } catch (IOException e) {
            Log.e("JSON", e.toString());
        }
    }
}

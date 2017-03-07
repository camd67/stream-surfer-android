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


public class Entries extends android.app.Application {
    private Map<String, Entry> entryMap = new HashMap<>();
    private Map<String, List<Entry>> genreMap = new HashMap<>();
    private Map<String, List<Entry>> serviceMap = new HashMap<>();
    private List<String> genreList = new ArrayList<>();
    private List<String> serviceList = new ArrayList<>();
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

    public String[] getServiceList() {
        return serviceList.toArray(new String[serviceList.size()]);
    }

    public Map<String, List<Entry>> getGenreMap() {
        if (genreMap.isEmpty()) {
            File directory = Environment.getExternalStorageDirectory();
            createEntries(new File(directory + "/data.json"));
        }
        return new HashMap<>(genreMap);
    }

    public Map<String, List<Entry>> getServiceMap() {
        if (serviceMap.isEmpty()) {
            File directory = Environment.getExternalStorageDirectory();
            createEntries(new File(directory + "/data.json"));
        }
        return new HashMap<>(serviceMap);
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
                    if (!genreList.contains(genre.toUpperCase())) {
                        genreList.add(genre.toUpperCase());
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
                    if (!serviceList.contains(name.toUpperCase())) {
                        serviceList.add(name.toUpperCase());
                    }
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
                Entry e = new Entry(title, synonyms, synopsis, thumbnail, genres, tags, services, episodes);
                entryMap.put(title.toLowerCase(), e);
                for (String genre : genres) {
                    addToMap(genreMap, e, genre.toUpperCase());
                }
                for (Service service : services) {
                    addToMap(serviceMap, e, service.getName().toUpperCase());
                }
            }
            reader.endArray();
        } catch (IOException e) {
            Log.e("JSON", e.toString());
        }
    }

    private void addToMap(Map<String, List<Entry>> map, Entry e, String check) {
        if (map.get(check) == null) {
            List<Entry> newList = new ArrayList<>();
            newList.add(e);
            map.put(check, newList);
        } else {
            List<Entry> list = map.get(check);
            list.add(e);
            map.put(check, list);
        }
    }
}

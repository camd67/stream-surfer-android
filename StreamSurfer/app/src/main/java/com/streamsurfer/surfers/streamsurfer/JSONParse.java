package com.streamsurfer.surfers.streamsurfer;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jack on 3/1/2017.
 */

public class JSONParse {
    private List<Entry> entries = new ArrayList<>();

    public List<Entry> getEntries() {
        return new ArrayList<>(entries);
    }

    public void createEntries(File file) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                reader.skipValue();
                String title = reader.nextString();
                reader.skipValue();
                List<String> synonyms = new ArrayList<String>();
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
                entries.add(new Entry(title, synonyms, synopsis, thumbnail, genres, tags, services, episodes));
            }
            reader.endArray();
        } catch (IOException e) {
            Log.e("JSON", e.toString());
        }
    }
}

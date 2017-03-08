package com.streamsurfer.surfers.streamsurfer;

import android.app.DownloadManager;
import android.os.Environment;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;


public class EntriesApp extends android.app.Application {
    private Map<String, Entry> entryMap = new HashMap<>();
    private Map<String, List<Entry>> genreMap = new HashMap<>();
    private Map<String, List<Entry>> serviceMap = new HashMap<>();
    private List<String> genreList = new ArrayList<>();
    private List<String> serviceList = new ArrayList<>();
    private Map<Date, Entry> updated = new HashMap<>();
    private static EntriesApp instance = null;
    private static final String TAG = "EntriesApp";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static EntriesApp getInstance() {
        if (instance == null) {
            instance = new EntriesApp();
        }
        return instance;
    }

    public Map<String, Entry> getEntries() {
        if (entryMap.isEmpty()) {
            createEntries();
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
            createEntries();
        }
        return new HashMap<>(genreMap);
    }

    public Map<String, List<Entry>> getServiceMap() {
        if (serviceMap.isEmpty()) {
            createEntries();
        }
        return new HashMap<>(serviceMap);
    }

    public Map<Date, Entry> getUpdated() {
        if (updated.isEmpty()) {
            createEntries();
        }
        return new HashMap<>(updated);
    }

    public List<Date> getSortedList() {
        return asSortedList(updated.keySet());
    }

    private static
    <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        List<T> shallowCopy = list.subList(0, list.size());
        Collections.reverse(shallowCopy);
        return shallowCopy;
    }

    private void createEntries() {
        try {
            File f = new File(Environment.getExternalStorageDirectory().getPath() + "/sampleData.json");
            Log.i(TAG, "Creating entries from " + f.getAbsolutePath());
            JsonReader reader = new JsonReader(new FileReader(f));
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
                String key = reader.nextName();
                String synopsis = "";
                if (key.equals("synopsis")) {
                    synopsis = reader.nextString();
                    reader.skipValue();
                }
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
                Date mostRecent = null;
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
                    String year = release.substring(0, 4);
                    String month = release.substring(5, 7);
                    String day = release.substring(8);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    Date strDate = sdf.parse(release);
                    if (mostRecent == null) {
                        mostRecent = strDate;
                    } else if (mostRecent.before(strDate)) {
                            mostRecent = strDate;
                    }
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
                updated.put(mostRecent, e);
            }
            reader.endArray();
        } catch (IOException e) {
            Log.e("JSON", e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
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

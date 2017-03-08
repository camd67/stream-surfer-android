package com.streamsurfer.surfers.streamsurfer;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MainActivity extends BaseActivity {

    public static final String RESULTS = "results";
    public static final String OPTION = "option";
    private static DownloadManager dm = null;
    private Map<String, Entry> entryList;
    private EntriesApp entries = EntriesApp.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DownloadTask downloadTask = new DownloadTask(MainActivity.this);
        downloadTask.execute("http://moebot.net/sampleData.json");
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            File temp = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // download the file
                input = connection.getInputStream();
                temp = new File(context.getFilesDir(), "temp.json");
                output = new FileOutputStream(temp);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                File old = new File(context.getFilesDir(), "sampleData.json");
                old.delete();
                temp.renameTo(old);
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            final TextView searchInput = (TextView) findViewById(R.id.search_input);
            Button searchButton = (Button) findViewById(R.id.search_button);
            Button advanced = (Button) findViewById(R.id.advanced_search_button);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String search = searchInput.getText().toString().toLowerCase();
                    Intent activity = new Intent(MainActivity.this, ResultsActivity.class);
                    activity.putExtra(RESULTS, search);

                    Intent test = new Intent(MainActivity.this, BrowseActivity.class);
                    test.putExtra(OPTION, "services");
                    //startActivity(test);

                    Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
                    //startActivity(settings);
                    startActivity(activity);
                }
            });

            searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        String search = searchInput.getText().toString().toLowerCase();
                        Intent activity = new Intent(MainActivity.this, ResultsActivity.class);
                        activity.putExtra(RESULTS, search);
                        startActivity(activity);
                        return true;
                    }
                    return false;
                }
            });

            advanced.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent advanced = new Intent(MainActivity.this, AdvancedSearch.class);
                    startActivity(advanced);
                }
            });
        }
    }
}

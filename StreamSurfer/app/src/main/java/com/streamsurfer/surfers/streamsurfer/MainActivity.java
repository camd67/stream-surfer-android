package com.streamsurfer.surfers.streamsurfer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        File json = new File("/storage/emulated/0/Android/data/edu.washington.jackw117.quizdroid3/files/data.json");
        JSONParse parser = new JSONParse();
        parser.createEntries(json);
        List<Entry> entryList = parser.getEntries();

        TextView searchInput = (TextView) findViewById(R.id.search_input);
        String search = searchInput.getText().toString();

    }
}

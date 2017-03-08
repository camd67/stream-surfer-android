package com.streamsurfer.surfers.streamsurfer;

import android.os.Bundle;
import android.widget.ListView;

public class MyListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        ListEntry[] entries = {};
        ListView myList = (ListView)findViewById(R.id.my_list);
        MyListAdapter adapter = new MyListAdapter(this, entries);
        myList.setAdapter(adapter);
    }
}

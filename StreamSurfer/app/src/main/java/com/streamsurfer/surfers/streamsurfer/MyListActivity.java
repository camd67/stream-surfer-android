package com.streamsurfer.surfers.streamsurfer;

import android.os.Bundle;
import android.widget.ListView;

public class MyListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        MyListManager myListManager = EntriesApp.getInstance().getMyListManager(this);
        ListView myList = (ListView)findViewById(R.id.my_list);
        MyListAdapter adapter = new MyListAdapter(this, myListManager.getAllEntries());
        myList.setAdapter(adapter);
    }
}

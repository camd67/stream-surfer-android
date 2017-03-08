package com.streamsurfer.surfers.streamsurfer;

import android.os.Bundle;
import android.widget.ListView;

public class MyListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        ListEntry[] entries = new ListEntry[]{
                new ListEntry("show 1", ShowStatus.DROPPED, 1, 10, 1, "test.png"),
                new ListEntry("show 2", ShowStatus.COMPLETE, 10, 10, 2, "test.png"),
                new ListEntry("show 3", ShowStatus.PLAN_TO_WATCH, 0, 5, 3, "test.png"),
                new ListEntry("show 4", ShowStatus.WATCHING, 5, 15, 4, "test.png"),
                new ListEntry("show 5", ShowStatus.WATCHING, 5, 15, 5, "test.png"),
                new ListEntry("show 6", ShowStatus.WATCHING, 5, 15, 7, "test.png")
        };
        ListView myList = (ListView)findViewById(R.id.my_list);
        MyListAdapter adapter = new MyListAdapter(this, entries);
        myList.setAdapter(adapter);
    }
}

package com.streamsurfer.surfers.streamsurfer;

import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.ListView;

public class MyListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1);

        MyListManager myListManager = EntriesApp.getInstance().getMyListManager(this);
        ListView myList = (ListView)findViewById(R.id.my_list);
        MyListAdapter adapter = new MyListAdapter(this, myListManager.getAllEntries());
        myList.setAdapter(adapter);
    }
}

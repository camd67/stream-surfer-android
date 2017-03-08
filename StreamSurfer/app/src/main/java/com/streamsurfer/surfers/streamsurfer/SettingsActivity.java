package com.streamsurfer.surfers.streamsurfer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox notify = (CheckBox) findViewById(R.id.notify_check_box);
        Button demo = (Button) findViewById(R.id.demo_notification);

        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emptyIntent = new Intent();
                PendingIntent pendingIntent = PendingIntent.getActivity(SettingsActivity.this, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(SettingsActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Demo Notification")
                        .setContentText("A show has been updated")
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());
            }
        });
    }
}

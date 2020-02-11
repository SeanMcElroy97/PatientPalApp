package com.example.patientpal.timePicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper nHelper = new NotificationHelper(context);
        String textInAlert = (String) intent.getSerializableExtra("message");
        int requestCode = (Integer) intent.getSerializableExtra("reqCode");
        NotificationCompat.Builder nb = nHelper.getChannel1Notification(textInAlert);
        nHelper.getNotificationManager().notify(requestCode,nb.build());
    }
}

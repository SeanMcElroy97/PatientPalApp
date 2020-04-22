package com.example.patientpal.medicineReminders.timePicker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.patientpal.R;

public class NotificationHelper extends ContextWrapper {

    //Notification Manager. Responsible for building channel
    private NotificationManager nm;

    public static final String channelID = "channelID";
    public static final String channelName = "Channel name";

    //Constructor
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel channel1 = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.DarkGreen);

        getNotificationManager().createNotificationChannel(channel1);
    }

    public NotificationManager getNotificationManager(){
        if (nm == null){
            nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return nm;
    }

    public NotificationCompat.Builder getChannel1Notification(String message){
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("title")
                .setContentText(message)
                .setSmallIcon(R.drawable.patient_pal_icon);
    }
}

package com.karthik.todo.Services;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.karthik.todo.R;
import com.karthik.todo.Screens.Todo.TodoActivity;

/**
 * Created by karthikrk on 02/09/17.
 */

public class BackgroundJobService extends JobService {
    private final String ChannelId = "TodoApp";
    private final String ChannelName = "TodoReminder";
    public static final String notificationId = "notificationId";
    public static final String notificationTitle = "notificationtitle";
    public static final String notificationMessage = "notificationmessage";

    @Override
    public boolean onStartJob(JobParameters job) {
        Bundle bundle = job.getExtras();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(ChannelId,ChannelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,ChannelId)
                .setSmallIcon(R.mipmap.launcher)
                .setContentIntent(PendingIntent.getActivity(this,0,
                                new Intent(this,TodoActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle(bundle.getString(notificationTitle))
                .setContentText(bundle.getString(notificationMessage))
                .setAutoCancel(false);
        notificationManager.notify(bundle.getInt(notificationId), builder.build());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}

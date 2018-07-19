package com.example.maguilar.shptes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

/**
 * Created by maguilar on 16/07/2018.
 */

public class NotificationHandler extends ContextWrapper {

    public NotificationManager notificationManager;
    public static final String CHANNEL_HIGH_ID = "1";
    public static final String CHANNEL_LOW_ID = "2";
    public static final String CHANNEL_LOW_NAME = "LOW_CHANNEL";
    public static final String CHANNEL_HIGH_NAME = "HIGH_CHANNEL";


    public NotificationHandler(Context base) {
        super(base);

        createChannel();
    }

    public NotificationManager getNotificationManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public void createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel highchannel = new NotificationChannel(CHANNEL_HIGH_ID,CHANNEL_HIGH_NAME,NotificationManager.IMPORTANCE_HIGH);
            highchannel.setShowBadge(true);

            NotificationChannel lowchannel = new NotificationChannel(CHANNEL_LOW_ID,CHANNEL_LOW_NAME,NotificationManager.IMPORTANCE_LOW);
            lowchannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getNotificationManager().createNotificationChannel(highchannel);
            getNotificationManager().createNotificationChannel(lowchannel);
        }
    }

    public Notification.Builder createNotification(String title,String message, boolean isHighImportance){
        if(Build.VERSION.SDK_INT >= 26){
            if (isHighImportance){
                return createNotificationWithChannel(title,message,CHANNEL_HIGH_NAME);
            }
            return createNotificationWithChannel(title,message,CHANNEL_LOW_NAME);
        }
        return createNotificationWithOutChannel(title,message);
    }

    public Notification.Builder createNotificationWithChannel(String title,String message, String channel){
        if(Build.VERSION.SDK_INT >= 26) {
            Intent intent = new Intent(this, ShopCarActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("message", message);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            return new Notification.Builder(getApplicationContext(), channel)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setColor(getColor(R.color.colorPrimary))
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setAutoCancel(true);
        }
        return null;
    }

    public Notification.Builder createNotificationWithOutChannel(String title,String message){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("message", message);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            return new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setAutoCancel(true);
    }
}

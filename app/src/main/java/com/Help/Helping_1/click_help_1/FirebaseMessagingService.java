package com.Help.Helping_1.click_help_1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by tashf on 10/6/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private FirebaseAuth mauth;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String notification_title=remoteMessage.getNotification().getTitle();
        String notification_message=remoteMessage.getNotification().getBody();


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(notification_title)
                        .setContentText(notification_message);
        mBuilder.setWhen(System.currentTimeMillis());

        if(mauth.getInstance().getCurrentUser()==null) {
            Intent resultIntent = new Intent(this,MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultpendingintent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultpendingintent);
        }
        else{
            Intent resultIntent = new Intent(this,Navigation.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(Navigation.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultpendingintent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultpendingintent);
        }

        int mNotificationId = (int) System.currentTimeMillis();

// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(mNotificationId, mBuilder.build());

// Builds the notification and issues it.

    }
}


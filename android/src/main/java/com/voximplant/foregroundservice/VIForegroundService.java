/*
 * Copyright (c) 2011-2019, Zingaya, Inc. All rights reserved.
 */

package com.voximplant.foregroundservice;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import static com.voximplant.foregroundservice.Constants.NOTIFICATION_CONFIG;
public class VIForegroundService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            if (action.equals(Constants.ACTION_FOREGROUND_SERVICE_START)) {
                if (intent.getExtras() != null && intent.getExtras().containsKey(NOTIFICATION_CONFIG)) {
                    Bundle notificationConfig = intent.getExtras().getBundle(NOTIFICATION_CONFIG);
                    if (notificationConfig != null && notificationConfig.containsKey("id")) {
                        Notification notification = NotificationHelper.getInstance(getApplicationContext())
                                .buildNotification(getApplicationContext(), notificationConfig);
                        Log.d("VIForegroundService", "FOREGROUND SERVICE: starting notification");
                        startForeground((int)notificationConfig.getDouble("id"),
                                notification);
                        Log.d("VIForegroundService", "FOREGROUND SERVICE: notification started");
                    } else {
                        Log.w("VIForegroundService", "FOREGROUND SERVICE: NO NOTIFICATION ID PROVIDED");
                    }
                } else {
                    Log.w("VIForegroundService", "FOREGROUND SERVICE: NO NOTIFICATION CONFIGURATION PROVIDED");
                }
            } else if (action.equals(Constants.ACTION_FOREGROUND_SERVICE_STOP)) {
                stopSelf();
            }
        } else {
            Log.w("VIForegroundService", "FOREGROUND SERVICE: Action is null");
        }
        return START_NOT_STICKY;

    }


}
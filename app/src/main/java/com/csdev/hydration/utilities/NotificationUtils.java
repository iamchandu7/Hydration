package com.csdev.hydration.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.csdev.hydration.MainActivity;
import com.csdev.hydration.R;
import com.csdev.hydration.sync.ReminderTasks;
import com.csdev.hydration.sync.WaterReminderIntentService;

public class NotificationUtils {

    private static final int WATER_REMINDER_NOTIFICATION_ID = 1138;
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 3417;
    private static final String WATER_REMAINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;
    private static final int ACTION_DRINK_PENDING_INTENT_ID = 1;

    public static void clearAllNotifications(Context context)
    {
        NotificationManager manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    public static void remindUserBecauseCharging(Context context) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel notificationChannel = new NotificationChannel(
                    WATER_REMAINDER_NOTIFICATION_CHANNEL_ID,
                    "Drink Water",
                    NotificationManager.IMPORTANCE_HIGH
                );

        notificationManager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context,
                WATER_REMAINDER_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_drink_black_24px)
                .setLargeIcon(LargeIcon(context))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true)
                .addAction(ignoreNotificationAction(context))
                .addAction(drinkWaterAction(context));

        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, builder.build());

    }
    private static NotificationCompat.Action ignoreNotificationAction(Context context)
    {
        Intent ignoreIntent = new Intent(context, WaterReminderIntentService.class);
        ignoreIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignorePendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_cancel_black_24px,
                                "No, thanks",
                                ignorePendingIntent );
        return action;
    }

    private static NotificationCompat.Action drinkWaterAction(Context context)
    {
        Intent incrementIntent = new Intent(context, WaterReminderIntentService.class);
        incrementIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);

        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                incrementIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_local_drink_black_24px,
                "I, did it",
                pendingIntent);
        return action;
    }

    public static PendingIntent contentIntent(Context context)
    {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context,
                        WATER_REMINDER_PENDING_INTENT_ID
                        ,startActivityIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static Bitmap LargeIcon(Context context)
    {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
    }
}

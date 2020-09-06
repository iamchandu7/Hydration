package com.csdev.hydration.sync;

import android.content.Context;

import com.csdev.hydration.utilities.NotificationUtils;
import com.csdev.hydration.utilities.PreferenceUtilities;

public class ReminderTasks{

    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String ACTION_CHARGING_REMAINDER = "action-charging-remainder";

    public static void executeTask(Context context,String action)
    {
        if(ACTION_INCREMENT_WATER_COUNT.equals(action))
        {
            incrementWaterCount(context);
        }
        else if(ACTION_DISMISS_NOTIFICATION.equals(action))
        {
            NotificationUtils.clearAllNotifications(context);
        }
        else if(ACTION_CHARGING_REMAINDER.equals(action))
        {
            issueChargingRemainder(context);
        }
    }

    private static void issueChargingRemainder(Context context)
    {
        PreferenceUtilities.getChargingReminderCount(context);
        NotificationUtils.remindUserBecauseCharging(context);
    }

    private static void incrementWaterCount(Context context)
    {
        PreferenceUtilities.incrementWaterCount(context);
        NotificationUtils.clearAllNotifications(context);
    }

}
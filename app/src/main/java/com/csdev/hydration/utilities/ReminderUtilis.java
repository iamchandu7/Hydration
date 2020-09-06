package com.csdev.hydration.utilities;

import android.content.Context;

import com.csdev.hydration.sync.WaterRemainderFirebaseJobSchedular;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderUtilis {

    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int)
            (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));

    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "reminder_job_tag";
    private static boolean mInitialize;


    synchronized public static void scheduleChargingReminder(Context context)
    {
        if(mInitialize) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);

        Job job = jobDispatcher.newJobBuilder()
                .setService(WaterRemainderFirebaseJobSchedular.class)
                .setTag(REMINDER_JOB_TAG)
                .setConstraints(Constraint.DEVICE_CHARGING)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        jobDispatcher.schedule(job);
        mInitialize = true;

    }


}

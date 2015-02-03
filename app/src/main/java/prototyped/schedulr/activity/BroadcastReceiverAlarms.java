package prototyped.schedulr.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import prototyped.schedulr.R;
import prototyped.schedulr.database.Profile;
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class BroadcastReceiverAlarms extends BroadcastReceiver
{
    private Context context;
    private ProfileDBDataSource profileDBDataSource;
    private ScheduleDBDataSource scheduleDBDataSource;
    private Profile profile;
    private long scheduleId;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        profileDBDataSource = new ProfileDBDataSource(context);
        profileDBDataSource.open();
        scheduleDBDataSource = new ScheduleDBDataSource(context);
        scheduleDBDataSource.open();

        if(intent.getExtras().getBoolean("is_service_intent"))
        {
            context.startService(new Intent(context, ServiceProfileScheduler.class));
        }
        else if(intent.getExtras().getBoolean("is_schedule"))
        {
            if(!PreferenceManager.getDefaultSharedPreferences(context).getBoolean("event_ongoing", false))
            {
                profile = profileDBDataSource.getProfile(intent.getExtras().getString("profile_name"));

                setDisplayParameters();
                setAudioParameters();
                setWifi();
                setMobileData();
                setNotification(profile);
            }
        }
        else
        {
            if((intent.getExtras().getLong("event_id") == PreferenceManager.getDefaultSharedPreferences(context).getLong("event_ongoing_id", 0)))
            {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("event_ongoing", false).apply();
                PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("event_ongoing_id", 0).apply();

                scheduleId = scheduleDBDataSource.getCurrentSchedule();
                if(scheduleId == -1)
                {
                    profile = profileDBDataSource.getProfile("Default");
                }
                else
                {
                    profile = profileDBDataSource.getProfile(scheduleDBDataSource.getSchedule(scheduleId).PROFILE_NAME);
                }

                setDisplayParameters();
                setAudioParameters();
                setWifi();
                setMobileData();
                setNotification(profile);
            }
            else
            {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("event_ongoing", true).apply();
                PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("event_ongoing_id", intent.getExtras().getLong("event_id")).apply();
                profile = profileDBDataSource.getProfile("Default");

                setDisplayParameters();
                setAudioParameters();
                setWifi();
                setMobileData();
                setNotification(profile);
            }
        }

        profileDBDataSource.close();
        scheduleDBDataSource.close();
    }

    private void setNotification(Profile profile)
    {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Schedulr")
                .setContentText(profile.PROFILE_NAME)
                .setAutoCancel(false);
        Notification notification = notificationBuilder.build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        notificationManager.notify(001, notification);
    }

    private void setDisplayParameters()
    {
        if(profile.DISPLAY_BRIGHTNESS_AUTO_STATE)
        {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        }
        else
        {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, profile.DISPLAY_BRIGHTNESS_LEVEL);
        }

        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, profile.DISPLAY_SLEEP_TIMEOUT);
    }

    private void setAudioParameters()
    {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        audioManager.setStreamVolume(AudioManager.STREAM_RING, (audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)*profile.SOUND_VOLUME_RINGTONE)/10, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, (audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)*profile.SOUND_VOLUME_RINGTONE)/10, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)*profile.SOUND_VOLUME_APPLICATION)/10, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, (audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)*profile.SOUND_VOLUME_APPLICATION)/10, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, (audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)*profile.SOUND_VOLUME_ALARM)/10, 0);

        RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, Uri.parse(profile.SOUND_RINGTONE));
        RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION, Uri.parse(profile.SOUND_NOTIFICATION_TONE));

        audioManager.setRingerMode(profile.SOUND_RING_MODE);
    }

    private void setWifi()
    {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(profile.WIFI_STATE);
    }

    private void setMobileData()
    {
        ConnectivityManager connectivityManager  = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try
        {
            Method dataMethod = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
            dataMethod.setAccessible(true);
            dataMethod.invoke(connectivityManager, profile.MOBILE_DATA_STATE);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}

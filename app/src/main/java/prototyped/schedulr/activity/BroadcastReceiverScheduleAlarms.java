package prototyped.schedulr.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import prototyped.schedulr.R;
import prototyped.schedulr.database.Profile;
import prototyped.schedulr.database.ProfileDBDataSource;

public class BroadcastReceiverScheduleAlarms extends BroadcastReceiver
{
    private Context context;
    private ProfileDBDataSource profileDBDataSource;
    private Profile profile;
    private Intent intent;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        this.intent = intent;
        profileDBDataSource = new ProfileDBDataSource(context);
        profileDBDataSource.open();
        profile = profileDBDataSource.getProfile(intent.getExtras().getString("profile_name"));

        setDisplayParameters();
        setAudioParameters();
        setWifi();
        setMobileData();
        setNotification();

        profileDBDataSource.close();


    }

    private void setNotification()
    {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Schedulr")
                .setContentText(intent.getExtras().getString("profile_name"))
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

//        RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, Uri.parse(profile.SOUND_RINGTONE));
//        RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION, Uri.parse(profile.SOUND_NOTIFICATION_TONE));

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

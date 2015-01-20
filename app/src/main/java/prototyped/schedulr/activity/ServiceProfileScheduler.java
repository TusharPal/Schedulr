package prototyped.schedulr.activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.database.Event;
import prototyped.schedulr.database.EventDBDataSource;
import prototyped.schedulr.database.Profile;
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.Schedule;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class ServiceProfileScheduler extends Service
{
    private ScheduleDBDataSource scheduleDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
    private EventDBDataSource eventDBDataSource;
    private AlarmManager alarmManager;
    private NotificationManager notificationManager;
    private Calendar calendar;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        scheduleDBDataSource = new ScheduleDBDataSource(this);
        scheduleDBDataSource.open();
        profileDBDataSource = new ProfileDBDataSource(this);
        profileDBDataSource.open();
        eventDBDataSource = new EventDBDataSource(this);
        eventDBDataSource.open();
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        cancelAlarms();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onDestroy()
    {
        scheduleDBDataSource.close();
        profileDBDataSource.close();
        eventDBDataSource.close();
    }

    private void setServiceAlarm()
    {
        Intent intent = new Intent(this, BroadcastReceiverServiceAlarms.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 00100, intent, 0);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        else
        {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        setScheduleAlarms();
    }

    private void setScheduleAlarms()
    {
        calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY? 6: calendar.get(Calendar.DAY_OF_WEEK)-2;
        int timeStampCurrent = (calendar.get(Calendar.HOUR_OF_DAY)*60)+calendar.get(Calendar.MINUTE);
        int timeStampStart;
        int timeStampEnd;
        List<Schedule> list = scheduleDBDataSource.getScheduleList(dayOfWeek);

        if(list.size() != 0)
        {
            for(Schedule schedule : list)
            {
                timeStampStart = (schedule.START_HOUR*60)+schedule.START_MINUTE;
                timeStampEnd = (schedule.END_HOUR*60)+schedule.END_MINUTE;

                if(timeStampStart <= timeStampCurrent && timeStampCurrent <= timeStampEnd &&  !PreferenceManager.getDefaultSharedPreferences(this).getBoolean("event_ongoing", false))
                {
                    Profile profile = profileDBDataSource.getProfile(schedule.PROFILE_NAME);
                    setDisplayParameters(profile);
                    setAudioParameters(profile);
                    setWifi(profile);
                    setMobileData(profile);
                    setNotification(schedule.PROFILE_NAME);

                    Intent intentEndTime = new Intent(getBaseContext(), BroadcastReceiverScheduleAlarms.class);
                    intentEndTime.putExtra("profile_name", "Default");
                    intentEndTime.putExtra("is_schedule", true);
                    PendingIntent pendingIntentEndTime = PendingIntent.getBroadcast(getBaseContext(), timeStampEnd, intentEndTime, PendingIntent.FLAG_ONE_SHOT);
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, schedule.END_HOUR);
                    calendar.set(Calendar.MINUTE, schedule.END_MINUTE);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentEndTime);
                    }
                    else
                    {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentEndTime);
                    }
                }
                else if(timeStampCurrent < timeStampStart)
                {
                    Intent intentStartTime = new Intent(getBaseContext(), BroadcastReceiverScheduleAlarms.class);
                    intentStartTime.putExtra("profile_name", schedule.PROFILE_NAME);
                    intentStartTime.putExtra("is_schedule", true);
                    Intent intentEndTime = new Intent(getBaseContext(), BroadcastReceiverScheduleAlarms.class);
                    intentEndTime.putExtra("profile_name", "Default");
                    intentEndTime.putExtra("is_schedule", true);
                    PendingIntent pendingIntentStartTime = PendingIntent.getBroadcast(getBaseContext(), timeStampStart, intentStartTime, PendingIntent.FLAG_ONE_SHOT);
                    PendingIntent pendingIntentEndTime = PendingIntent.getBroadcast(getBaseContext(), timeStampEnd, intentEndTime, PendingIntent.FLAG_ONE_SHOT);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, schedule.START_HOUR);
                        calendar.set(Calendar.MINUTE, schedule.START_MINUTE);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentStartTime);

                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, schedule.END_HOUR);
                        calendar.set(Calendar.MINUTE, schedule.END_MINUTE);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentEndTime);
                    }
                    else
                    {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, schedule.START_HOUR);
                        calendar.set(Calendar.MINUTE, schedule.START_MINUTE);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentStartTime);

                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, schedule.END_HOUR);
                        calendar.set(Calendar.MINUTE, schedule.END_MINUTE);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentEndTime);
                    }
                }
                else
                {

                }
            }
        }

        setEventAlarms();
    }

    private void setEventAlarms()
    {
        calendar = Calendar.getInstance();
        int timeStampCurrent = (calendar.get(Calendar.HOUR_OF_DAY)*60)+calendar.get(Calendar.MINUTE);
        int timeStampStart;
        int timeStampEnd;
        List<Event> list = eventDBDataSource.getEventList();

        if(list.size() != 0)
        {
            for(Event event : list)
            {
                timeStampStart = (event.EVENT_START_HOUR*60)+event.EVENT_START_MINUTE;
                timeStampEnd = (event.EVENT_END_HOUR*60)+event.EVENT_END_MINUTE;

                if(timeStampStart <= timeStampCurrent && timeStampCurrent <= timeStampEnd && !PreferenceManager.getDefaultSharedPreferences(this).getBoolean("event_ongoing", false))
                {
                    Profile profile = profileDBDataSource.getProfile(event.EVENT_PROFILE_NAME);
                    setDisplayParameters(profile);
                    setAudioParameters(profile);
                    setWifi(profile);
                    setMobileData(profile);
                    setNotification(event.EVENT_PROFILE_NAME);

                    Intent intentEndTime = new Intent(getBaseContext(), BroadcastReceiverScheduleAlarms.class);
                    intentEndTime.putExtra("profile_name", "Default");
                    intentEndTime.putExtra("is_schedule", false);
                    PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("event_ongoing", true).apply();
                    PendingIntent pendingIntentEndTime = PendingIntent.getBroadcast(getBaseContext(), timeStampEnd*10, intentEndTime, PendingIntent.FLAG_ONE_SHOT);
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, event.EVENT_END_HOUR);
                    calendar.set(Calendar.MINUTE, event.EVENT_END_MINUTE);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentEndTime);
                    }
                    else
                    {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentEndTime);
                    }
                }
                else if(timeStampCurrent < timeStampStart)
                {
                    Intent intentStartTime = new Intent(getBaseContext(), BroadcastReceiverScheduleAlarms.class);
                    intentStartTime.putExtra("profile_name", event.EVENT_PROFILE_NAME);
                    intentStartTime.putExtra("is_schedule", false);
                    Intent intentEndTime = new Intent(getBaseContext(), BroadcastReceiverScheduleAlarms.class);
                    intentEndTime.putExtra("profile_name", "Default");
                    intentEndTime.putExtra("is_schedule", false);
                    PendingIntent pendingIntentStartTime = PendingIntent.getBroadcast(getBaseContext(), timeStampStart*10, intentStartTime, PendingIntent.FLAG_ONE_SHOT);
                    PendingIntent pendingIntentEndTime = PendingIntent.getBroadcast(getBaseContext(), timeStampEnd*10, intentEndTime, PendingIntent.FLAG_ONE_SHOT);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, event.EVENT_START_HOUR);
                        calendar.set(Calendar.MINUTE, event.EVENT_START_MINUTE);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentStartTime);

                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, event.EVENT_END_HOUR);
                        calendar.set(Calendar.MINUTE, event.EVENT_END_MINUTE);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentEndTime);
                    }
                    else
                    {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, event.EVENT_START_HOUR);
                        calendar.set(Calendar.MINUTE, event.EVENT_START_MINUTE);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentStartTime);

                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, event.EVENT_END_HOUR);
                        calendar.set(Calendar.MINUTE, event.EVENT_END_MINUTE);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentEndTime);
                    }
                }
                else
                {

                }
            }
        }

        stopSelf();
    }

    private void cancelAlarms()
    {
        Intent intent;
        PendingIntent pendingIntent;
        int dayOfWeek;

        calendar = Calendar.getInstance();
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY? 6: calendar.get(Calendar.DAY_OF_WEEK)-2;

        for(Schedule schedule : scheduleDBDataSource.getScheduleList(dayOfWeek))
        {
            intent = new Intent(this, BroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, (schedule.START_HOUR*60)+schedule.START_MINUTE, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.cancel(pendingIntent);

            intent = new Intent(this, BroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, (schedule.END_HOUR*60)+schedule.END_MINUTE, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.cancel(pendingIntent);
        }

        for(Event event : eventDBDataSource.getDayEventList())
        {
            intent = new Intent(this, BroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, ((event.EVENT_START_HOUR*60)+event.EVENT_START_MINUTE)*10, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.cancel(pendingIntent);

            intent = new Intent(this, BroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, ((event.EVENT_END_HOUR*60)+event.EVENT_END_MINUTE)*10, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.cancel(pendingIntent);
        }

        cancelNotification();
    }

    private void setNotification(String profileName)
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                                                            .setSmallIcon(R.drawable.ic_launcher)
                                                            .setContentTitle("Schedulr")
                                                            .setContentText(profileName);
        Notification notification = notificationBuilder.build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        notificationManager.notify(001, notification);
    }

    private void cancelNotification()
    {
        notificationManager.cancelAll();

        setServiceAlarm();
    }

    private void setDisplayParameters(Profile profile)
    {
        if(profile.DISPLAY_BRIGHTNESS_AUTO_STATE)
        {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        }
        else
        {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, profile.DISPLAY_BRIGHTNESS_LEVEL);
        }

        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, profile.DISPLAY_SLEEP_TIMEOUT);
    }

    private void setAudioParameters(Profile profile)
    {
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        audioManager.setStreamVolume(AudioManager.STREAM_RING, (audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)*profile.SOUND_VOLUME_RINGTONE)/10, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, (audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)*profile.SOUND_VOLUME_RINGTONE)/10, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)*profile.SOUND_VOLUME_APPLICATION)/10, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, (audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)*profile.SOUND_VOLUME_APPLICATION)/10, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, (audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)*profile.SOUND_VOLUME_ALARM)/10, 0);

//        RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, Uri.parse(profile.SOUND_RINGTONE));
//        RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION, Uri.parse(profile.SOUND_NOTIFICATION_TONE));

        audioManager.setRingerMode(profile.SOUND_RING_MODE);
    }

    private void setWifi(Profile profile)
    {
        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(profile.WIFI_STATE);
    }

    private void setMobileData(Profile profile)
    {
        ConnectivityManager connectivityManager  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

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

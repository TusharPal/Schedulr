package prototyped.schedulr.activity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.Schedule;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class ServiceProfileScheduler extends Service
{
    private ScheduleDBDataSource scheduleDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
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
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(intent.getExtras() != null && intent.getExtras().getBoolean("cancel_alarms"))
        {
            cancelAlarms();
            setAlarms();
        }
        else
        {
            setAlarms();
        }

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private void setAlarms()
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

                if(timeStampStart <= timeStampCurrent && timeStampCurrent <= timeStampEnd)
                {
                    setProfile(schedule);
                    Intent intentEndTime = new Intent(getBaseContext(), BroadcastReceiverAlarm.class);
                    intentEndTime.putExtra("profile_name", "default");
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
                    Intent intentStartTime = new Intent(getBaseContext(), BroadcastReceiverAlarm.class);
                    intentStartTime.putExtra("profile_name", schedule.PROFILE_NAME);
                    Intent intentEndTime = new Intent(getBaseContext(), BroadcastReceiverAlarm.class);
                    intentEndTime.putExtra("profile_name", "Default");
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
    }

    private void cancelAlarms()
    {
        Intent intent = new Intent(this, BroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 001, intent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.cancel(pendingIntent);
        cancelNotification();
    }

    private void setProfile(Schedule schedule)
    {
        setNotification(schedule);
    }

    private void setNotification(Schedule schedule)
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Schedulr")
                .setContentText(schedule.PROFILE_NAME);
        notificationManager.notify(001, notificationBuilder.build());
    }

    private void cancelNotification()
    {
        notificationManager.cancelAll();
    }
}

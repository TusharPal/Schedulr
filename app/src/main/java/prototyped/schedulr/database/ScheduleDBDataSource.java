package prototyped.schedulr.database;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import prototyped.schedulr.activity.BroadcastReceiverAlarms;

public class ScheduleDBDataSource
{
    private Context context;
    private ScheduleDBHelper helper;
    private SQLiteDatabase database;
    private AlarmManager alarmManager;
    private String allColumns[] = {ScheduleDBHelper.COLUMN_ID,
                                    ScheduleDBHelper.COLUMN_PROFILE_ICON,
                                    ScheduleDBHelper.COLUMN_PROFILE_NAME,
                                    ScheduleDBHelper.COLUMN_START_HOUR,
                                    ScheduleDBHelper.COLUMN_START_MINUTE,
                                    ScheduleDBHelper.COLUMN_END_HOUR,
                                    ScheduleDBHelper.COLUMN_END_MINUTE,
                                    ScheduleDBHelper.COLUMN_MONDAY,
                                    ScheduleDBHelper.COLUMN_TUESDAY,
                                    ScheduleDBHelper.COLUMN_WEDNESDAY,
                                    ScheduleDBHelper.COLUMN_THURSDAY,
                                    ScheduleDBHelper.COLUMN_FRIDAY,
                                    ScheduleDBHelper.COLUMN_SATURDAY,
                                    ScheduleDBHelper.COLUMN_SUNDAY};

    public ScheduleDBDataSource(Context context)
    {
        this.context = context;
        helper = new ScheduleDBHelper(context, ScheduleDBHelper.DATABASE_NAME, null, ScheduleDBHelper.DATABASE_VERSION);
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    public void open()
    {
        database = helper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
        helper.close();
    }

    public void createSchedule(Schedule schedule)
    {
        ContentValues values = new ContentValues();
        values.put(ScheduleDBHelper.COLUMN_PROFILE_ICON, schedule.PROFILE_ICON);
        values.put(ScheduleDBHelper.COLUMN_PROFILE_NAME, schedule.PROFILE_NAME);
        values.put(ScheduleDBHelper.COLUMN_START_HOUR, schedule.START_HOUR);
        values.put(ScheduleDBHelper.COLUMN_START_MINUTE, schedule.START_MINUTE);
        values.put(ScheduleDBHelper.COLUMN_END_HOUR, schedule.END_HOUR);
        values.put(ScheduleDBHelper.COLUMN_END_MINUTE, schedule.END_MINUTE);
        values.put(ScheduleDBHelper.COLUMN_MONDAY, schedule.MONDAY);
        values.put(ScheduleDBHelper.COLUMN_TUESDAY, schedule.TUESDAY);
        values.put(ScheduleDBHelper.COLUMN_WEDNESDAY, schedule.WEDNESDAY);
        values.put(ScheduleDBHelper.COLUMN_THURSDAY, schedule.THURSDAY);
        values.put(ScheduleDBHelper.COLUMN_FRIDAY, schedule.FRIDAY);
        values.put(ScheduleDBHelper.COLUMN_SATURDAY, schedule.SATURDAY);
        values.put(ScheduleDBHelper.COLUMN_SUNDAY, schedule.SUNDAY);

        database.insert(ScheduleDBHelper.TABLE_NAME, null, values);
    }

    public void updateSchedule(Schedule schedule)
    {
        ContentValues values = new ContentValues();
        values.put(ScheduleDBHelper.COLUMN_PROFILE_ICON, schedule.PROFILE_ICON);
        values.put(ScheduleDBHelper.COLUMN_PROFILE_NAME, schedule.PROFILE_NAME);
        values.put(ScheduleDBHelper.COLUMN_START_HOUR, schedule.START_HOUR);
        values.put(ScheduleDBHelper.COLUMN_START_MINUTE, schedule.START_MINUTE);
        values.put(ScheduleDBHelper.COLUMN_END_HOUR, schedule.END_HOUR);
        values.put(ScheduleDBHelper.COLUMN_END_MINUTE, schedule.END_MINUTE);
        values.put(ScheduleDBHelper.COLUMN_MONDAY, schedule.MONDAY);
        values.put(ScheduleDBHelper.COLUMN_TUESDAY, schedule.TUESDAY);
        values.put(ScheduleDBHelper.COLUMN_WEDNESDAY, schedule.WEDNESDAY);
        values.put(ScheduleDBHelper.COLUMN_THURSDAY, schedule.THURSDAY);
        values.put(ScheduleDBHelper.COLUMN_FRIDAY, schedule.FRIDAY);
        values.put(ScheduleDBHelper.COLUMN_SATURDAY, schedule.SATURDAY);
        values.put(ScheduleDBHelper.COLUMN_SUNDAY, schedule.SUNDAY);

        database.update(ScheduleDBHelper.TABLE_NAME, values, ScheduleDBHelper.COLUMN_ID + " = " + schedule.ID, null);
    }

    public void deleteSchedule(Schedule schedule)
    {
        Intent intentStart;
        Intent intentEnd;
        PendingIntent pendingIntentStart;
        PendingIntent pendingIntentEnd;

        intentStart = new Intent(context.getApplicationContext(), BroadcastReceiverAlarms.class);
        intentStart.putExtra("profile_name", schedule.PROFILE_NAME);
        intentStart.putExtra("is_schedule", true);
        intentEnd = new Intent(context.getApplicationContext(), BroadcastReceiverAlarms.class);
        intentEnd.putExtra("profile_name", "Default");
        intentEnd.putExtra("is_schedule", true);
        pendingIntentStart = PendingIntent.getBroadcast(context.getApplicationContext(), (int)(schedule.ID*19), intentStart, PendingIntent.FLAG_ONE_SHOT);
        pendingIntentEnd = PendingIntent.getBroadcast(context.getApplicationContext(), (int)(schedule.ID*21), intentEnd, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.cancel(pendingIntentStart);
        alarmManager.cancel(pendingIntentEnd);
        pendingIntentStart.cancel();
        pendingIntentEnd.cancel();

        database.delete(ScheduleDBHelper.TABLE_NAME, ScheduleDBHelper.COLUMN_ID + " = "  + schedule.ID, null);
    }

    public void deleteSchedule(String profileName)
    {
        Schedule schedule;
        Intent intentStart;
        Intent intentEnd;
        PendingIntent pendingIntentStart;
        PendingIntent pendingIntentEnd;
        Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_PROFILE_NAME + " = "  + "\"" + profileName + "\"", null, null, null, null);

        if(cursor.moveToNext())
        {
            while(!cursor.isAfterLast())
            {
                schedule = cursorToSchedule(cursor);

                intentStart = new Intent(context.getApplicationContext(), BroadcastReceiverAlarms.class);
                intentStart.putExtra("profile_name", schedule.PROFILE_NAME);
                intentStart.putExtra("is_schedule", true);
                intentEnd = new Intent(context.getApplicationContext(), BroadcastReceiverAlarms.class);
                intentEnd.putExtra("profile_name", "Default");
                intentEnd.putExtra("is_schedule", true);
                pendingIntentStart = PendingIntent.getBroadcast(context.getApplicationContext(), (int)(schedule.ID*19), intentStart, PendingIntent.FLAG_ONE_SHOT);
                pendingIntentEnd = PendingIntent.getBroadcast(context.getApplicationContext(), (int)(schedule.ID*21), intentEnd, PendingIntent.FLAG_ONE_SHOT);
                alarmManager.cancel(pendingIntentStart);
                alarmManager.cancel(pendingIntentEnd);
                pendingIntentStart.cancel();
                pendingIntentEnd.cancel();

                database.delete(ScheduleDBHelper.TABLE_NAME, ScheduleDBHelper.COLUMN_ID + " = "  + schedule.ID, null);
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    public Schedule getSchedule(long id)
    {
        Schedule schedule;
        Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_ID + " = " + id, null, null, null, null);

        if(cursor.moveToFirst())
        {
            schedule = cursorToSchedule(cursor);
            cursor.close();
        }
        else
        {
            return null;
        }

        return schedule;
    }

    public List<Schedule> getScheduleList(int dayOfWeek)
    {
        List<Schedule> list = new ArrayList<Schedule>();
        Cursor cursor;

        switch(dayOfWeek)
        {
            case 0:
            {
                cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_MONDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC, " + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");
                if(cursor.moveToFirst())
                {
                    while(!cursor.isAfterLast())
                    {
                        list.add(cursorToSchedule(cursor));
                        cursor.moveToNext();
                    }
                    cursor.close();
                }

                break;
            }

            case 1:
            {
                cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_TUESDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC, " + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");
                if(cursor.moveToFirst())
                {
                    while(!cursor.isAfterLast())
                    {
                        list.add(cursorToSchedule(cursor));
                        cursor.moveToNext();
                    }
                    cursor.close();
                }

                break;
            }

            case 2:
            {
                cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_WEDNESDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC, " + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");
                if(cursor.moveToFirst())
                {
                    while(!cursor.isAfterLast())
                    {
                        list.add(cursorToSchedule(cursor));
                        cursor.moveToNext();
                    }
                    cursor.close();
                }

                break;
            }

            case 3:
            {
                cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_THURSDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC, " + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");
                if(cursor.moveToFirst())
                {
                    while(!cursor.isAfterLast())
                    {
                        list.add(cursorToSchedule(cursor));
                        cursor.moveToNext();
                    }
                    cursor.close();
                }

                break;
            }

            case 4:
            {
                cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_FRIDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC, " + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");
                if(cursor.moveToFirst())
                {
                    while(!cursor.isAfterLast())
                    {
                        list.add(cursorToSchedule(cursor));
                        cursor.moveToNext();
                    }
                    cursor.close();
                }

                break;
            }

            case 5:
            {
                cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_SATURDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC, " + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");
                if(cursor.moveToFirst())
                {
                    while(!cursor.isAfterLast())
                    {
                        list.add(cursorToSchedule(cursor));
                        cursor.moveToNext();
                    }
                    cursor.close();
                }

                break;
            }

            case 6:
            {
                cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_SUNDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC, " + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");
                if(cursor.moveToFirst())
                {
                    while(!cursor.isAfterLast())
                    {
                        list.add(cursorToSchedule(cursor));
                        cursor.moveToNext();
                    }
                    cursor.close();
                }

                break;
            }
        }

        return list;
    }

    public long getCurrentSchedule()
    {
        Schedule schedule;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ? 6 : calendar.get(Calendar.DAY_OF_WEEK)-2;
        int currentTime = (calendar.get(Calendar.HOUR_OF_DAY)*60)+calendar.get(Calendar.MINUTE);

        switch(dayOfWeek)
        {
            case 0:
            {
                Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_MONDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC," + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");

                while(cursor.moveToFirst())
                {
                    schedule = cursorToSchedule(cursor);
                    if((((schedule.START_HOUR*60)+schedule.START_MINUTE) <= currentTime) && (currentTime <= ((schedule.END_HOUR*60)+schedule.END_MINUTE)))
                    {
                        return schedule.ID;
                    }
                    else
                    {
                        cursor.moveToNext();
                    }
                }
                cursor.close();

                break;
            }
            case 1:
            {
                Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_TUESDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC," + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");

                while(cursor.moveToFirst())
                {
                    schedule = cursorToSchedule(cursor);
                    if((((schedule.START_HOUR*60)+schedule.START_MINUTE) <= currentTime) && (currentTime <= ((schedule.END_HOUR*60)+schedule.END_MINUTE)))
                    {
                        return schedule.ID;
                    }
                    else
                    {
                        cursor.moveToNext();
                    }
                }
                cursor.close();

                break;
            }
            case 2:
            {
                Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_WEDNESDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC," + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");

                while(cursor.moveToFirst())
                {
                    schedule = cursorToSchedule(cursor);
                    if((((schedule.START_HOUR*60)+schedule.START_MINUTE) <= currentTime) && (currentTime <= ((schedule.END_HOUR*60)+schedule.END_MINUTE)))
                    {
                        return schedule.ID;
                    }
                    else
                    {
                        cursor.moveToNext();
                    }
                }
                cursor.close();

                break;
            }
            case 3:
            {
                Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_THURSDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC," + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");

                while(cursor.moveToFirst())
                {
                    schedule = cursorToSchedule(cursor);
                    if((((schedule.START_HOUR*60)+schedule.START_MINUTE) <= currentTime) && (currentTime <= ((schedule.END_HOUR*60)+schedule.END_MINUTE)))
                    {
                        return schedule.ID;
                    }
                    else
                    {
                        cursor.moveToNext();
                    }
                }
                cursor.close();

                break;
            }
            case 4:
            {
                Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_FRIDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC," + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");

                while(cursor.moveToFirst())
                {
                    schedule = cursorToSchedule(cursor);
                    if((((schedule.START_HOUR*60)+schedule.START_MINUTE) <= currentTime) && (currentTime <= ((schedule.END_HOUR*60)+schedule.END_MINUTE)))
                    {
                        return schedule.ID;
                    }
                    else
                    {
                        cursor.moveToNext();
                    }
                }
                cursor.close();

                break;
            }
            case 5:
            {
                Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_SATURDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC," + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");

                while(cursor.moveToFirst())
                {
                    schedule = cursorToSchedule(cursor);
                    if((((schedule.START_HOUR*60)+schedule.START_MINUTE) <= currentTime) && (currentTime <= ((schedule.END_HOUR*60)+schedule.END_MINUTE)))
                    {
                        return schedule.ID;
                    }
                    else
                    {
                        cursor.moveToNext();
                    }
                }
                cursor.close();

                break;
            }
            case 6:
            {
                Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_SUNDAY + " = " + 1, null, null, null, ScheduleDBHelper.COLUMN_START_HOUR + " ASC," + ScheduleDBHelper.COLUMN_START_MINUTE + " ASC");

                while(cursor.moveToFirst())
                {
                    schedule = cursorToSchedule(cursor);
                    if((((schedule.START_HOUR*60)+schedule.START_MINUTE) <= currentTime) && (currentTime <= ((schedule.END_HOUR*60)+schedule.END_MINUTE)))
                    {
                        return schedule.ID;
                    }
                    else
                    {
                        cursor.moveToNext();
                    }
                }
                cursor.close();

                break;
            }
        }

        return -1;
    }

    private Schedule cursorToSchedule(Cursor cursor)
    {
        Schedule schedule = new Schedule();

        schedule.ID = cursor.getLong(0);
        schedule.PROFILE_ICON = cursor.getInt(1);
        schedule.PROFILE_NAME = cursor.getString(2);
        schedule.START_HOUR = cursor.getInt(3);
        schedule.START_MINUTE = cursor.getInt(4);
        schedule.END_HOUR = cursor.getInt(5);
        schedule.END_MINUTE = cursor.getInt(6);
        schedule.MONDAY = cursor.getInt(7);
        schedule.TUESDAY = cursor.getInt(8);
        schedule.WEDNESDAY = cursor.getInt(9);
        schedule.THURSDAY = cursor.getInt(10);
        schedule.FRIDAY = cursor.getInt(11);
        schedule.SATURDAY = cursor.getInt(12);
        schedule.SUNDAY = cursor.getInt(13);

        return schedule;
    }

    public int checkIfValidSchedule(Schedule schedule)
    {
        if(((schedule.START_HOUR*100)+schedule.START_MINUTE) > ((schedule.END_HOUR*100)+schedule.END_MINUTE) || ((schedule.START_HOUR*100)+schedule.START_MINUTE) == ((schedule.END_HOUR*100)+schedule.END_MINUTE))
        {
            Log.d("case", "1");
            return 1;
        }

        for(int dayOfWeek=0; dayOfWeek<7; dayOfWeek++)
        {
            Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_ID + " <> " + schedule.ID + " AND (" + ScheduleDBHelper.COLUMN_MONDAY + " = " + schedule.MONDAY + " OR " + ScheduleDBHelper.COLUMN_TUESDAY + " = " + schedule.TUESDAY + " OR " + ScheduleDBHelper.COLUMN_WEDNESDAY + " = " + schedule.WEDNESDAY + " OR " + ScheduleDBHelper.COLUMN_THURSDAY + " = " + schedule.THURSDAY + " OR " + ScheduleDBHelper.COLUMN_FRIDAY + " = " + schedule.FRIDAY + " OR " + ScheduleDBHelper.COLUMN_SATURDAY + " = " + schedule.SATURDAY + " OR " + ScheduleDBHelper.COLUMN_SUNDAY + " = " + schedule.SUNDAY + ")", null, null, null, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Schedule temp = cursorToSchedule(cursor);

                if(((schedule.START_HOUR*100)+schedule.START_MINUTE) <= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) >= ((temp.END_HOUR*100)+temp.END_MINUTE))
                {
                    cursor.close();
                    Log.d("case", "2");

                    return 2;
                }
                else if(((schedule.START_HOUR*100)+schedule.START_MINUTE) <= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) >= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) <= ((temp.END_HOUR*100)+temp.END_MINUTE))
                {
                    cursor.close();
                    Log.d("case", "3");

                    return 2;
                }
                else if(((schedule.START_HOUR*100)+schedule.START_MINUTE) >= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.START_HOUR*100)+schedule.START_MINUTE) <= ((temp.END_HOUR*100)+temp.END_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) >= ((temp.END_HOUR*100)+temp.END_MINUTE))
                {
                    cursor.close();
                    Log.d("case", "4");

                    return 2;
                }
                else if(((schedule.START_HOUR*100)+schedule.START_MINUTE) >= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) <= ((temp.END_HOUR*100)+temp.END_MINUTE))
                {
                    cursor.close();
                    Log.d("case", "5");

                    return 2;
                }
                else
                {
                    cursor.moveToNext();
                }
            }
        }

        return 0;
    }
}

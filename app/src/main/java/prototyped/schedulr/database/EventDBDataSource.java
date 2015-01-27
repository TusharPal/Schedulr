package prototyped.schedulr.database;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import prototyped.schedulr.activity.BroadcastReceiverAlarms;

public class EventDBDataSource
{
    private Context context;
    private EventDBHelper helper;
    private SQLiteDatabase database;
    private AlarmManager alarmManager;
    private final String allColumns[] = {EventDBHelper.COLUMN_ID,
                                            EventDBHelper.COLUMN_TITLE,
                                            EventDBHelper.COLUMN_NOTE,
                                            EventDBHelper.COLUMN_PROFILE_NAME,
                                            EventDBHelper.COLUMN_START_HOUR,
                                            EventDBHelper.COLUMN_START_MINUTE,
                                            EventDBHelper.COLUMN_END_HOUR,
                                            EventDBHelper.COLUMN_END_MINUTE,
                                            EventDBHelper.COLUMN_DAY_OF_MONTH,
                                            EventDBHelper.COLUMN_MONTH,
                                            EventDBHelper.COLUMN_YEAR,
                                            EventDBHelper.COLUMN_LATITUDE,
                                            EventDBHelper.COLUMN_LONGITUDE,
                                            EventDBHelper.COLUMN_FLAG_LOCATION_SET};

    public EventDBDataSource(Context context)
    {
        this.context = context;
        helper = new EventDBHelper(context);
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

    public void createEvent(Event event)
    {
        ContentValues values = new ContentValues();
        values.put(EventDBHelper.COLUMN_TITLE, event.TITLE);
        values.put(EventDBHelper.COLUMN_NOTE, event.NOTE);
        values.put(EventDBHelper.COLUMN_PROFILE_NAME, event.PROFILE_NAME);
        values.put(EventDBHelper.COLUMN_START_HOUR, event.START_HOUR);
        values.put(EventDBHelper.COLUMN_START_MINUTE, event.START_MINUTE);
        values.put(EventDBHelper.COLUMN_END_HOUR, event.END_HOUR);
        values.put(EventDBHelper.COLUMN_END_MINUTE, event.END_MINUTE);
        values.put(EventDBHelper.COLUMN_DAY_OF_MONTH, event.DAY_OF_MONTH);
        values.put(EventDBHelper.COLUMN_MONTH, event.MONTH);
        values.put(EventDBHelper.COLUMN_YEAR, event.YEAR);
        values.put(EventDBHelper.COLUMN_LATITUDE, event.LATITUDE);
        values.put(EventDBHelper.COLUMN_LONGITUDE, event.LONGITUDE);
        values.put(EventDBHelper.COLUMN_FLAG_LOCATION_SET, event.FLAG_LOCATION_SET);

        database.insert(EventDBHelper.TABLE_NAME, null, values);
    }

    public void editEvent(Event event)
    {
        ContentValues values = new ContentValues();
        values.put(EventDBHelper.COLUMN_TITLE, event.TITLE);
        values.put(EventDBHelper.COLUMN_NOTE, event.NOTE);
        values.put(EventDBHelper.COLUMN_PROFILE_NAME, event.PROFILE_NAME);
        values.put(EventDBHelper.COLUMN_START_HOUR, event.START_HOUR);
        values.put(EventDBHelper.COLUMN_START_MINUTE, event.START_MINUTE);
        values.put(EventDBHelper.COLUMN_END_HOUR, event.END_HOUR);
        values.put(EventDBHelper.COLUMN_END_MINUTE, event.END_MINUTE);
        values.put(EventDBHelper.COLUMN_DAY_OF_MONTH, event.DAY_OF_MONTH);
        values.put(EventDBHelper.COLUMN_MONTH, event.MONTH);
        values.put(EventDBHelper.COLUMN_YEAR, event.YEAR);
        values.put(EventDBHelper.COLUMN_LATITUDE, event.LATITUDE);
        values.put(EventDBHelper.COLUMN_LONGITUDE, event.LONGITUDE);
        values.put(EventDBHelper.COLUMN_FLAG_LOCATION_SET, event.FLAG_LOCATION_SET);

        database.update(EventDBHelper.TABLE_NAME, values, EventDBHelper.COLUMN_ID + " = " +  event.ID, null);
    }

    public void deleteEvent(Event event)
    {
        Intent intentStart;
        Intent intentEnd;
        PendingIntent pendingIntentStart;
        PendingIntent pendingIntentEnd;

        intentStart = new Intent(context.getApplicationContext(), BroadcastReceiverAlarms.class);
        intentStart.putExtra("profile_name", event.PROFILE_NAME);
        intentStart.putExtra("is_schedule", false);
        intentEnd = new Intent(context.getApplicationContext(), BroadcastReceiverAlarms.class);
        intentEnd.putExtra("profile_name", "Default");
        intentEnd.putExtra("is_schedule", false);
        pendingIntentStart = PendingIntent.getBroadcast(context.getApplicationContext(), (int)(event.ID*39), intentStart, PendingIntent.FLAG_ONE_SHOT);
        pendingIntentEnd = PendingIntent.getBroadcast(context.getApplicationContext(), (int)(event.ID*41), intentEnd, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.cancel(pendingIntentStart);
        alarmManager.cancel(pendingIntentEnd);
        pendingIntentStart.cancel();
        pendingIntentEnd.cancel();

        database.delete(EventDBHelper.TABLE_NAME, EventDBHelper.COLUMN_ID + " = " +  event.ID, null);
    }

    public void deleteEvent(String profileName)
    {
        Event event;
        Intent intentStart;
        Intent intentEnd;
        PendingIntent pendingIntentStart;
        PendingIntent pendingIntentEnd;
        Cursor cursor = database.query(EventDBHelper.TABLE_NAME, allColumns, EventDBHelper.COLUMN_PROFILE_NAME + " = " + "\"" + profileName + "\"", null, null, null, null, null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                event = cursorToEvent(cursor);

                intentStart = new Intent(context.getApplicationContext(), BroadcastReceiverAlarms.class);
                intentStart.putExtra("profile_name", event.PROFILE_NAME);
                intentStart.putExtra("is_schedule", false);
                intentEnd = new Intent(context.getApplicationContext(), BroadcastReceiverAlarms.class);
                intentEnd.putExtra("profile_name", "Default");
                intentEnd.putExtra("is_schedule", false);
                pendingIntentStart = PendingIntent.getBroadcast(context.getApplicationContext(), (int)(event.ID*39), intentStart, PendingIntent.FLAG_ONE_SHOT);
                pendingIntentEnd = PendingIntent.getBroadcast(context.getApplicationContext(), (int)(event.ID*41), intentEnd, PendingIntent.FLAG_ONE_SHOT);
                alarmManager.cancel(pendingIntentStart);
                alarmManager.cancel(pendingIntentEnd);
                pendingIntentStart.cancel();
                pendingIntentEnd.cancel();

                database.delete(EventDBHelper.TABLE_NAME, EventDBHelper.COLUMN_ID + " = " + event.ID, null);
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    public Event getEvent(long id)
    {
        Event event;
        Cursor cursor = database.query(EventDBHelper.TABLE_NAME, allColumns, EventDBHelper.COLUMN_ID + " = " + id, null, null, null, EventDBHelper.COLUMN_ID);
        if(cursor.moveToFirst())
        {
            event = cursorToEvent(cursor);
        }
        else
        {
            event = null;
        }
        cursor.close();

        return event;
    }

    public List<Event> getEventList()
    {
        List<Event> list = new ArrayList<Event>();
        Cursor cursor = database.query(EventDBHelper.TABLE_NAME, allColumns, null, null, null, null, EventDBHelper.COLUMN_YEAR + " ASC," +  EventDBHelper.COLUMN_MONTH + " ASC," + EventDBHelper.COLUMN_DAY_OF_MONTH + " ASC," + EventDBHelper.COLUMN_START_HOUR + " ASC," + EventDBHelper.COLUMN_START_MINUTE + " ASC");
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            list.add(cursorToEvent(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public List<Event> getCurrentDayEventList()
    {
        List<Event> list = new ArrayList<Event>();
        Cursor cursor = database.query(EventDBHelper.TABLE_NAME, allColumns, EventDBHelper.COLUMN_DAY_OF_MONTH + " = " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " AND " + EventDBHelper.COLUMN_MONTH + " = " + Calendar.getInstance().get(Calendar.MONTH) + " AND " + EventDBHelper.COLUMN_YEAR + " = " + Calendar.getInstance().get(Calendar.YEAR), null, null, null, EventDBHelper.COLUMN_YEAR + " ASC," +  EventDBHelper.COLUMN_MONTH + " ASC," + EventDBHelper.COLUMN_DAY_OF_MONTH + " ASC," + EventDBHelper.COLUMN_START_HOUR + " ASC," + EventDBHelper.COLUMN_START_MINUTE + " ASC");
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            list.add(cursorToEvent(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public Event cursorToEvent(Cursor cursor)
    {
        Event event = new Event();

        event.ID = cursor.getLong(0);
        event.TITLE = cursor.getString(1);
        event.NOTE = cursor.getString(2);
        event.PROFILE_NAME = cursor.getString(3);
        event.START_HOUR = cursor.getInt(4);
        event.START_MINUTE = cursor.getInt(5);
        event.END_HOUR = cursor.getInt(6);
        event.END_MINUTE = cursor.getInt(7);
        event.DAY_OF_MONTH = cursor.getInt(8);
        event.MONTH = cursor.getInt(9);
        event.YEAR = cursor.getInt(10);
        event.LATITUDE = cursor.getFloat(11);
        event.LONGITUDE = cursor.getFloat(12);
        event.FLAG_LOCATION_SET = cursor.getInt(13);

        return event;
    }

    public int checkIfEventValid(Event event)
    {
        int currentTime;
        int currentDate;
        int eventStart;
        int eventDate;
        int eventEnd;
        int tempStart;
        int tempEnd;

        currentTime = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60) + Calendar.getInstance().get(Calendar.MINUTE);
        currentDate = (((Calendar.getInstance().get(Calendar.DAY_OF_MONTH)*100) + Calendar.getInstance().get(Calendar.MONTH))*10000) + Calendar.getInstance().get(Calendar.YEAR);
        eventDate = (((event.DAY_OF_MONTH*100)+event.MONTH)*10000)+event.YEAR;
        eventStart = (event.START_HOUR *60) + event.START_MINUTE;
        eventEnd = (event.END_HOUR *60) + event.END_MINUTE;

        if(((eventStart > eventEnd) || (eventStart == eventEnd)))
        {
            return 1;
        }
        else if((eventStart <= currentTime) && (currentDate == eventDate))
        {
            return 3;
        }
        else
        {
            Cursor cursor = database.query(EventDBHelper.TABLE_NAME, allColumns, EventDBHelper.COLUMN_START_HOUR + " = " + event.START_HOUR + " AND " + EventDBHelper.COLUMN_START_MINUTE + " = " + event.START_MINUTE + " AND " + EventDBHelper.COLUMN_DAY_OF_MONTH + " = " + event.DAY_OF_MONTH + " AND " + EventDBHelper.COLUMN_MONTH + " = " + event.MONTH + " AND " + EventDBHelper.COLUMN_YEAR + " = " + event.YEAR + " AND " + EventDBHelper.COLUMN_ID + " <> " + event.ID, null, null, null, null);
            if(cursor.moveToFirst())
            {
                while(!cursor.isAfterLast())
                {
                    Event temp = cursorToEvent(cursor);
                    tempStart = (temp.START_HOUR *60) + temp.START_MINUTE;
                    tempEnd = (temp.END_HOUR *60) + temp.END_MINUTE;

                    if(event.ID == temp.ID)
                    {
                        cursor.moveToNext();
                    }
                    else if(event.DAY_OF_MONTH == temp.DAY_OF_MONTH && event.MONTH == temp.MONTH && event.YEAR == temp.YEAR)
                    {
                        if(eventStart <= tempStart && eventEnd >= tempEnd)
                        {
                            cursor.close();

                            return 2;
                        }
                        else if(eventStart <= tempStart && eventEnd >= tempStart && eventEnd <= tempEnd)
                        {
                            cursor.close();

                            return 2;
                        }
                        else if(eventStart >= tempStart && eventStart <= tempEnd && eventEnd >= tempEnd)
                        {
                            cursor.close();

                            return 2;
                        }
                        else if(eventStart >= tempStart && eventEnd <= tempEnd)
                        {
                            cursor.close();

                            return 2;
                        }
                        else
                        {
                            cursor.moveToNext();
                        }
                    }
                }
                cursor.close();

                return 0;
            }
            else
            {
                return 0;
            }
        }
    }
}

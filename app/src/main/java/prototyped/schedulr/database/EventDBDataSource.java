package prototyped.schedulr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventDBDataSource
{
    private EventDBHelper helper;
    private SQLiteDatabase database;
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
        helper = new EventDBHelper(context);
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
        values.put(EventDBHelper.COLUMN_TITLE, event.EVENT_TITLE);
        values.put(EventDBHelper.COLUMN_NOTE, event.EVENT_NOTE);
        values.put(EventDBHelper.COLUMN_PROFILE_NAME, event.EVENT_PROFILE_NAME);
        values.put(EventDBHelper.COLUMN_START_HOUR, event.EVENT_START_HOUR);
        values.put(EventDBHelper.COLUMN_START_MINUTE, event.EVENT_START_MINUTE);
        values.put(EventDBHelper.COLUMN_END_HOUR, event.EVENT_END_HOUR);
        values.put(EventDBHelper.COLUMN_END_MINUTE, event.EVENT_END_MINUTE);
        values.put(EventDBHelper.COLUMN_DAY_OF_MONTH, event.EVENT_DAY_OF_MONTH);
        values.put(EventDBHelper.COLUMN_MONTH, event.EVENT_MONTH);
        values.put(EventDBHelper.COLUMN_YEAR, event.EVENT_YEAR);
        values.put(EventDBHelper.COLUMN_LATITUDE, event.EVENT_LATITUDE);
        values.put(EventDBHelper.COLUMN_LONGITUDE, event.EVENT_LONGITUDE);
        values.put(EventDBHelper.COLUMN_FLAG_LOCATION_SET, event.EVENT_FLAG_LOCATION_SET);

        database.insert(EventDBHelper.TABLE_NAME, null, values);
    }

    public void editEvent(Event event)
    {
        ContentValues values = new ContentValues();
        values.put(EventDBHelper.COLUMN_TITLE, event.EVENT_TITLE);
        values.put(EventDBHelper.COLUMN_NOTE, event.EVENT_NOTE);
        values.put(EventDBHelper.COLUMN_PROFILE_NAME, event.EVENT_PROFILE_NAME);
        values.put(EventDBHelper.COLUMN_START_HOUR, event.EVENT_START_HOUR);
        values.put(EventDBHelper.COLUMN_START_MINUTE, event.EVENT_START_MINUTE);
        values.put(EventDBHelper.COLUMN_END_HOUR, event.EVENT_END_HOUR);
        values.put(EventDBHelper.COLUMN_END_MINUTE, event.EVENT_END_MINUTE);
        values.put(EventDBHelper.COLUMN_DAY_OF_MONTH, event.EVENT_DAY_OF_MONTH);
        values.put(EventDBHelper.COLUMN_MONTH, event.EVENT_MONTH);
        values.put(EventDBHelper.COLUMN_YEAR, event.EVENT_YEAR);
        values.put(EventDBHelper.COLUMN_LATITUDE, event.EVENT_LATITUDE);
        values.put(EventDBHelper.COLUMN_LONGITUDE, event.EVENT_LONGITUDE);
        values.put(EventDBHelper.COLUMN_FLAG_LOCATION_SET, event.EVENT_FLAG_LOCATION_SET);

        database.update(EventDBHelper.TABLE_NAME, values, EventDBHelper.COLUMN_ID + " = " +  event.EVENT_ID, null);
    }

    public void deleteEvent(long id)
    {
        database.delete(EventDBHelper.TABLE_NAME, EventDBHelper.COLUMN_ID + " = " +  id, null);
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

    public List<Event> getDayEventList()
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

        event.EVENT_ID = cursor.getLong(0);
        event.EVENT_TITLE = cursor.getString(1);
        event.EVENT_NOTE = cursor.getString(2);
        event.EVENT_PROFILE_NAME = cursor.getString(3);
        event.EVENT_START_HOUR = cursor.getInt(4);
        event.EVENT_START_MINUTE = cursor.getInt(5);
        event.EVENT_END_HOUR = cursor.getInt(6);
        event.EVENT_END_MINUTE = cursor.getInt(7);
        event.EVENT_DAY_OF_MONTH = cursor.getInt(8);
        event.EVENT_MONTH = cursor.getInt(9);
        event.EVENT_YEAR = cursor.getInt(10);
        event.EVENT_LATITUDE = cursor.getFloat(11);
        event.EVENT_LONGITUDE = cursor.getFloat(12);
        event.EVENT_FLAG_LOCATION_SET = cursor.getInt(13);

        return event;
    }

    public int checkIfEventValid(Event event)
    {
        int currentTime;
        int currentStart;
        int currentEnd;
        int tempStart;
        int tempEnd;

        currentTime = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60) + Calendar.getInstance().get(Calendar.MINUTE);
        currentStart = (event.EVENT_START_HOUR*60) + event.EVENT_START_MINUTE;
        currentEnd = (event.EVENT_END_HOUR*60) + event.EVENT_END_MINUTE;

        if((currentStart > currentEnd) || (currentStart == currentEnd))
        {
            return 1;
        }
        else if(currentStart <= currentTime)
        {
            return 3;
        }
        else
        {
            Cursor cursor = database.query(EventDBHelper.TABLE_NAME, allColumns, EventDBHelper.COLUMN_START_HOUR + " = " + event.EVENT_START_HOUR + " AND " + EventDBHelper.COLUMN_START_MINUTE + " = " + event.EVENT_START_MINUTE + " AND " + EventDBHelper.COLUMN_DAY_OF_MONTH + " = " + event.EVENT_DAY_OF_MONTH + " AND " + EventDBHelper.COLUMN_MONTH + " = " + event.EVENT_MONTH + " AND " + EventDBHelper.COLUMN_YEAR + " = " + event.EVENT_YEAR, null, null, null, null);
            if(cursor.moveToFirst())
            {
                while(!cursor.isAfterLast())
                {
                    Event temp = cursorToEvent(cursor);
                    tempStart = (temp.EVENT_START_HOUR*60) + temp.EVENT_START_MINUTE;
                    tempEnd = (temp.EVENT_END_HOUR*60) + temp.EVENT_END_MINUTE;

                    if(event.EVENT_ID == temp.EVENT_ID)
                    {
                        cursor.moveToNext();
                    }
                    else if(event.EVENT_DAY_OF_MONTH == temp.EVENT_DAY_OF_MONTH && event.EVENT_MONTH == temp.EVENT_MONTH && event.EVENT_YEAR == temp.EVENT_YEAR)
                    {
                        if(currentStart <= tempStart && currentEnd >= tempEnd)
                        {
                            cursor.close();

                            return 2;
                        }
                        else if(currentStart <= tempStart && currentEnd >= tempStart && currentEnd <= tempEnd)
                        {
                            cursor.close();

                            return 2;
                        }
                        else if(currentStart >= tempStart && currentStart <= tempEnd && currentEnd >= tempEnd)
                        {
                            cursor.close();

                            return 2;
                        }
                        else if(currentStart >= tempStart && currentEnd <= tempEnd)
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

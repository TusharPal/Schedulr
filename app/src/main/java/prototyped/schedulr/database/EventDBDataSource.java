package prototyped.schedulr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventDBDataSource
{
    private EventDBHelper helper;
    private SQLiteDatabase database;
    private final String allColumns[] = {EventDBHelper.COLUMN_ID,
                                            EventDBHelper.COLUMN_TITLE,
                                            EventDBHelper.COLUMN_NOTE,
                                            EventDBHelper.COLUMN_START_TIME,
                                            EventDBHelper.COLUMN_END_TIME,
                                            EventDBHelper.COLUMN_DAY_OF_WEEK,
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
        values.put(EventDBHelper.COLUMN_ID, event.EVENT_ID);
        values.put(EventDBHelper.COLUMN_TITLE, event.EVENT_TITLE);
        values.put(EventDBHelper.COLUMN_NOTE, event.EVENT_NOTE);
        values.put(EventDBHelper.COLUMN_START_TIME, event.EVENT_START_TIME);
        values.put(EventDBHelper.COLUMN_END_TIME, event.EVENT_END_TIME);
        values.put(EventDBHelper.COLUMN_DAY_OF_WEEK, event.EVENT_DAY_OF_WEEK);
        values.put(EventDBHelper.COLUMN_LATITUDE, event.EVENT_LATITUDE);
        values.put(EventDBHelper.COLUMN_LONGITUDE, event.EVENT_LONGITUDE);
        values.put(EventDBHelper.COLUMN_FLAG_LOCATION_SET, event.EVENT_FLAG_LOCATION_SET);

        database.insert(EventDBHelper.TABLE_NAME, null, values);
    }

    public void editEvent(Event event)
    {
        ContentValues values = new ContentValues();
        values.put(EventDBHelper.COLUMN_ID, event.EVENT_ID);
        values.put(EventDBHelper.COLUMN_TITLE, event.EVENT_TITLE);
        values.put(EventDBHelper.COLUMN_NOTE, event.EVENT_NOTE);
        values.put(EventDBHelper.COLUMN_START_TIME, event.EVENT_START_TIME);
        values.put(EventDBHelper.COLUMN_END_TIME, event.EVENT_END_TIME);
        values.put(EventDBHelper.COLUMN_DAY_OF_WEEK, event.EVENT_DAY_OF_WEEK);
        values.put(EventDBHelper.COLUMN_LATITUDE, event.EVENT_LATITUDE);
        values.put(EventDBHelper.COLUMN_LONGITUDE, event.EVENT_LONGITUDE);
        values.put(EventDBHelper.COLUMN_FLAG_LOCATION_SET, event.EVENT_FLAG_LOCATION_SET);

        database.update(EventDBHelper.TABLE_NAME, values, EventDBHelper.COLUMN_ID + " = " +  event.EVENT_ID, null);
    }

    public void deleteEvent(Event event)
    {
        database.delete(EventDBHelper.TABLE_NAME, EventDBHelper.COLUMN_ID + " = " +  event.EVENT_ID, null);
    }

    public Event getEvent(long id)
    {
        return null;
    }

    public List<Event> getEventList(int dayOfWeek)
    {
        List<Event> list = new ArrayList<Event>();

        Cursor cursor = database.query(EventDBHelper.TABLE_NAME, allColumns, EventDBHelper.COLUMN_DAY_OF_WEEK + " = " + dayOfWeek, null, null, null, EventDBHelper.COLUMN_ID);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            list.add(cursorToEvent(cursor));
        }
        cursor.close();

        return null;
    }

    public Event cursorToEvent(Cursor cursor)
    {
        Event event = new Event();

        event.EVENT_ID = cursor.getLong(0);
        event.EVENT_TITLE = cursor.getString(1);
        event.EVENT_NOTE = cursor.getString(2);
        event.EVENT_START_TIME = cursor.getString(3);
        event.EVENT_END_TIME = cursor.getString(4);
        event.EVENT_DAY_OF_WEEK = cursor.getInt(5);
        event.EVENT_LATITUDE = cursor.getFloat(6);
        event.EVENT_LONGITUDE = cursor.getFloat(7);
        event.EVENT_FLAG_LOCATION_SET = cursor.getInt(8);

        return event;
    }
}

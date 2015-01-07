package prototyped.schedulr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDBDataSource
{
    private ScheduleDBHelper helper;
    private SQLiteDatabase database;
    private String allColumns[] = {ScheduleDBHelper.COLUMN_PROFILE_ICON,
                                    ScheduleDBHelper.COLUMN_PROFILE_NAME,
                                    ScheduleDBHelper.COLUMN_START_TIME,
                                    ScheduleDBHelper.COLUMN_END_TIME,
                                    ScheduleDBHelper.COLUMN_DAY_OF_WEEK};

    public ScheduleDBDataSource(Context context)
    {
        helper = new ScheduleDBHelper(context, ScheduleDBHelper.DATABASE_NAME, null, ScheduleDBHelper.DATABASE_VERSION);
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

    public int createSchedule(Schedule schedule)
    {
        ContentValues values = new ContentValues();
        values.put(ScheduleDBHelper.COLUMN_PROFILE_ICON, schedule.PROFILE_ICON);
        values.put(ScheduleDBHelper.COLUMN_PROFILE_NAME, schedule.PROFILE_NAME);
        values.put(ScheduleDBHelper.COLUMN_START_TIME, schedule.START_TIME);
        values.put(ScheduleDBHelper.COLUMN_END_TIME, schedule.END_TIME);
        values.put(ScheduleDBHelper.COLUMN_DAY_OF_WEEK, schedule.DAY_OF_WEEK);

        database.insert(ScheduleDBHelper.TABLE_NAME, null, values);

        return 0;
    }

    public int updateSchedule(Schedule schedule)
    {
        ContentValues values = new ContentValues();
        values.put(ScheduleDBHelper.COLUMN_PROFILE_ICON, schedule.PROFILE_ICON);
        values.put(ScheduleDBHelper.COLUMN_PROFILE_NAME, schedule.PROFILE_NAME);
        values.put(ScheduleDBHelper.COLUMN_START_TIME, schedule.START_TIME);
        values.put(ScheduleDBHelper.COLUMN_END_TIME, schedule.END_TIME);
        values.put(ScheduleDBHelper.COLUMN_DAY_OF_WEEK, schedule.DAY_OF_WEEK);

        database.insert(ScheduleDBHelper.TABLE_NAME, null, values);

        return 0;
    }

    public void deleteSchedule(Schedule schedule)
    {
        database.delete(ScheduleDBHelper.TABLE_NAME, ScheduleDBHelper.COLUMN_PROFILE_NAME + " = " + "\"" + schedule.PROFILE_NAME + "\"" + " && " + ScheduleDBHelper.COLUMN_START_TIME + " = " + "\"" + schedule.START_TIME + "\"" + " && " + ScheduleDBHelper.COLUMN_END_TIME + " = " + "\"" + schedule.END_TIME + "\"" + " && " + ScheduleDBHelper.COLUMN_DAY_OF_WEEK + " = " + "\"" + schedule.DAY_OF_WEEK + "\"", null);
    }

    public List<Schedule> getScheduleList(int dayOfWeek)
    {
        List<Schedule> list = new ArrayList<Schedule>();
        Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_DAY_OF_WEEK + " = " + dayOfWeek, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            list.add(cursorToSchedule(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    private Schedule cursorToSchedule(Cursor cursor)
    {
        Schedule schedule = new Schedule();

        schedule.PROFILE_ICON = cursor.getInt(0);
        schedule.PROFILE_NAME = cursor.getString(1);
        schedule.START_TIME = cursor.getInt(2);
        schedule.END_TIME = cursor.getInt(3);
        schedule.DAY_OF_WEEK = cursor.getInt(4);

        return schedule;
    }
}

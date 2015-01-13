package prototyped.schedulr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDBDataSource
{
    private ScheduleDBHelper helper;
    private SQLiteDatabase database;
    private String allColumns[] = {ScheduleDBHelper.COLUMN_PROFILE_ICON,
                                    ScheduleDBHelper.COLUMN_PROFILE_NAME,
                                    ScheduleDBHelper.COLUMN_START_HOUR,
                                    ScheduleDBHelper.COLUMN_START_MINUTE,
                                    ScheduleDBHelper.COLUMN_END_HOUR,
                                    ScheduleDBHelper.COLUMN_END_MINUTE,
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

    public void createSchedule(Schedule schedule)
    {
        ContentValues values = new ContentValues();
        values.put(ScheduleDBHelper.COLUMN_PROFILE_ICON, schedule.PROFILE_ICON);
        values.put(ScheduleDBHelper.COLUMN_PROFILE_NAME, schedule.PROFILE_NAME);
        values.put(ScheduleDBHelper.COLUMN_START_HOUR, schedule.START_HOUR);
        values.put(ScheduleDBHelper.COLUMN_START_MINUTE, schedule.START_MINUTE);
        values.put(ScheduleDBHelper.COLUMN_END_HOUR, schedule.END_HOUR);
        values.put(ScheduleDBHelper.COLUMN_END_MINUTE, schedule.END_MINUTE);
        values.put(ScheduleDBHelper.COLUMN_DAY_OF_WEEK, schedule.DAY_OF_WEEK);

        database.insert(ScheduleDBHelper.TABLE_NAME, null, values);
    }

    public void deleteSchedule(Schedule schedule)
    {
        database.delete(ScheduleDBHelper.TABLE_NAME, ScheduleDBHelper.COLUMN_PROFILE_NAME + " = " + "\"" + schedule.PROFILE_NAME + "\"" + " AND " + ScheduleDBHelper.COLUMN_START_HOUR + " = " + "\"" + schedule.START_HOUR + "\"" + " AND " + ScheduleDBHelper.COLUMN_START_MINUTE + " = " + "\"" + schedule.START_MINUTE + "\"" + " AND " + ScheduleDBHelper.COLUMN_END_HOUR + " = " + "\"" + schedule.END_HOUR + "\"" + " AND " + ScheduleDBHelper.COLUMN_END_MINUTE + " = " + "\"" + schedule.END_MINUTE + "\"" + " AND " + ScheduleDBHelper.COLUMN_DAY_OF_WEEK + " = " + "\"" + schedule.DAY_OF_WEEK + "\"", null);
    }

    public void deleteSchedule(String profileName)
    {
        database.delete(ScheduleDBHelper.TABLE_NAME, ScheduleDBHelper.COLUMN_PROFILE_NAME + " = "  + "\"" + profileName + "\"", null);    }

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
        schedule.START_HOUR = cursor.getInt(2);
        schedule.START_MINUTE = cursor.getInt(3);
        schedule.END_HOUR = cursor.getInt(4);
        schedule.END_MINUTE = cursor.getInt(5);
        schedule.DAY_OF_WEEK = cursor.getInt(6);

        return schedule;
    }

    public int checkIfValidSchedule(Schedule schedule)
    {
        Cursor cursor = database.query(ScheduleDBHelper.TABLE_NAME, allColumns, ScheduleDBHelper.COLUMN_DAY_OF_WEEK + " = " + schedule.DAY_OF_WEEK, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            Schedule temp = cursorToSchedule(cursor);

            if(((schedule.START_HOUR*100)+schedule.START_MINUTE) >= ((schedule.END_HOUR*100)+schedule.END_MINUTE))
            {
                cursor.close();

                return 1;
            }
            if(((schedule.START_HOUR*100)+schedule.START_MINUTE) <= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) >= ((temp.END_HOUR*100)+temp.END_MINUTE))
            {
                cursor.close();

                return 2;
            }
            else if(((schedule.START_HOUR*100)+schedule.START_MINUTE) <= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) >= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) <= ((temp.END_HOUR*100)+temp.END_MINUTE))
            {
                cursor.close();

                return 2;
            }
            else if(((schedule.START_HOUR*100)+schedule.START_MINUTE) >= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.START_HOUR*100)+schedule.START_MINUTE) <= ((temp.END_HOUR*100)+temp.END_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) >= ((temp.END_HOUR*100)+temp.END_MINUTE))
            {
                cursor.close();

                return 2;
            }
            else if(((schedule.START_HOUR*100)+schedule.START_MINUTE) >= ((temp.START_HOUR*100)+temp.START_MINUTE) && ((schedule.END_HOUR*100)+schedule.END_MINUTE) <= ((temp.END_HOUR*100)+temp.END_MINUTE))
            {
                cursor.close();

                return 2;
            }
            else
            {
                cursor.moveToNext();
            }
        }
        cursor.close();

        return 0;
    }
}

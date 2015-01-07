package prototyped.schedulr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScheduleDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "schedule.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "schedule";

    public static final String COLUMN_PROFILE_ICON = "schedule_profile_icon";
    public static final String COLUMN_PROFILE_NAME = "schedule_profile_name";
    public static final String COLUMN_START_TIME = "schedule_start_time";
    public static final String COLUMN_END_TIME = "schedule_end_time";
    public static final String COLUMN_DAY_OF_WEEK = "schedule_day_of_week";

    private final String DATABASE_CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "("
                                                        + COLUMN_PROFILE_ICON + " INTEGER NOT NULL, "
                                                        + COLUMN_PROFILE_NAME + " TEXT NOT NULL, "
                                                        + COLUMN_START_TIME + " INTEGER NOT NULL, "
                                                        + COLUMN_END_TIME + " INTEGER NOT NULL, "
                                                        + COLUMN_DAY_OF_WEEK + " INTEGER NOT NULL);";

    public ScheduleDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(DATABASE_CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}

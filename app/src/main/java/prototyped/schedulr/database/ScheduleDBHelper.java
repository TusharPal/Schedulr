package prototyped.schedulr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScheduleDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "schedule.db";
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "schedule";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PROFILE_ICON = "schedule_profile_icon";
    public static final String COLUMN_PROFILE_NAME = "schedule_profile_name";
    public static final String COLUMN_START_HOUR = "schedule_start_hour";
    public static final String COLUMN_START_MINUTE = "schedule_start_minute";
    public static final String COLUMN_END_HOUR = "schedule_end_hour";
    public static final String COLUMN_END_MINUTE = "schedule_end_minute";
    public static final String COLUMN_MONDAY = "schedule_monday";
    public static final String COLUMN_TUESDAY = "schedule_tuesday";
    public static final String COLUMN_WEDNESDAY = "schedule_wednesday";
    public static final String COLUMN_THURSDAY = "schedule_thursday";
    public static final String COLUMN_FRIDAY = "schedule_friday";
    public static final String COLUMN_SATURDAY = "schedule_saturday";
    public static final String COLUMN_SUNDAY = "schedule_sunday";

    private final String DATABASE_CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "("
                                                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                        + COLUMN_PROFILE_ICON + " INTEGER NOT NULL, "
                                                        + COLUMN_PROFILE_NAME + " TEXT NOT NULL, "
                                                        + COLUMN_START_HOUR + " INTEGER NOT NULL, "
                                                        + COLUMN_START_MINUTE + " INTEGER NOT NULL, "
                                                        + COLUMN_END_HOUR + " INTEGER NOT NULL, "
                                                        + COLUMN_END_MINUTE + " INTEGER NOT NULL, "
                                                        + COLUMN_MONDAY + " INTEGER NOT NULL, "
                                                        + COLUMN_TUESDAY + " INTEGER NOT NULL, "
                                                        + COLUMN_WEDNESDAY + " INTEGER NOT NULL, "
                                                        + COLUMN_THURSDAY + " INTEGER NOT NULL, "
                                                        + COLUMN_FRIDAY + " INTEGER NOT NULL, "
                                                        + COLUMN_SATURDAY + " INTEGER NOT NULL, "
                                                        + COLUMN_SUNDAY + " INTEGER NOT NULL);";

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
        onCreate(sqLiteDatabase);
    }
}

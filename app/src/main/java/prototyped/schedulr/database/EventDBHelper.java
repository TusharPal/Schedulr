package prototyped.schedulr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "events.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "events";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "event_title";
    public static final String COLUMN_NOTE = "event_note";
    public static final String COLUMN_PROFILE_NAME = "event_profile_name";
    public static final String COLUMN_START_HOUR = "event_start_hour";
    public static final String COLUMN_START_MINUTE = "event_start_minute";
    public static final String COLUMN_END_HOUR = "event_end_hour";
    public static final String COLUMN_END_MINUTE = "event_end_minute";
    public static final String COLUMN_DAY_OF_MONTH = "event_day_of_month";
    public static final String COLUMN_MONTH = "event_month";
    public static final String COLUMN_YEAR = "event_year";
    public static final String COLUMN_LATITUDE = "event_latitude";
    public static final String COLUMN_LONGITUDE = "event_longitude";
    public static final String COLUMN_FLAG_LOCATION_SET = "event_location_set";

    private final String DATABASE_CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
                                                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                        COLUMN_TITLE + " TEXT NOT NULL, " +
                                                        COLUMN_NOTE + " TEXT NOT NULL, " +
                                                        COLUMN_PROFILE_NAME + " TEXT NOT NULL, " +
                                                        COLUMN_START_HOUR + " INTEGER NOT NULL, " +
                                                        COLUMN_START_MINUTE + " INTEGER NOT NULL, " +
                                                        COLUMN_END_HOUR + " INTEGER NOT NULL, " +
                                                        COLUMN_END_MINUTE + " INTEGER NOT NULL, " +
                                                        COLUMN_DAY_OF_MONTH + " INTEGER NOT NULL, " +
                                                        COLUMN_MONTH + " INTEGER NOT NULL, " +
                                                        COLUMN_YEAR + " INTEGER NOT NULL, " +
                                                        COLUMN_LATITUDE + " REAL NULL, " +
                                                        COLUMN_LONGITUDE + " REAL NULL, " +
                                                        COLUMN_FLAG_LOCATION_SET + " INTEGER NOT NULL);";

    public EventDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

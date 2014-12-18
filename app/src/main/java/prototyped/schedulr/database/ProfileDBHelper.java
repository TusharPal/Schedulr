package prototyped.schedulr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Profiles.db";
    public static final String TABLE_NAME = "Profiles";
    public static final int DATABASE_VERSION = 0;

    public static final String COLUMN_PROFILE_NAME = "profile_name";
    public static final String COLUMN_PROFILE_COLOR = "profile_color";
    public static final String COLUMN_PROFILE_ICON = "profile_icon";
    public static final String COLUMN_SOUND_RINGTONE = "profile_sound_ringtone";
    public static final String COLUMN_SOUND_RING_MODE = "profile_sound_ring_mode";
    public static final String COLUMN_SOUND_NOTIFICATION_TONE = "profile_sound_notification_tone";
    public static final String COLUMN_DISPLAY_BRIGHTNESS_LEVEL = "profile_display_brightness_level";
    public static final String COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE = "profile_display_brightness_auto_state";
    public static final String COLUMN_DISPLAY_SLEEP_TIMEOUT = "profile_display_sleep_timeout";
    public static final String COLUMN_WIFI_STATE = "profile_wifi_state";

    private final String DATABASE_CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "("
                                                        + COLUMN_PROFILE_NAME + " TEXT PRIMARY KEY,"
                                                        + COLUMN_PROFILE_COLOR + " TEXT NOT NULL,"
                                                        + COLUMN_PROFILE_ICON + " INTEGER NOT NULL,"
                                                        + COLUMN_SOUND_RINGTONE + " STRING NOT NULL,"
                                                        + COLUMN_SOUND_RING_MODE + " INTEGER NOT NULL,"
                                                        + COLUMN_SOUND_NOTIFICATION_TONE + " STRING NOT NULL,"
                                                        + COLUMN_DISPLAY_BRIGHTNESS_LEVEL + " INTEGER NOT NULL,"
                                                        + COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE + " INTEGER NOT NULL,"
                                                        + COLUMN_DISPLAY_SLEEP_TIMEOUT + " INTEGER NOT NULL,"
                                                        + COLUMN_WIFI_STATE + " INTEGER NOT NULL);";


    public ProfileDBHelper(Context context)
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

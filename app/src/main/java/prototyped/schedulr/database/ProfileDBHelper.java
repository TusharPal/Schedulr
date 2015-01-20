package prototyped.schedulr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import prototyped.schedulr.R;

public class ProfileDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Profiles.db";
    public static final String TABLE_NAME = "Profiles";
    public static final int DATABASE_VERSION = 9;

    public static final String COLUMN_PROFILE_NAME = "profile_name";
    public static final String COLUMN_PROFILE_ICON = "profile_icon";
    public static final String COLUMN_SOUND_VOLUME_RINGTONE = "profile_sound_volume_ringtone";
    public static final String COLUMN_SOUND_VOLUME_APPLICATION = "profile_sound_volume_application";
    public static final String COLUMN_SOUND_VOLUME_ALARM = "profile_sound_volume_alarm";
    public static final String COLUMN_SOUND_RINGTONE = "profile_sound_ringtone";
    public static final String COLUMN_SOUND_NOTIFICATION_TONE = "profile_sound_notification_tone";
    public static final String COLUMN_SOUND_RING_MODE = "profile_sound_ring_mode";
    public static final String COLUMN_DISPLAY_BRIGHTNESS_LEVEL = "profile_display_brightness_level";
    public static final String COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE = "profile_display_brightness_auto_state";
    public static final String COLUMN_DISPLAY_SLEEP_TIMEOUT = "profile_display_sleep_timeout";
    public static final String COLUMN_WIFI_STATE = "profile_wifi_state";
    public static final String COLUMN_MOBILE_DATA_STATE = "profile_mobile_data_state";

    private final String DATABASE_CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "("
                                                        + COLUMN_PROFILE_NAME + " TEXT PRIMARY KEY,"
                                                        + COLUMN_PROFILE_ICON + " INTEGER NOT NULL,"
                                                        + COLUMN_DISPLAY_BRIGHTNESS_LEVEL + " INTEGER NOT NULL,"
                                                        + COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE + " INTEGER NOT NULL,"
                                                        + COLUMN_DISPLAY_SLEEP_TIMEOUT + " INTEGER NOT NULL,"
                                                        + COLUMN_SOUND_VOLUME_RINGTONE + " INTEGER NOT NULL,"
                                                        + COLUMN_SOUND_VOLUME_APPLICATION + " INTEGER NOT NULL,"
                                                        + COLUMN_SOUND_VOLUME_ALARM + " INTEGER NOT NULL,"
                                                        + COLUMN_SOUND_RINGTONE + " TEXT NOT NULL,"
                                                        + COLUMN_SOUND_NOTIFICATION_TONE + " TEXT NOT NULL,"
                                                        + COLUMN_SOUND_RING_MODE + " INTEGER NOT NULL,"
                                                        + COLUMN_WIFI_STATE + " INTEGER NOT NULL,"
                                                        + COLUMN_MOBILE_DATA_STATE + " INTEGER NOT NULL);";

    public ProfileDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(DATABASE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES("
                                                            + "\"Silent\"" + ","
                                                            + R.drawable.ic_profile_05 + ","
                                                            + 5 + ","
                                                            + 0 + ","
                                                            + 30 + ","
                                                            + 0 + ","
                                                            + 0 + ","
                                                            + 0 + ","
                                                            + "\"\"" + ","
                                                            + "\"\"" + ","
                                                            + 0 + ","
                                                            + 0 + ","
                                                            + 0 + ");");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES("
                                                            + "\"Home\"" + ","
                                                            + R.drawable.ic_profile_11 + ","
                                                            + 5 + ","
                                                            + 0 + ","
                                                            + 60 + ","
                                                            + 10 + ","
                                                            + 10 + ","
                                                            + 10 + ","
                                                            + "\"\"" + ","
                                                            + "\"\"" + ","
                                                            + 2 + ","
                                                            + 1 + ","
                                                            + 0 + ");");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES("
                                                            + "\"Work\"" + ","
                                                            + R.drawable.ic_profile_12 + ","
                                                            + 5 + ","
                                                            + 0 + ","
                                                            + 30 + ","
                                                            + 0 + ","
                                                            + 0 + ","
                                                            + 0 + ","
                                                            + "\"\"" + ","
                                                            + "\"\"" + ","
                                                            + 1 + ","
                                                            + 0 + ","
                                                            + 1 + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

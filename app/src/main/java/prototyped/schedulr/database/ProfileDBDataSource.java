package prototyped.schedulr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProfileDBDataSource
{
    private SQLiteDatabase database;
    private ProfileDBHelper dbHelper;
    private String allColumns[]={ProfileDBHelper.COLUMN_PROFILE_NAME,
                                    ProfileDBHelper.COLUMN_PROFILE_COLOR,
                                    ProfileDBHelper.COLUMN_PROFILE_ICON,
                                    ProfileDBHelper.COLUMN_SOUND_RINGTONE,
                                    ProfileDBHelper.COLUMN_SOUND_RING_MODE,
                                    ProfileDBHelper.COLUMN_SOUND_NOTIFICATION_TONE,
                                    ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_LEVEL,
                                    ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE,
                                    ProfileDBHelper.COLUMN_DISPLAY_SLEEP_TIMEOUT,
                                    ProfileDBHelper.COLUMN_WIFI_STATE};

    public ProfileDBDataSource(Context context)
    {
        dbHelper = new ProfileDBHelper(context);
    }

    public void open()
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
        dbHelper.close();
    }

    public void createProfile(Profile profile)
    {
        ContentValues values = new ContentValues();
        values.put(ProfileDBHelper.COLUMN_PROFILE_NAME, profile.PROFILE_NAME);
        values.put(ProfileDBHelper.COLUMN_PROFILE_COLOR, profile.PROFILE_COLOR);
        values.put(ProfileDBHelper.COLUMN_PROFILE_ICON, profile.PROFILE_ICON_ID);
        values.put(ProfileDBHelper.COLUMN_SOUND_RINGTONE, profile.SOUND_RINGTONE);
        values.put(ProfileDBHelper.COLUMN_SOUND_RING_MODE, profile.SOUND_RING_MODE);
        values.put(ProfileDBHelper.COLUMN_SOUND_NOTIFICATION_TONE, profile.SOUND_NOTIFICATION_TONE);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_LEVEL, profile.DISPLAY_BRIGHTNESS_LEVEL);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE, profile.DISPLAY_BRIGHTNESS_AUTO_STATE);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_SLEEP_TIMEOUT, profile.DISPLAY_SLEEP_TIMEOUT);
        values.put(ProfileDBHelper.COLUMN_WIFI_STATE, profile.WIFI_STATE);

        database.insert(ProfileDBHelper.TABLE_NAME, null, values);
    }

    public void editProfile(Profile profile)
    {
        ContentValues values = new ContentValues();
        values.put(ProfileDBHelper.COLUMN_PROFILE_NAME, profile.PROFILE_NAME);
        values.put(ProfileDBHelper.COLUMN_PROFILE_COLOR, profile.PROFILE_COLOR);
        values.put(ProfileDBHelper.COLUMN_PROFILE_ICON, profile.PROFILE_ICON_ID);
        values.put(ProfileDBHelper.COLUMN_SOUND_RINGTONE, profile.SOUND_RINGTONE);
        values.put(ProfileDBHelper.COLUMN_SOUND_RING_MODE, profile.SOUND_RING_MODE);
        values.put(ProfileDBHelper.COLUMN_SOUND_NOTIFICATION_TONE, profile.SOUND_NOTIFICATION_TONE);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_LEVEL, profile.DISPLAY_BRIGHTNESS_LEVEL);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE, profile.DISPLAY_BRIGHTNESS_AUTO_STATE);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_SLEEP_TIMEOUT, profile.DISPLAY_SLEEP_TIMEOUT);
        values.put(ProfileDBHelper.COLUMN_WIFI_STATE, profile.WIFI_STATE);

        database.update(ProfileDBHelper.TABLE_NAME, values, ProfileDBHelper.COLUMN_PROFILE_NAME + " = " + profile.PROFILE_NAME, null);
    }

    public void deleteProfile(Profile profile)
    {
        database.delete(ProfileDBHelper.TABLE_NAME, ProfileDBHelper.COLUMN_PROFILE_NAME + " = " + profile.PROFILE_NAME, null );
    }

    public Profile getProfile(String name)
    {
        Cursor cursor = database.query(ProfileDBHelper.TABLE_NAME, allColumns, ProfileDBHelper.COLUMN_PROFILE_NAME + " = " + name, null, null, null, null);
        cursor.moveToFirst();
        Profile profile = cursorToProfile(cursor);
        cursor.close();

        return profile;
    }

    public List<Profile> getProfileList()
    {
        List<Profile> list=new ArrayList<Profile>();

        Cursor cursor = database.query(ProfileDBHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            list.add(cursorToProfile(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    private Profile cursorToProfile(Cursor cursor)
    {
        Profile profile=new Profile();

        profile.PROFILE_NAME = cursor.getString(0);
        profile.PROFILE_COLOR = cursor.getString(1);
        profile.PROFILE_ICON_ID = cursor.getInt(2);
        profile.SOUND_RINGTONE = cursor.getString(3);
        profile.SOUND_RING_MODE = cursor.getInt(4);
        profile.SOUND_NOTIFICATION_TONE = cursor.getString(5);
        profile.DISPLAY_BRIGHTNESS_LEVEL = cursor.getInt(6);
        profile.DISPLAY_BRIGHTNESS_AUTO_STATE = cursor.getInt(7);
        profile.DISPLAY_SLEEP_TIMEOUT = cursor.getInt(8);
        profile.WIFI_STATE = cursor.getInt(9);

        return profile;
    }
}

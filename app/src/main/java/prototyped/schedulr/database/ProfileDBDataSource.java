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
    private Context context;
    private String allColumns[]={ProfileDBHelper.COLUMN_PROFILE_NAME,
                                    ProfileDBHelper.COLUMN_PROFILE_ICON,
                                    ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_LEVEL,
                                    ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE,
                                    ProfileDBHelper.COLUMN_DISPLAY_SLEEP_TIMEOUT,
                                    ProfileDBHelper.COLUMN_SOUND_VOLUME_RINGTONE,
                                    ProfileDBHelper.COLUMN_SOUND_VOLUME_APPLICATION,
                                    ProfileDBHelper.COLUMN_SOUND_VOLUME_ALARM,
                                    ProfileDBHelper.COLUMN_SOUND_RINGTONE,
                                    ProfileDBHelper.COLUMN_SOUND_RING_MODE,
                                    ProfileDBHelper.COLUMN_SOUND_NOTIFICATION_TONE,
                                    ProfileDBHelper.COLUMN_WIFI_STATE,
                                    ProfileDBHelper.COLUMN_MOBILE_DATA_STATE};

    public ProfileDBDataSource(Context context)
    {
        dbHelper = new ProfileDBHelper(context);
        this.context = context;
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
        values.put(ProfileDBHelper.COLUMN_PROFILE_ICON, profile.PROFILE_ICON);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_LEVEL, profile.DISPLAY_BRIGHTNESS_LEVEL);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE, (profile.DISPLAY_BRIGHTNESS_AUTO_STATE?1:0));
        values.put(ProfileDBHelper.COLUMN_DISPLAY_SLEEP_TIMEOUT, profile.DISPLAY_SLEEP_TIMEOUT);
        values.put(ProfileDBHelper.COLUMN_SOUND_VOLUME_RINGTONE, profile.SOUND_VOLUME_RINGTONE);
        values.put(ProfileDBHelper.COLUMN_SOUND_VOLUME_APPLICATION, profile.SOUND_VOLUME_APPLICATION);
        values.put(ProfileDBHelper.COLUMN_SOUND_VOLUME_ALARM, profile.SOUND_VOLUME_ALARM);
        values.put(ProfileDBHelper.COLUMN_SOUND_RINGTONE, profile.SOUND_RINGTONE);
        values.put(ProfileDBHelper.COLUMN_SOUND_RING_MODE, profile.SOUND_RING_MODE);
        values.put(ProfileDBHelper.COLUMN_SOUND_NOTIFICATION_TONE, profile.SOUND_NOTIFICATION_TONE);
        values.put(ProfileDBHelper.COLUMN_WIFI_STATE, (profile.WIFI_STATE?1:0));
        values.put(ProfileDBHelper.COLUMN_MOBILE_DATA_STATE, (profile.MOBILE_DATA_STATE?1:0));

        database.insert(ProfileDBHelper.TABLE_NAME, null, values);
    }

    public void editProfile(String oldProfileName, Profile profile)
    {
        if(oldProfileName == "Default")
        {
            profile.PROFILE_NAME = oldProfileName;
        }

        ContentValues values = new ContentValues();
        values.put(ProfileDBHelper.COLUMN_PROFILE_NAME, profile.PROFILE_NAME);
        values.put(ProfileDBHelper.COLUMN_PROFILE_ICON, profile.PROFILE_ICON);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_LEVEL, profile.DISPLAY_BRIGHTNESS_LEVEL);
        values.put(ProfileDBHelper.COLUMN_DISPLAY_BRIGHTNESS_AUTO_STATE, (profile.DISPLAY_BRIGHTNESS_AUTO_STATE?1:0));
        values.put(ProfileDBHelper.COLUMN_DISPLAY_SLEEP_TIMEOUT, profile.DISPLAY_SLEEP_TIMEOUT);
        values.put(ProfileDBHelper.COLUMN_SOUND_VOLUME_RINGTONE, profile.SOUND_VOLUME_RINGTONE);
        values.put(ProfileDBHelper.COLUMN_SOUND_VOLUME_APPLICATION, profile.SOUND_VOLUME_APPLICATION);
        values.put(ProfileDBHelper.COLUMN_SOUND_VOLUME_ALARM, profile.SOUND_VOLUME_ALARM);
        values.put(ProfileDBHelper.COLUMN_SOUND_RINGTONE, profile.SOUND_RINGTONE);
        values.put(ProfileDBHelper.COLUMN_SOUND_RING_MODE, profile.SOUND_RING_MODE);
        values.put(ProfileDBHelper.COLUMN_SOUND_NOTIFICATION_TONE, profile.SOUND_NOTIFICATION_TONE);
        values.put(ProfileDBHelper.COLUMN_WIFI_STATE, (profile.WIFI_STATE?1:0));
        values.put(ProfileDBHelper.COLUMN_MOBILE_DATA_STATE, (profile.MOBILE_DATA_STATE?1:0));

        database.update(ProfileDBHelper.TABLE_NAME, values, ProfileDBHelper.COLUMN_PROFILE_NAME + " = " + "\"" + oldProfileName + "\"", null);
    }

    public void deleteProfile(String profileName)
    {
        database.delete(ProfileDBHelper.TABLE_NAME, ProfileDBHelper.COLUMN_PROFILE_NAME + " = " + "\"" + profileName + "\"", null );
    }

    public Profile getProfile(String name)
    {
        Cursor cursor = database.query(ProfileDBHelper.TABLE_NAME, allColumns, ProfileDBHelper.COLUMN_PROFILE_NAME + " = " + "\"" + name + "\"", null, null, null, null);
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
        profile.PROFILE_ICON = cursor.getInt(1);
        profile.DISPLAY_BRIGHTNESS_LEVEL = cursor.getInt(2);
        profile.DISPLAY_BRIGHTNESS_AUTO_STATE = cursor.getInt(3)>0?true:false;
        profile.DISPLAY_SLEEP_TIMEOUT = cursor.getInt(4);
        profile.SOUND_VOLUME_RINGTONE = cursor.getInt(5);
        profile.SOUND_VOLUME_APPLICATION = cursor.getInt(6);
        profile.SOUND_VOLUME_ALARM = cursor.getInt(7);
        profile.SOUND_RINGTONE = cursor.getString(8);
        profile.SOUND_NOTIFICATION_TONE = cursor.getString(9);
        profile.SOUND_RING_MODE = cursor.getInt(10);
        profile.WIFI_STATE = cursor.getInt(11)>0?true:false;
        profile.MOBILE_DATA_STATE = cursor.getInt(12)>0?true:false;

        return profile;
    }
}

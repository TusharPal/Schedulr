package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import prototyped.schedulr.R;
import prototyped.schedulr.database.Profile;
import prototyped.schedulr.database.ProfileDBDataSource;

public class ActivityProfileCreateEdit extends PreferenceActivity implements Preference.OnPreferenceChangeListener, DialogInterface.OnClickListener
{
    private ProfileDBDataSource dataSource;
    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dataSource = new ProfileDBDataSource(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        getFragmentManager().beginTransaction().replace(android.R.id.content, ProfilePreferenceFragment.newInstance()).commit();
        setPreferences();
    }

    protected void onResume()
    {
        super.onResume();

        setPreferences();
    }

    protected void onPause()
    {
        super.onPause();


    }

    private void setPreferences()
    {
        Profile profile;

        if(getIntent().getExtras().getBoolean("flag_new_profile"))
        {
            profile = new Profile();

            sharedPreferences.edit().putString("profile_name", profile.PROFILE_NAME).apply();
            sharedPreferences.edit().putInt("profile_icon", profile.PROFILE_ICON).apply();
            sharedPreferences.edit().putInt("profile_display_brightness", profile.DISPLAY_BRIGHTNESS_LEVEL).apply();
            sharedPreferences.edit().putBoolean("profile_display_brightness_auto_state", profile.DISPLAY_BRIGHTNESS_AUTO_STATE).apply();
            sharedPreferences.edit().putInt("profile_display_timeout", profile.DISPLAY_SLEEP_TIMEOUT).apply();
            sharedPreferences.edit().putInt("profile_sound_volume_ringtone", profile.SOUND_VOLUME_RINGTONE).apply();
            sharedPreferences.edit().putInt("profile_sound_volume_application", profile.SOUND_VOLUME_APPLICATION).apply();
            sharedPreferences.edit().putInt("profile_sound_volume_alarm", profile.SOUND_VOLUME_ALARM).apply();
            sharedPreferences.edit().putString("profile_ringtone", profile.SOUND_RINGTONE).apply();
            sharedPreferences.edit().putString("profile_notification_tone", profile.SOUND_NOTIFICATION_TONE).apply();
//            sharedPreferences.edit().putInt("profile_sound_ring_mode", profile.SOUND_RING_MODE).apply();
            sharedPreferences.edit().putBoolean("profile_wifi_state", profile.WIFI_STATE).apply();
            sharedPreferences.edit().putBoolean("profile_mobile_data_state", profile.MOBILE_DATA_STATE).apply();
        }
        else
        {
            profile = dataSource.getProfile(sharedPreferences.getString("_search_profile_name", ""));

            sharedPreferences.edit().putString("profile_name", profile.PROFILE_NAME).apply();
            sharedPreferences.edit().putInt("profile_icon", profile.PROFILE_ICON).apply();
            sharedPreferences.edit().putInt("profile_display_brightness", profile.DISPLAY_BRIGHTNESS_LEVEL).apply();
            sharedPreferences.edit().putBoolean("profile_display_brightness_auto_state", profile.DISPLAY_BRIGHTNESS_AUTO_STATE).apply();
            sharedPreferences.edit().putInt("profile_display_timeout", profile.DISPLAY_SLEEP_TIMEOUT).apply();
            sharedPreferences.edit().putInt("profile_sound_volume_ringtone", profile.SOUND_VOLUME_RINGTONE).apply();
            sharedPreferences.edit().putInt("profile_sound_volume_application", profile.SOUND_VOLUME_APPLICATION).apply();
            sharedPreferences.edit().putInt("profile_sound_volume_alarm", profile.SOUND_VOLUME_ALARM).apply();
            sharedPreferences.edit().putString("profile_ringtone", profile.SOUND_RINGTONE).apply();
            sharedPreferences.edit().putString("profile_notification_tone", profile.SOUND_NOTIFICATION_TONE).apply();
//            sharedPreferences.edit().putInt("profile_sound_ring_mode", profile.SOUND_RING_MODE).apply();
            sharedPreferences.edit().putBoolean("profile_wifi_state", profile.WIFI_STATE).apply();
            sharedPreferences.edit().putBoolean("profile_mobile_data_state", profile.MOBILE_DATA_STATE).apply();
        }
    }

    private void saveProfile()
    {
        Profile profile = new Profile();

        if(getIntent().getExtras().getBoolean("flag_new_profile"))
        {
            profile.PROFILE_NAME = sharedPreferences.getString("profile_name", "");
            profile.PROFILE_ICON = sharedPreferences.getInt("profile_icon", 0);
            profile.DISPLAY_BRIGHTNESS_LEVEL = sharedPreferences.getInt("profile_display_brightness", 0);
            profile.DISPLAY_BRIGHTNESS_AUTO_STATE = sharedPreferences.getBoolean("profile_display_brightness_auto_state", false);
            profile.DISPLAY_SLEEP_TIMEOUT = sharedPreferences.getInt("profile_display_timeout", 0);
            profile.SOUND_VOLUME_RINGTONE = sharedPreferences.getInt("profile_sound_volume_ringtone", 0);
            profile.SOUND_VOLUME_APPLICATION = sharedPreferences.getInt("profile_sound_volume_application", 0);
            profile.SOUND_VOLUME_ALARM = sharedPreferences.getInt("profile_sound_volume_alarm", 0);
            profile.SOUND_RINGTONE = sharedPreferences.getString("profile_sound_ringtone", "");
            profile.SOUND_NOTIFICATION_TONE = sharedPreferences.getString("profile_sound_notification_tone", "");
//            profile.SOUND_RING_MODE = Integer.parseInt(sharedPreferences.getString("profile_sound_ring_mode", ""));
            profile.WIFI_STATE = sharedPreferences.getBoolean("profile_wifi_state", false);
            profile.MOBILE_DATA_STATE = sharedPreferences.getBoolean("profile_mobile_data_state", false);

            dataSource.createProfile(profile);
        }
        else
        {
            profile.PROFILE_NAME = sharedPreferences.getString("profile_name", "");
            profile.PROFILE_ICON = sharedPreferences.getInt("profile_icon", 0);
            profile.DISPLAY_BRIGHTNESS_LEVEL = sharedPreferences.getInt("profile_display_brightness", 0);
            profile.DISPLAY_BRIGHTNESS_AUTO_STATE = sharedPreferences.getBoolean("profile_display_brightness_auto_state", false);
            profile.DISPLAY_SLEEP_TIMEOUT = sharedPreferences.getInt("profile_display_timeout", 0);
            profile.SOUND_VOLUME_RINGTONE = sharedPreferences.getInt("profile_sound_volume_ringtone", 0);
            profile.SOUND_VOLUME_APPLICATION = sharedPreferences.getInt("profile_sound_volume_application", 0);
            profile.SOUND_VOLUME_ALARM = sharedPreferences.getInt("profile_sound_volume_alarm", 0);
            profile.SOUND_RINGTONE = sharedPreferences.getString("profile_sound_ringtone", "");
            profile.SOUND_NOTIFICATION_TONE = sharedPreferences.getString("profile_sound_notification_tone", "");
//            profile.SOUND_RING_MODE = Integer.parseInt(sharedPreferences.getString("profile_sound_ring_mode", ""));
            profile.WIFI_STATE = sharedPreferences.getBoolean("profile_wifi_state", false);
            profile.MOBILE_DATA_STATE = sharedPreferences.getBoolean("profile_mobile_data_state", false);

            dataSource.editProfile(getIntent().getExtras().getString("_search_profile_name", ""), profile);
        }
    }

    private void alertDialogSave()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Save this profile?");

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                Intent positveActivity = new Intent(getApplicationContext(),com.example.alertdialog.PositiveActivity.class);
                startActivity(positveActivity);

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent negativeActivity = new Intent(getApplicationContext(),com.example.alertdialog.NegativeActivity.class);
                startActivity(negativeActivity);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o)
    {
        return false;
    }

    public static class ProfilePreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {

        public static final ProfilePreferenceFragment newInstance()
        {
            ProfilePreferenceFragment profilePreferenceFragment = new ProfilePreferenceFragment();

            return profilePreferenceFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.profile_create_edit);
        }

        public void onPause()
        {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        public void onResume()
        {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s)
        {
            Toast.makeText(getActivity(), sharedPreferences.getString(s, ""), Toast.LENGTH_SHORT).show();
        }
    }
}

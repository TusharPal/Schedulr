package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import prototyped.schedulr.R;
import prototyped.schedulr.database.Profile;
import prototyped.schedulr.database.ProfileDBDataSource;

public class ActivityProfileCreateEdit extends PreferenceActivity
{
    private ProfileDBDataSource dataSource;
    private SharedPreferences sharedPreferences;
    private AlertDialog alertDialogSave;
    private String searchProfileName;
    private boolean flagNewProfile;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dataSource = new ProfileDBDataSource(this);
        dataSource.open();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        getFragmentManager().beginTransaction().replace(android.R.id.content, ProfilePreferenceFragment.newInstance()).commit();

        alertDialogSave = alertDialogSave();
        searchProfileName = getIntent().getExtras().getString("search_profile_name");
        flagNewProfile = getIntent().getExtras().getBoolean("flag_new_profile");

        setPreferences();
    }

    protected void onResume()
    {
        super.onResume();

        dataSource.open();
        setPreferences();
    }

    protected void onPause()
    {
        super.onPause();

        dataSource.close();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void onBackPressed()
    {
        alertDialogSave.show();
    }

    private void setPreferences()
    {
        Profile profile;

        if(flagNewProfile)
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
            sharedPreferences.edit().putString("profile_sound_ringtone", profile.SOUND_RINGTONE).apply();
            sharedPreferences.edit().putString("profile_sound_notification_tone", profile.SOUND_NOTIFICATION_TONE).apply();
            sharedPreferences.edit().putInt("profile_sound_ring_mode", profile.SOUND_RING_MODE).apply();
            sharedPreferences.edit().putBoolean("profile_wifi_state", profile.WIFI_STATE).apply();
            sharedPreferences.edit().putBoolean("profile_mobile_data_state", profile.MOBILE_DATA_STATE).apply();
        }
        else
        {
            profile = dataSource.getProfile(searchProfileName);

            sharedPreferences.edit().putString("profile_name", profile.PROFILE_NAME).apply();
            sharedPreferences.edit().putInt("profile_icon", profile.PROFILE_ICON).apply();
            sharedPreferences.edit().putInt("profile_display_brightness", profile.DISPLAY_BRIGHTNESS_LEVEL).apply();
            sharedPreferences.edit().putBoolean("profile_display_brightness_auto_state", profile.DISPLAY_BRIGHTNESS_AUTO_STATE).apply();
            sharedPreferences.edit().putInt("profile_display_timeout", profile.DISPLAY_SLEEP_TIMEOUT).apply();
            sharedPreferences.edit().putInt("profile_sound_volume_ringtone", profile.SOUND_VOLUME_RINGTONE).apply();
            sharedPreferences.edit().putInt("profile_sound_volume_application", profile.SOUND_VOLUME_APPLICATION).apply();
            sharedPreferences.edit().putInt("profile_sound_volume_alarm", profile.SOUND_VOLUME_ALARM).apply();
            sharedPreferences.edit().putString("profile_sound_ringtone", profile.SOUND_RINGTONE).apply();
            sharedPreferences.edit().putString("profile_sound_notification_tone", profile.SOUND_NOTIFICATION_TONE).apply();
            sharedPreferences.edit().putInt("profile_sound_ring_mode", profile.SOUND_RING_MODE).apply();
            sharedPreferences.edit().putBoolean("profile_wifi_state", profile.WIFI_STATE).apply();
            sharedPreferences.edit().putBoolean("profile_mobile_data_state", profile.MOBILE_DATA_STATE).apply();
        }
    }

    private void saveProfile()
    {
        Profile profile = new Profile();

        if(flagNewProfile)
        {
            profile.PROFILE_NAME = sharedPreferences.contains("profile_name")?sharedPreferences.getString("profile_name", ""):"New Profile";
            profile.PROFILE_ICON = sharedPreferences.contains("profile_icon")?sharedPreferences.getInt("profile_icon", 0):0;
            profile.DISPLAY_BRIGHTNESS_LEVEL = sharedPreferences.contains("profile_display_brightness")?sharedPreferences.getInt("profile_display_brightness", 0):5;
            profile.DISPLAY_BRIGHTNESS_AUTO_STATE = sharedPreferences.contains("profile_display_brightness_auto_state")?sharedPreferences.getBoolean("profile_display_brightness_auto_state", false):false;
            profile.DISPLAY_SLEEP_TIMEOUT = sharedPreferences.contains("profile_display_timeout")?sharedPreferences.getInt("profile_display_timeout", 30000):30000;
            profile.SOUND_VOLUME_RINGTONE = sharedPreferences.contains("profile_sound_volume_ringtone")?sharedPreferences.getInt("profile_sound_volume_ringtone", 0):5;
            profile.SOUND_VOLUME_APPLICATION = sharedPreferences.contains("profile_sound_volume_application")?sharedPreferences.getInt("profile_sound_volume_application", 0):5;
            profile.SOUND_VOLUME_ALARM = sharedPreferences.contains("profile_sound_volume_alarm")?sharedPreferences.getInt("profile_sound_volume_alarm", 0):5;
            profile.SOUND_RINGTONE = sharedPreferences.contains("profile_sound_ringtone")?sharedPreferences.getString("profile_sound_ringtone", ""):RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString();
            profile.SOUND_NOTIFICATION_TONE = sharedPreferences.contains("profile_sound_notification_tone")?sharedPreferences.getString("profile_sound_notification_tone", ""):RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString();
            profile.SOUND_RING_MODE = sharedPreferences.contains("profile_sound_ring_mode")?sharedPreferences.getInt("profile_sound_ring_mode", 2):2;
            profile.WIFI_STATE = sharedPreferences.contains("profile_wifi_state")?sharedPreferences.getBoolean("profile_wifi_state", false):false;
            profile.MOBILE_DATA_STATE = sharedPreferences.contains("profile_mobile_data_state")?sharedPreferences.getBoolean("profile_mobile_data_state", false):false;

            dataSource.createProfile(profile);
        }
        else
        {
            profile.PROFILE_NAME = sharedPreferences.contains("profile_name")?sharedPreferences.getString("profile_name", ""):"New Profile";
            profile.PROFILE_ICON = sharedPreferences.contains("profile_icon")?sharedPreferences.getInt("profile_icon", 0):0;
            profile.DISPLAY_BRIGHTNESS_LEVEL = sharedPreferences.contains("profile_display_brightness")?sharedPreferences.getInt("profile_display_brightness", 0):5;
            profile.DISPLAY_BRIGHTNESS_AUTO_STATE = sharedPreferences.contains("profile_display_brightness_auto_state")?sharedPreferences.getBoolean("profile_display_brightness_auto_state", false):false;
            profile.DISPLAY_SLEEP_TIMEOUT = sharedPreferences.contains("profile_display_timeout")?sharedPreferences.getInt("profile_display_timeout", 30000):30000;
            profile.SOUND_VOLUME_RINGTONE = sharedPreferences.contains("profile_sound_volume_ringtone")?sharedPreferences.getInt("profile_sound_volume_ringtone", 0):5;
            profile.SOUND_VOLUME_APPLICATION = sharedPreferences.contains("profile_sound_volume_application")?sharedPreferences.getInt("profile_sound_volume_application", 0):5;
            profile.SOUND_VOLUME_ALARM = sharedPreferences.contains("profile_sound_volume_alarm")?sharedPreferences.getInt("profile_sound_volume_alarm", 0):5;
            profile.SOUND_RINGTONE = sharedPreferences.contains("profile_sound_ringtone")?sharedPreferences.getString("profile_sound_ringtone", ""):RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString();
            profile.SOUND_NOTIFICATION_TONE = sharedPreferences.contains("profile_sound_notification_tone")?sharedPreferences.getString("profile_sound_notification_tone", ""):RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString();
            profile.SOUND_RING_MODE = sharedPreferences.contains("profile_sound_ring_mode")?sharedPreferences.getInt("profile_sound_ring_mode", 2):2;
            profile.WIFI_STATE = sharedPreferences.contains("profile_wifi_state")?sharedPreferences.getBoolean("profile_wifi_state", false):false;
            profile.MOBILE_DATA_STATE = sharedPreferences.contains("profile_mobile_data_state")?sharedPreferences.getBoolean("profile_mobile_data_state", false):false;

            dataSource.editProfile(searchProfileName, profile);
        }
    }

    private AlertDialog alertDialogSave()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Save this profile?");

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();

                saveProfile();
                startService(new Intent(getApplicationContext(), ServiceProfileScheduler.class));
                Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                intent.putExtra("fragment_number", 2);
                startActivity(intent);

                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();

                Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                intent.putExtra("fragment_number", 2);
                startActivity(intent);

                finish();
            }
        });

        return alertDialogBuilder.create();
    }

    public static class ProfilePreferenceFragment extends PreferenceFragment
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
        }

        public void onResume()
        {
            super.onResume();
        }
    }
}

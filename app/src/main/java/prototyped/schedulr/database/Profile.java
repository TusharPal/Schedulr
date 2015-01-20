package prototyped.schedulr.database;

import android.media.RingtoneManager;

import prototyped.schedulr.R;

public class Profile
{
    public String PROFILE_NAME;
    public int PROFILE_ICON;
    public int DISPLAY_BRIGHTNESS_LEVEL;
    public boolean DISPLAY_BRIGHTNESS_AUTO_STATE;
    public int DISPLAY_SLEEP_TIMEOUT;
    public int SOUND_VOLUME_RINGTONE;
    public int SOUND_VOLUME_APPLICATION;
    public int SOUND_VOLUME_ALARM;
    public String SOUND_RINGTONE;
    public String SOUND_NOTIFICATION_TONE;
    public int SOUND_RING_MODE;
    public boolean WIFI_STATE;
    public boolean MOBILE_DATA_STATE;

    public Profile()
    {
        this.PROFILE_NAME = "New Profile";
        this.PROFILE_ICON = R.drawable.ic_profile_01;
        this.DISPLAY_BRIGHTNESS_LEVEL = 5;
        this.DISPLAY_BRIGHTNESS_AUTO_STATE = false;
        this.DISPLAY_SLEEP_TIMEOUT = 30000;
        this.SOUND_VOLUME_RINGTONE = 5;
        this.SOUND_VOLUME_APPLICATION = 5;
        this.SOUND_VOLUME_ALARM = 5;
        this.SOUND_RINGTONE = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString();
        this.SOUND_NOTIFICATION_TONE = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString();
        this.SOUND_RING_MODE = 2;
        this.WIFI_STATE = false;
        this.MOBILE_DATA_STATE = false;
    }
}

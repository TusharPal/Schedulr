package prototyped.schedulr.database;

public class Profile
{
    public String PROFILE_NAME;
    public String PROFILE_COLOR;
    public String PROFILE_ICON;
    public String SOUND_RINGTONE;
    public int SOUND_RING_MODE;
    public String SOUND_NOTIFICATION_TONE;
    public int DISPLAY_BRIGHTNESS_LEVEL;
    public int DISPLAY_BRIGHTNESS_AUTO_STATE;
    public int DISPLAY_SLEEP_TIMEOUT;
    public int WIFI_STATE;



    public Profile()
    {
        this.PROFILE_NAME = "New Profile";
        this.PROFILE_COLOR = "#ffffff";
        this.PROFILE_ICON = "";
        this.SOUND_RINGTONE = "";
        this.SOUND_RING_MODE = 2;
        this.SOUND_NOTIFICATION_TONE = "";
        this.DISPLAY_BRIGHTNESS_LEVEL = 5;
        this.DISPLAY_BRIGHTNESS_AUTO_STATE = 0;
        this.DISPLAY_SLEEP_TIMEOUT = 1;
        this.WIFI_STATE = 0;
    }
}

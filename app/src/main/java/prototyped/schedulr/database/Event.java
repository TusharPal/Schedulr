package prototyped.schedulr.database;

public class Event
{
    public long EVENT_ID;
    public String EVENT_TITLE;
    public String EVENT_NOTE;
    public String EVENT_PROFILE_NAME;
    public int EVENT_START_HOUR;
    public int EVENT_START_MINUTE;
    public int EVENT_END_HOUR;
    public int EVENT_END_MINUTE;
    public int EVENT_DAY_OF_MONTH;
    public int EVENT_MONTH;
    public int EVENT_YEAR;
    public float EVENT_LATITUDE;
    public float EVENT_LONGITUDE;
    public int EVENT_FLAG_LOCATION_SET;

    public Event()
    {
        this.EVENT_ID = 0;
        this.EVENT_TITLE = "";
        this.EVENT_NOTE = "";
        this.EVENT_PROFILE_NAME = "";
        this.EVENT_START_HOUR = 0;
        this.EVENT_START_MINUTE = 0;
        this.EVENT_END_HOUR  = 0;
        this.EVENT_END_MINUTE = 0;
        this.EVENT_DAY_OF_MONTH = 0;
        this.EVENT_MONTH = 0;
        this.EVENT_YEAR = 0;
        this.EVENT_LATITUDE = 0;
        this.EVENT_LONGITUDE = 0;
        this.EVENT_FLAG_LOCATION_SET = 0;
    }
}

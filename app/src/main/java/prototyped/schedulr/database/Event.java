package prototyped.schedulr.database;

public class Event
{
    public long EVENT_ID;
    public String EVENT_TITLE;
    public String EVENT_NOTE;
    public String EVENT_START_TIME;
    public String EVENT_END_TIME;
    public int EVENT_DAY_OF_WEEK;
    public float EVENT_LATITUDE;
    public float EVENT_LONGITUDE;
    public int EVENT_FLAG_LOCATION_SET;

    public Event()
    {
        this.EVENT_ID = 0;
        this.EVENT_TITLE = "";
        this.EVENT_NOTE = "";
        this.EVENT_START_TIME = "";
        this.EVENT_END_TIME  = "";
        this.EVENT_DAY_OF_WEEK = 0;
        this.EVENT_LATITUDE = 0;
        this.EVENT_LONGITUDE = 0;
        this.EVENT_FLAG_LOCATION_SET = 0;
    }
}

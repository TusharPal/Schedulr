package prototyped.schedulr.database;

public class Event
{
    public long ID;
    public String TITLE;
    public String NOTE;
    public String PROFILE_NAME;
    public int START_HOUR;
    public int START_MINUTE;
    public int END_HOUR;
    public int END_MINUTE;
    public int DAY_OF_MONTH;
    public int MONTH;
    public int YEAR;
    public float LATITUDE;
    public float LONGITUDE;
    public int FLAG_LOCATION_SET;

    public Event()
    {
        this.ID = 0;
        this.TITLE = "";
        this.NOTE = "";
        this.PROFILE_NAME = "";
        this.START_HOUR = 7;
        this.START_MINUTE = 0;
        this.END_HOUR = 8;
        this.END_MINUTE = 0;
        this.DAY_OF_MONTH = 0;
        this.MONTH = 0;
        this.YEAR = 0;
        this.LATITUDE = 0;
        this.LONGITUDE = 0;
        this.FLAG_LOCATION_SET = 0;
    }
}

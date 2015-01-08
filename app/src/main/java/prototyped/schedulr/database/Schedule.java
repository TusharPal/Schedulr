package prototyped.schedulr.database;

import prototyped.schedulr.R;

public class Schedule
{
    public int PROFILE_ICON;
    public String PROFILE_NAME;
    public int START_HOUR;
    public int START_MINUTE;
    public int END_HOUR;
    public int END_MINUTE;
    public int DAY_OF_WEEK;

    public Schedule()
    {
        this.PROFILE_ICON = R.drawable.ic_profile_01;
        this.PROFILE_NAME = "";
        this.START_HOUR = 0;
        this.START_MINUTE = 0;
        this.END_HOUR = 0;
        this.END_MINUTE = 0;
        this.DAY_OF_WEEK = 1;
    }
}

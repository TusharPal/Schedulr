package prototyped.schedulr.database;

import prototyped.schedulr.R;

public class Schedule
{
    public long ID;
    public int PROFILE_ICON;
    public String PROFILE_NAME;
    public int START_HOUR;
    public int START_MINUTE;
    public int END_HOUR;
    public int END_MINUTE;
    public int MONDAY;
    public int TUESDAY;
    public int WEDNESDAY;
    public int THURSDAY;
    public int FRIDAY;
    public int SATURDAY;
    public int SUNDAY;

    public Schedule()
    {
        this.ID = 0;
        this.PROFILE_ICON = R.drawable.ic_profile_01;
        this.PROFILE_NAME = "";
        this.START_HOUR = 0;
        this.START_MINUTE = 0;
        this.END_HOUR = 0;
        this.END_MINUTE = 0;
        this.MONDAY = 0;
        this.TUESDAY = 0;
        this.WEDNESDAY = 0;
        this.THURSDAY = 0;
        this.FRIDAY = 0;
        this.SATURDAY = 0;
        this.SUNDAY = 0;
    }
}

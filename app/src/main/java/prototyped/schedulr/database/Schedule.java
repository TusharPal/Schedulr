package prototyped.schedulr.database;

import prototyped.schedulr.R;

public class Schedule
{
    public int PROFILE_ICON;
    public String PROFILE_NAME;
    public int START_TIME;
    public int END_TIME;
    public int DAY_OF_WEEK;

    public Schedule()
    {
        this.PROFILE_ICON = R.drawable.ic_profile_01;
        this.PROFILE_NAME = "";
        this.START_TIME = 0;
        this.END_TIME = 0;
        this.DAY_OF_WEEK = 1;
    }
}

package prototyped.schedulr.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import prototyped.schedulr.activity.FragmentDaySchedule;

public class ScheduleFragmentPagerAdapter extends FragmentPagerAdapter
{
    private final int fragmentCount = 7;
    private final String dayNames[] = {"Monday",
                                        "Tuesday",
                                        "Wednesday",
                                        "Thursday",
                                        "Friday",
                                        "Saturday",
                                        "Sunday"};

    public ScheduleFragmentPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position)
    {
        FragmentDaySchedule fragmentDaySchedule = FragmentDaySchedule.newInstance(position, dayNames[position]);

        return fragmentDaySchedule;
    }

    @Override
    public int getCount()
    {
        return fragmentCount;
    }

    public CharSequence getPageTitle(int position)
    {
        return dayNames[position];
    }
}

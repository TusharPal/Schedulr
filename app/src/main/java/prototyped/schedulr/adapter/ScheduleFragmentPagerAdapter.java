package prototyped.schedulr.adapter;

import android.content.Context;
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
    private Context context;

    public ScheduleFragmentPagerAdapter(Context context, FragmentManager fragmentManager)
    {
        super(fragmentManager);

        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        FragmentDaySchedule fragmentDaySchedule = FragmentDaySchedule.newInstance(context, position);

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

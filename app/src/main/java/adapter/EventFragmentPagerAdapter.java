package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import prototyped.schedulr.activity.FragmentDayEvent;

public class EventFragmentPagerAdapter extends FragmentPagerAdapter
{
    private final int fragmentCount = 7;
    private final String dayNames[] = {"Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"};

    public EventFragmentPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position)
    {
        FragmentDayEvent fragmentDayEvent = FragmentDayEvent.newInstance(position, dayNames[position]);

        return fragmentDayEvent;
    }

    @Override
    public int getCount()
    {
        return fragmentCount;
    }
}

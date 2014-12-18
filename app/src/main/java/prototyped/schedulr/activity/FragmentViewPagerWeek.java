package prototyped.schedulr.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adapter.EventFragmentPagerAdapter;
import adapter.ScheduleFragmentPagerAdapter;
import prototyped.schedulr.R;

public class FragmentViewPagerWeek extends Fragment
{
    private static String NAVIGATION_DRAWER_POSITION = "POSITION";

    public static final FragmentViewPagerWeek newInstance(int position)
    {
        FragmentViewPagerWeek fragmentSchedule = new FragmentViewPagerWeek();
        Bundle args = fragmentSchedule.getArguments();
        args.putInt(NAVIGATION_DRAWER_POSITION, position);
        fragmentSchedule.setArguments(args);

        return fragmentSchedule;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_view_pager_week, container, false);
        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.viewpager_fragment_schedules);

        switch(getArguments().getInt(NAVIGATION_DRAWER_POSITION))
        {
            case 0:
            {
                viewPager.setAdapter(new ScheduleFragmentPagerAdapter(getFragmentManager()));

                break;
            }
            case 2:
            {
                viewPager.setAdapter(new EventFragmentPagerAdapter(getFragmentManager()));

                break;
            }
        }


        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        ((ActivityMain)activity).onSectionAttached(getArguments().getInt(NAVIGATION_DRAWER_POSITION));
    }
}

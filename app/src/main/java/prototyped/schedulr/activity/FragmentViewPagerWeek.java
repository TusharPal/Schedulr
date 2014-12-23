package prototyped.schedulr.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prototyped.schedulr.adapter.EventFragmentPagerAdapter;
import prototyped.schedulr.adapter.ScheduleFragmentPagerAdapter;
import prototyped.schedulr.R;

public class FragmentViewPagerWeek extends Fragment
{
    private static final String NAVIGATION_DRAWER_POSITION = "position";

    public static final FragmentViewPagerWeek newInstance(int position)
    {
        FragmentViewPagerWeek fragmentViewPagerWeek = new FragmentViewPagerWeek();
        Bundle args = new Bundle();
        args.putInt(NAVIGATION_DRAWER_POSITION, position);
        fragmentViewPagerWeek.setArguments(args);

        return fragmentViewPagerWeek;
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
            case 1:
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

package prototyped.schedulr.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ScheduleFragmentPagerAdapter;
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class FragmentViewPagerWeek extends Fragment
{
    private static final String NAVIGATION_DRAWER_POSITION = "navigation_drawer_position";
    private static Context context;
    private ScheduleDBDataSource scheduleDBDataSource;
    private ProfileDBDataSource profileDBDataSource;

    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;

    public static final FragmentViewPagerWeek newInstance(Context c, int position)
    {
        context = c;
        FragmentViewPagerWeek fragmentViewPagerWeek = new FragmentViewPagerWeek();
        Bundle args = new Bundle();
        args.putInt(NAVIGATION_DRAWER_POSITION, position);
        fragmentViewPagerWeek.setArguments(args);

        return fragmentViewPagerWeek;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        scheduleDBDataSource = new ScheduleDBDataSource(context);
        scheduleDBDataSource.open();
        profileDBDataSource = new ProfileDBDataSource(context);
        profileDBDataSource.open();

        View rootView = inflater.inflate(R.layout.fragment_view_pager_week, container, false);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager_fragment_view_pager_week);
        pagerTabStrip = (PagerTabStrip)rootView.findViewById(R.id.pagertabstrip_fragment_view_pager_week);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColorResource(android.R.color.holo_blue_dark);
        viewPager.setAdapter(new ScheduleFragmentPagerAdapter(context, getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY?6:(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2), true);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        ((ActivityMain)activity).onSectionAttached(getArguments().getInt(NAVIGATION_DRAWER_POSITION));
    }

    public void onResume()
    {
        super.onResume();

        scheduleDBDataSource.open();
        profileDBDataSource.open();
        viewPager.setAdapter(new ScheduleFragmentPagerAdapter(context, getChildFragmentManager()));
        viewPager.setCurrentItem(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY?6:(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2), true);
    }

    public void onPause()
    {
        super.onPause();

        scheduleDBDataSource.close();
        profileDBDataSource.close();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.fragment_view_pager_week, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add_schedule_fragment_view_pager_week:
            {
                Intent intent = new Intent(context, ActivityScheduleEditor.class);
                intent.putExtra("new_schedule", true);
                intent.putExtra("current_day", viewPager.getCurrentItem());
                startActivity(intent);
                getActivity().finish();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}

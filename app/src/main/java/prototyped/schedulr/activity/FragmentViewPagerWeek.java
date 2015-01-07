package prototyped.schedulr.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ScheduleFragmentPagerAdapter;

public class FragmentViewPagerWeek extends Fragment
{
    private static final String NAVIGATION_DRAWER_POSITION = "position";
    private static Context context;

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
        View rootView = inflater.inflate(R.layout.fragment_view_pager_week, container, false);
        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.viewpager_fragment_view_pager_week);
        viewPager.setAdapter(new ScheduleFragmentPagerAdapter(context, getFragmentManager()));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        ((ActivityMain)activity).onSectionAttached(getArguments().getInt(NAVIGATION_DRAWER_POSITION));
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.fragment_view_pager_week, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_add_schedule_fragment_schedule:
            {
                Toast.makeText(context, "Add schedule", Toast.LENGTH_SHORT).show();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}

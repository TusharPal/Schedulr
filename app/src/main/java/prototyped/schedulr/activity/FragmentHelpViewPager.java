package prototyped.schedulr.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.HelpFragmentPagerAdapter;

public class FragmentHelpViewPager extends Fragment
{
    private static final String NAVIGATION_DRAWER_POSITION = "position";
    private static Context context;
    private ViewPager viewPager;

    public static final FragmentHelpViewPager newInstance(Context c, int position)
    {
        context = c;
        FragmentHelpViewPager fragmentHelpViewPager = new FragmentHelpViewPager();
        Bundle args = new Bundle();
        args.putInt(NAVIGATION_DRAWER_POSITION, position);
        fragmentHelpViewPager.setArguments(args);

        return fragmentHelpViewPager;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_help_view_pager, container, false);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager_fragment_help_view_pager);
        viewPager.setAdapter(new HelpFragmentPagerAdapter(context, getChildFragmentManager()));

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
    }

    public void onPause()
    {
        super.onPause();
    }
}

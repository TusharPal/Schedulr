package prototyped.schedulr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import prototyped.schedulr.R;

public class ActivityMain extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FragmentManager fragmentManager;
    private CharSequence mTitle;
    private String navigationDrawerItems[] = {"Schedule", "Events", "Profiles", "Help"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));

        if(getIntent().getExtras() != null)
        {
            mNavigationDrawerFragment.selectItem(getIntent().getExtras().getInt("fragment_number"));
        }
        else
        {
            mNavigationDrawerFragment.selectItem(0);
        }
        mTitle = getTitle();

        startService(new Intent(this, ServiceProfileScheduler.class));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        switch(position)
        {
            case 0:
            {
                fragmentManager.beginTransaction().replace(R.id.container, FragmentViewPagerWeek.newInstance(getApplicationContext(), position)).commit();

                break;
            }
            case 1:
            {
                fragmentManager.beginTransaction().replace(R.id.container, FragmentEvents.newInstance(position)).commit();

                break;
            }
            case 2:
            {
                fragmentManager.beginTransaction().replace(R.id.container, FragmentProfiles.newInstance(getApplicationContext(), position)).commit();

                break;
            }
            case 3:
            {
                fragmentManager.beginTransaction().replace(R.id.container, FragmentHelpViewPager.newInstance(getApplicationContext(), position)).commit();

                break;
            }
        }
    }

    public void onSectionAttached(int position)
    {
        mTitle = navigationDrawerItems[position];
    }
}

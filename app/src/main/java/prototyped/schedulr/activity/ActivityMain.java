package prototyped.schedulr.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import prototyped.schedulr.R;


public class ActivityMain extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private String navigationDrawerItems[] = {"Schedule", "Alarms", "Events", "Profiles"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        mTitle = getTitle();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch(position)
        {
            case 0:
            {
                fragmentManager.beginTransaction().replace(R.id.container, FragmentViewPagerWeek.newInstance(position)).commit();

                break;
            }
            case 1:
            {
                fragmentManager.beginTransaction().replace(R.id.container, FragmentAlarms.newInstance()).commit();

                break;
            }
            case 2:
            {
                fragmentManager.beginTransaction().replace(R.id.container, FragmentViewPagerWeek.newInstance(position)).commit();

                break;
            }
            case 3:
            {
                fragmentManager.beginTransaction().replace(R.id.container, FragmentProfiles.newInstance()).commit();

                break;
            }
        }


    }

    public void onSectionAttached(int position)
    {
        mTitle = navigationDrawerItems[position];
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            getMenuInflater().inflate(R.menu.activity_main, menu);
            restoreActionBar();

            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_activity_main_settings:
            {
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}

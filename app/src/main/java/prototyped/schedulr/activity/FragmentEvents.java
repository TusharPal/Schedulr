package prototyped.schedulr.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import prototyped.schedulr.R;

public class FragmentEvents extends Fragment
{
    private static final String NAVIGATION_DRAWER_POSITION = "POSITION";

    public static final FragmentEvents newInstance(int position)
    {
        FragmentEvents fragmentEvents = new FragmentEvents();
        Bundle args = new Bundle();
        args.putInt(NAVIGATION_DRAWER_POSITION, position);
        fragmentEvents.setArguments(args);

        return fragmentEvents;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

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
        inflater.inflate(R.menu.fragment_events, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
        }

        return super.onOptionsItemSelected(item);
    }
}

package prototyped.schedulr.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prototyped.schedulr.R;

public class FragmentEvents extends Fragment
{
    private static final String FRAGMENT_POSITION = "POSITION";

    public static final FragmentEvents newInstance(int position)
    {
        FragmentEvents fragmentEvents = new FragmentEvents();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_POSITION, position);
        fragmentEvents.setArguments(args);

        return fragmentEvents;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        return rootView;
    }
}

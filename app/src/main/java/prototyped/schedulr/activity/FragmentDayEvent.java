package prototyped.schedulr.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prototyped.schedulr.R;

public class FragmentDayEvent extends Fragment
{
    private static final String FRAGMENT_POSITION = "POSITION";
    private static final String FRAGMENT_NAME = "NAME";

    public static final FragmentDayEvent newInstance(int position, String dayName)
    {
        FragmentDayEvent fragmentDayEvent = new FragmentDayEvent();
        Bundle args = fragmentDayEvent.getArguments();
        args.putInt(FRAGMENT_POSITION, position);
        args.putString(FRAGMENT_NAME, dayName);
        fragmentDayEvent.setArguments(args);

        return fragmentDayEvent;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        return rootView;
    }
}

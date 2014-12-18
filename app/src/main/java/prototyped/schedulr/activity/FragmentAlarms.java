package prototyped.schedulr.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prototyped.schedulr.R;

public class FragmentAlarms extends Fragment
{

    public static final FragmentAlarms newInstance()
    {
        FragmentAlarms fragmentAlarms = new FragmentAlarms();

        return fragmentAlarms;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        return rootView;
    }
}

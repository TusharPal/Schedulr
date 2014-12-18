package prototyped.schedulr.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prototyped.schedulr.R;

public class FragmentProfiles extends Fragment
{
    public static final FragmentProfiles newInstance()
    {
        FragmentProfiles fragmentProfiles = new FragmentProfiles();

        return fragmentProfiles;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_profiles, container, false);

        return rootView;
    }
}

package prototyped.schedulr.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import prototyped.schedulr.R;

public class FragmentProfileCreateEdit extends PreferenceFragment
{
    private SharedPreferences sharedPreferences;



    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        addPreferencesFromResource(R.xml.profile_create_edit);
    }

    public void onPause()
    {
        super.onPause();


    }
}

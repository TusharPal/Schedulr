package prototyped.schedulr.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import prototyped.schedulr.R;

public class FragmentProfileCreateEdit extends PreferenceFragment
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.profile_create_edit);
    }
}

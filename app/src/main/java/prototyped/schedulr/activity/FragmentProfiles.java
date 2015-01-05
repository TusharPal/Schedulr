package prototyped.schedulr.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ProfileListViewAdapter;
import prototyped.schedulr.database.Profile;
import prototyped.schedulr.database.ProfileDBDataSource;

public class FragmentProfiles extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private static final String NAVIGATION_DRAWER_POSITION = "position";
    private static ProfileDBDataSource dataSource;
    private List<Profile> list;
    private static Context context;

    public static final FragmentProfiles newInstance(Context c, int position)
    {
        context = c;
        FragmentProfiles fragmentProfiles = new FragmentProfiles();
        Bundle args = new Bundle();
        args.putInt(NAVIGATION_DRAWER_POSITION, position);
        fragmentProfiles.setArguments(args);
        dataSource = new ProfileDBDataSource(context);
        dataSource.open();

        return fragmentProfiles;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        list=dataSource.getProfileList();

        View rootView = inflater.inflate(R.layout.fragment_profiles, container, false);
        ListView listView = (ListView)rootView.findViewById(R.id.listView_fragment_profiles);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        ProfileListViewAdapter adapter = new ProfileListViewAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        ((ActivityMain)activity).onSectionAttached(getArguments().getInt(NAVIGATION_DRAWER_POSITION));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Toast.makeText(getActivity().getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Toast.makeText(getActivity().getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();

        return false;
    }
}

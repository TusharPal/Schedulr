package prototyped.schedulr.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ProfileListViewAdapter;
import prototyped.schedulr.database.Profile;
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class FragmentProfiles extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private static final String NAVIGATION_DRAWER_POSITION = "position";
    private ProfileDBDataSource profileDBDataSource;
    private ScheduleDBDataSource scheduleDBDataSource;
    private List<Profile> list;
    private static Context context;
    private int position;
    private AlertDialog alertDialogDelete;
    private ProfileListViewAdapter adapter;
    private ListView listView;

    public static final FragmentProfiles newInstance(Context c, int position)
    {
        context = c;
        FragmentProfiles fragmentProfiles = new FragmentProfiles();
        Bundle args = new Bundle();
        args.putInt(NAVIGATION_DRAWER_POSITION, position);
        fragmentProfiles.setArguments(args);

        return fragmentProfiles;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        alertDialogDelete = alertDialogDelete();
        profileDBDataSource = new ProfileDBDataSource(context);
        profileDBDataSource.open();
        scheduleDBDataSource = new ScheduleDBDataSource(context);
        scheduleDBDataSource.open();
        list=profileDBDataSource.getProfileList();

        View rootView = inflater.inflate(R.layout.fragment_profiles, container, false);
        adapter = new ProfileListViewAdapter(getActivity(), list);
        listView = (ListView)rootView.findViewById(R.id.listView_fragment_profiles);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        ((ActivityMain)activity).onSectionAttached(getArguments().getInt(NAVIGATION_DRAWER_POSITION));
    }

    public void onResume()
    {
        super.onResume();

        profileDBDataSource.open();
        scheduleDBDataSource.open();
    }

    public void onPause()
    {
        super.onPause();

        profileDBDataSource.close();
        scheduleDBDataSource.close();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.fragment_profiles, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_add_profile_fragment_profiles:
            {
                Intent intent = new Intent(context, ActivityProfileCreateEdit.class);
                intent.putExtra("flag_new_profile", true);
                startActivity(intent);
                getActivity().finish();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        if(!alertDialogDelete.isShowing())
        {
            Intent intent = new Intent(getActivity(), ActivityProfileCreateEdit.class);
            intent.putExtra("search_profile_name", list.get(position).PROFILE_NAME.toString());
            intent.putExtra("flag_new_profile", false);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        this.position = position;

        alertDialogDelete.show();

        return false;
    }

    private AlertDialog alertDialogDelete()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((getActivity()));
        alertDialogBuilder.setMessage("Delete this profile? All schedules using this profile will be deleted as well.");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                dialogInterface.cancel();

                deleteProfile();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                dialogInterface.cancel();
            }
        });

        return alertDialogBuilder.create();
    }

    private void deleteProfile()
    {
        scheduleDBDataSource.deleteSchedule(list.get(position).PROFILE_NAME.toString());
        profileDBDataSource.deleteProfile(list.get(position).PROFILE_NAME.toString());
        list = profileDBDataSource.getProfileList();
        adapter = new ProfileListViewAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }
}

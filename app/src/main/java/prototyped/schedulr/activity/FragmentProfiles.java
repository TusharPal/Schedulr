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
import android.widget.Toast;

import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ProfileListViewAdapter;
import prototyped.schedulr.database.EventDBDataSource;
import prototyped.schedulr.database.Profile;
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class FragmentProfiles extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private static final String NAVIGATION_DRAWER_POSITION = "position";
    private static Context context;
    private ProfileDBDataSource profileDBDataSource;
    private ScheduleDBDataSource scheduleDBDataSource;
    private EventDBDataSource eventDBDataSource;
    private List<Profile> list;
    private int longClickPosition;

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
        profileDBDataSource = new ProfileDBDataSource(context);
        profileDBDataSource.open();
        scheduleDBDataSource = new ScheduleDBDataSource(context);
        scheduleDBDataSource.open();
        eventDBDataSource = new EventDBDataSource(context);
        eventDBDataSource.open();
        list=profileDBDataSource.getProfileList();

        View rootView = inflater.inflate(R.layout.fragment_profiles, container, false);
        listView = (ListView)rootView.findViewById(R.id.listView_fragment_profiles);
        listView.setAdapter(new ProfileListViewAdapter(context, profileDBDataSource.getProfileList()));
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

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
        eventDBDataSource.open();
    }

    public void onPause()
    {
        super.onPause();

        profileDBDataSource.close();
        scheduleDBDataSource.close();
        eventDBDataSource.close();
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
                Intent intent = new Intent(context, ActivityProfileEditor.class);
                intent.putExtra("new_profile", true);
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
        Intent intent = new Intent(context, ActivityProfileEditor.class);
        intent.putExtra("search_profile_name", list.get(position).PROFILE_NAME.toString());
        intent.putExtra("new_profile", false);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        this.longClickPosition = position;
        alertDialogDelete().show();

        return true;
    }

    private AlertDialog alertDialogDelete()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((getActivity()));
        alertDialogBuilder.setMessage("Delete this profile? All schedules and events using this profile will be deleted as well.");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                dialogInterface.cancel();

                if(list.get(longClickPosition).PROFILE_NAME.trim().equals("Default"))
                {
                    Toast.makeText(context, "Cannot delete default profile", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent serviceIntent = new Intent(context, ServiceProfileScheduler.class);
                    context.startService(serviceIntent);

                    eventDBDataSource.deleteEvent(list.get(longClickPosition).PROFILE_NAME.toString());
                    scheduleDBDataSource.deleteSchedule(list.get(longClickPosition).PROFILE_NAME.toString());
                    profileDBDataSource.deleteProfile(list.get(longClickPosition).PROFILE_NAME.toString());
                    list = profileDBDataSource.getProfileList();
                    listView.setAdapter(new ProfileListViewAdapter(getActivity(), list));

                    serviceIntent = new Intent(context, ServiceProfileScheduler.class);
                    context.startService(serviceIntent);
                }
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
}

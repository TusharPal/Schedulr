package prototyped.schedulr.activity;

import android.app.Activity;
import android.app.AlertDialog;
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

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.EventListViewAdapter;
import prototyped.schedulr.database.EventDBDataSource;
import prototyped.schedulr.database.ProfileDBDataSource;

public class FragmentEvents extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private static final String NAVIGATION_DRAWER_POSITION = "position";
    private EventDBDataSource eventDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
    private int longClickPosition;

    private ListView listView;

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
        eventDBDataSource = new EventDBDataSource(getActivity());
        eventDBDataSource.open();
        profileDBDataSource = new ProfileDBDataSource(getActivity());
        profileDBDataSource.open();

        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        listView = (ListView)rootView.findViewById(R.id.listView_fragment_event);
        listView.setAdapter(new EventListViewAdapter(getActivity(), eventDBDataSource.getEventList()));
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        return rootView;
    }

    public void onResume()
    {
        super.onResume();

        eventDBDataSource.open();
        profileDBDataSource.open();
        listView.setAdapter(new EventListViewAdapter(getActivity(), eventDBDataSource.getEventList()));
    }

    public void onPause()
    {
        super.onPause();

        eventDBDataSource.close();
        profileDBDataSource.close();
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
            case R.id.action_add_event_fragment_events:
            {
                Intent intent = new Intent(getActivity(), ActivityEventEditor.class);
                intent.putExtra("new_event", true);
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
        Intent intent = new Intent(getActivity(), ActivityEventEditor.class);
        intent.putExtra("new_event", false);
        intent.putExtra("event_id", eventDBDataSource.getEventList().get(position).ID);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        longClickPosition = position;
        alertDialogDelete().show();

        return true;
    }

    private AlertDialog alertDialogDelete()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((getActivity()));
        alertDialogBuilder.setMessage("Delete this event?");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                eventDBDataSource.deleteEvent(eventDBDataSource.getEventList().get(longClickPosition));
                listView.setAdapter(new EventListViewAdapter(getActivity(), eventDBDataSource.getEventList()));

                Intent serviceIntent = new Intent(getActivity(), ServiceProfileScheduler.class);
                getActivity().startService(serviceIntent);

                dialogInterface.cancel();
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

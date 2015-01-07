package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ScheduleListViewAdapter;
import prototyped.schedulr.database.Schedule;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class FragmentDaySchedule extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static Context context;
    private ScheduleDBDataSource dataSource;
    private ListView listViewSchedule;
    private ScheduleListViewAdapter adapter;
    private AlertDialog alertDialogDelete;
    private List<Schedule> list;
    private int position;

    public static final FragmentDaySchedule newInstance(Context c, int position)
    {
        context = c;

        FragmentDaySchedule fragmentDaySchedule = new FragmentDaySchedule();
        Bundle args = new Bundle();
        args.putInt(DAY_OF_WEEK, position);
        fragmentDaySchedule.setArguments(args);

        return fragmentDaySchedule;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        dataSource = new ScheduleDBDataSource(context);
        dataSource.open();
        alertDialogDelete = alertDialogDelete();
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        list = dataSource.getScheduleList(getArguments().getInt(DAY_OF_WEEK));
        if(list.size() != 0)
        {
            listViewSchedule = (ListView) rootView.findViewById(R.id.listView_fragment_day);
            adapter = new ScheduleListViewAdapter(context, list);
            listViewSchedule.setAdapter(adapter);
        }

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Delete this profile?");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                dataSource.deleteSchedule(list.get(position));
                list = dataSource.getScheduleList(getArguments().getInt(DAY_OF_WEEK));
                if(list.size() != 0)
                {
                    adapter = new ScheduleListViewAdapter(getActivity(), list);
                    listViewSchedule.setAdapter(adapter);
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

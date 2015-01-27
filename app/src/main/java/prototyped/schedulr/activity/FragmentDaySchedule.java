package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.Schedule;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class FragmentDaySchedule extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static Context context;
    private ScheduleDBDataSource scheduleDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
    private List<Schedule> list;
    private int longClickPosition;
    private ListView listViewSchedule;

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
        scheduleDBDataSource = new ScheduleDBDataSource(context);
        scheduleDBDataSource.open();
        profileDBDataSource = new ProfileDBDataSource(context);
        profileDBDataSource.open();
        list = scheduleDBDataSource.getScheduleList(getArguments().getInt(DAY_OF_WEEK));

        View rootView = inflater.inflate(R.layout.fragment_day, container, false);
        listViewSchedule = (ListView) rootView.findViewById(R.id.listView_fragment_day);
        listViewSchedule.setAdapter(new ScheduleListViewAdapter(context, list));
        listViewSchedule.setOnItemClickListener(this);
        listViewSchedule.setOnItemLongClickListener(this);

        return rootView;
    }

    public void onPause()
    {
        super.onPause();

        scheduleDBDataSource.close();
        profileDBDataSource.close();
    }

    public void onResume()
    {
        scheduleDBDataSource.open();
        profileDBDataSource.open();

        list = scheduleDBDataSource.getScheduleList(getArguments().getInt(DAY_OF_WEEK));
        listViewSchedule.setAdapter(new ScheduleListViewAdapter(context, list));

        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Intent intent = new Intent(context, ActivityScheduleEditor.class);
        intent.putExtra("new_schedule", false);
        intent.putExtra("current_day", DAY_OF_WEEK);
        intent.putExtra("schedule_id", list.get(position).ID);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        this.longClickPosition = position;
        alertDialogDeleteSchedule().show();

        return true;
    }

    private AlertDialog alertDialogDeleteSchedule()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Delete this schedule?");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                scheduleDBDataSource.deleteSchedule(list.get(longClickPosition));

                list = scheduleDBDataSource.getScheduleList(getArguments().getInt(DAY_OF_WEEK));
                listViewSchedule.setAdapter(new ScheduleListViewAdapter(context, list));

                Intent serviceIntent = new Intent(context, ServiceProfileScheduler.class);
                context.startService(serviceIntent);
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

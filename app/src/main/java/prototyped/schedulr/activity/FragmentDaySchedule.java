package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ProfileListViewAdapter;
import prototyped.schedulr.adapter.ScheduleListViewAdapter;
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.Schedule;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class FragmentDaySchedule extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener
{
    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static Context context;
    private ScheduleDBDataSource scheduleDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
    private ListView listViewSchedule;
    private ScheduleListViewAdapter scheduleListViewAdapter;
    private AlertDialog alertDialogDeleteSchedule;
    private AlertDialog alertDialogEditSchedule;
    private List<Schedule> list;
    private int position;
    private Spinner spinner;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private int spinnerPosition = 0;
    private int schedulePosition =0;
    private Schedule oldSchedule;
    private Schedule newSchedule;

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
        alertDialogDeleteSchedule = alertDialogDeleteSchedule();
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        list = scheduleDBDataSource.getScheduleList(getArguments().getInt(DAY_OF_WEEK));
        listViewSchedule = (ListView) rootView.findViewById(R.id.listView_fragment_day);
        scheduleListViewAdapter = new ScheduleListViewAdapter(context, list);
        listViewSchedule.setAdapter(scheduleListViewAdapter);
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
        scheduleListViewAdapter = new ScheduleListViewAdapter(context, list);
        listViewSchedule.setAdapter(scheduleListViewAdapter);

        super.onResume();
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
                scheduleDBDataSource.deleteSchedule(list.get(position));
                Intent serviceIntent = new Intent(context, ServiceProfileScheduler.class);
                serviceIntent.putExtra("cancel_alarms", true);
                context.startService(serviceIntent);

                list = scheduleDBDataSource.getScheduleList(getArguments().getInt(DAY_OF_WEEK));
                scheduleListViewAdapter = new ScheduleListViewAdapter(context, list);
                listViewSchedule.setAdapter(scheduleListViewAdapter);
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

    private AlertDialog alertDialogEditSchedule()
    {
        oldSchedule = scheduleDBDataSource.getScheduleList(getArguments().getInt(DAY_OF_WEEK)).get(schedulePosition);
        newSchedule = new Schedule();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_create_edit_schedule, null);
        spinner = (Spinner)view.findViewById(R.id.spinner_profilelist_alertdialog_create_edit_schedule);
        startTimePicker = (TimePicker)view.findViewById(R.id.timePicker_start_alertdialog_create_edit_schedule);
        endTimePicker = (TimePicker)view.findViewById(R.id.timePicker_end_alertdialog_create_edit_schedule);

        ProfileListViewAdapter profileListViewAdapter = new ProfileListViewAdapter(context, profileDBDataSource.getProfileList());
        spinner.setAdapter(profileListViewAdapter);
        spinner.setOnItemSelectedListener(this);
        startTimePicker.setIs24HourView(DateFormat.is24HourFormat(context));
        endTimePicker.setIs24HourView(DateFormat.is24HourFormat(context));
        startTimePicker.setCurrentHour(oldSchedule.START_HOUR);
        startTimePicker.setCurrentMinute(oldSchedule.START_MINUTE);
        endTimePicker.setCurrentHour(oldSchedule.END_HOUR);
        endTimePicker.setCurrentMinute(oldSchedule.END_MINUTE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                newSchedule.PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerPosition).PROFILE_NAME;
                newSchedule.PROFILE_ICON = profileDBDataSource.getProfileList().get(spinnerPosition).PROFILE_ICON;
                newSchedule.START_HOUR = startTimePicker.getCurrentHour();
                newSchedule.START_MINUTE = startTimePicker.getCurrentMinute();
                newSchedule.END_HOUR = endTimePicker.getCurrentHour();
                newSchedule.END_MINUTE = endTimePicker.getCurrentMinute();
                newSchedule.DAY_OF_WEEK = getArguments().getInt(DAY_OF_WEEK);
                scheduleDBDataSource.deleteSchedule(oldSchedule);

                switch(scheduleDBDataSource.checkIfValidSchedule(newSchedule))
                {
                    case 0:
                    {
                        scheduleDBDataSource.createSchedule(newSchedule);

                        Intent serviceIntent = new Intent(context, ServiceProfileScheduler.class);
                        serviceIntent.putExtra("cancel_alarms", true);
                        context.startService(serviceIntent);

                        onResume();
                        dialogInterface.cancel();
                        break;
                    }
                    case 1:
                    {
                        Toast.makeText(context, "Schedule not saved.  Start time cannot be later than or equal to end time", Toast.LENGTH_SHORT).show();
                        scheduleDBDataSource.createSchedule(oldSchedule);

                        dialogInterface.cancel();
                        break;
                    }
                    case 2:
                    {
                        Toast.makeText(context, "Schedule not saved. One or more existing schedules overlap with this one.", Toast.LENGTH_SHORT).show();
                        scheduleDBDataSource.createSchedule(oldSchedule);

                        dialogInterface.cancel();
                        break;
                    }
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        this.schedulePosition = position;
        alertDialogEditSchedule = alertDialogEditSchedule();
        alertDialogEditSchedule.show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        this.position = position;
        alertDialogDeleteSchedule.show();

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
    {
        this.spinnerPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
        this.spinnerPosition = 0;
    }
}

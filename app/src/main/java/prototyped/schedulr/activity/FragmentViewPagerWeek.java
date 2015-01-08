package prototyped.schedulr.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ProfileListViewAdapter;
import prototyped.schedulr.adapter.ScheduleFragmentPagerAdapter;
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.Schedule;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class FragmentViewPagerWeek extends Fragment implements AdapterView.OnItemSelectedListener, TimePicker.OnTimeChangedListener
{
    private static final String NAVIGATION_DRAWER_POSITION = "position";
    private static Context context;
    private Calendar calendar = Calendar.getInstance();
    private ViewPager viewPager;
    private AlertDialog alertDialogAddSchedule;
    private ScheduleDBDataSource scheduleDataSource;
    private ProfileDBDataSource profileDatSource;
    private Schedule schedule;
    private int spinnerPosition = -1;
    private int startTimeHour = -1;
    private int startTimeMinute = -1;
    private int endTimeHour = -1;
    private int endTimeMinute = -1;
    private int dayOfWeek;

    public static final FragmentViewPagerWeek newInstance(Context c, int position)
    {
        context = c;
        FragmentViewPagerWeek fragmentViewPagerWeek = new FragmentViewPagerWeek();
        Bundle args = new Bundle();
        args.putInt(NAVIGATION_DRAWER_POSITION, position);
        fragmentViewPagerWeek.setArguments(args);

        return fragmentViewPagerWeek;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        scheduleDataSource = new ScheduleDBDataSource(context);
        scheduleDataSource.open();
        profileDatSource = new ProfileDBDataSource(context);
        profileDatSource.open();
        schedule = new Schedule();

        View rootView = inflater.inflate(R.layout.fragment_view_pager_week, container, false);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager_fragment_view_pager_week);
        viewPager.setAdapter(new ScheduleFragmentPagerAdapter(context, getFragmentManager()));
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        {
            viewPager.setCurrentItem(6, true);
        }
        else
        {
            viewPager.setCurrentItem(calendar.get(Calendar.DAY_OF_WEEK)-2, true);
        }
        alertDialogAddSchedule = alertDialogAddSchedule();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        ((ActivityMain)activity).onSectionAttached(getArguments().getInt(NAVIGATION_DRAWER_POSITION));
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.fragment_view_pager_week, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_add_schedule_fragment_schedule:
            {
                if(profileDatSource.getProfileList().size() == 0)
                {
                    Toast.makeText(context, "Please create a profile first", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                    {
                        this.dayOfWeek = 6;
                    }
                    else
                    {
                        this.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-2;
                    }

                    alertDialogAddSchedule.show();
                }

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertDialogAddSchedule()
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_create_edit_schedule, null);
        final Spinner spinner = (Spinner)view.findViewById(R.id.spinner_profilelist_alertdialog_create_edit_schedule);
        final TimePicker startTimePicker = (TimePicker)view.findViewById(R.id.timePicker_start_alertdialog_create_edit_schedule);
        final TimePicker endTimePicker = (TimePicker)view.findViewById(R.id.timePicker_end_alertdialog_create_edit_schedule);

        ProfileListViewAdapter profileListViewAdapter = new ProfileListViewAdapter(context, profileDatSource.getProfileList());
        spinner.setAdapter(profileListViewAdapter);
        spinner.setOnItemSelectedListener(this);
        startTimePicker.setIs24HourView(DateFormat.is24HourFormat(context));
        endTimePicker.setIs24HourView(DateFormat.is24HourFormat(context));
        startTimePicker.setOnTimeChangedListener(this);
        endTimePicker.setOnTimeChangedListener(this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                schedule.PROFILE_NAME = profileDatSource.getProfileList().get(spinnerPosition).PROFILE_NAME;
                schedule.PROFILE_ICON = profileDatSource.getProfileList().get(spinnerPosition).PROFILE_ICON;
                schedule.START_HOUR = startTimeHour;
                schedule.START_MINUTE = startTimeMinute;
                schedule.END_HOUR = endTimeHour;
                schedule.END_MINUTE = endTimeMinute;
                schedule.DAY_OF_WEEK = dayOfWeek;
                scheduleDataSource.createSchedule(schedule);

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

    @Override
    public void onTimeChanged(TimePicker timePicker, int hour, int minute)
    {
        switch(timePicker.getId())
        {
            case R.id.timePicker_start_alertdialog_create_edit_schedule:
            {
                startTimeHour = hour;
                startTimeMinute = minute;

                break;
            }
            case R.id.timePicker_end_alertdialog_create_edit_schedule:
            {
                endTimeHour = hour;
                endTimeMinute = minute;
                break;
            }
        }
    }
}

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

public class FragmentViewPagerWeek extends Fragment implements AdapterView.OnItemSelectedListener
{
    private static final String NAVIGATION_DRAWER_POSITION = "position";
    private static Context context;
    private Calendar calendar = Calendar.getInstance();
    private ViewPager viewPager;
    private ScheduleFragmentPagerAdapter scheduleFragmentPagerAdapter;
    private AlertDialog alertDialogAddSchedule;
    private ScheduleDBDataSource scheduleDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
    private Schedule oldSchedule;
    private Spinner spinner;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private int spinnerPosition = 0;

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
        scheduleDBDataSource = new ScheduleDBDataSource(context);
        scheduleDBDataSource.open();
        profileDBDataSource = new ProfileDBDataSource(context);
        profileDBDataSource.open();
        oldSchedule = new Schedule();
        alertDialogAddSchedule = alertDialogAddSchedule();

        View rootView = inflater.inflate(R.layout.fragment_view_pager_week, container, false);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager_fragment_view_pager_week);
        scheduleFragmentPagerAdapter = new ScheduleFragmentPagerAdapter(context, getFragmentManager());
        viewPager.setAdapter(scheduleFragmentPagerAdapter);
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        {
            viewPager.setCurrentItem(6, true);
        }
        else
        {
            viewPager.setCurrentItem(calendar.get(Calendar.DAY_OF_WEEK)-2, true);
        }
        viewPager.setOffscreenPageLimit(1);


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

        scheduleDBDataSource.open();
        profileDBDataSource.open();
    }

    public void onPause()
    {
        super.onPause();

        scheduleDBDataSource.close();
        profileDBDataSource.close();
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
                if(profileDBDataSource.getProfileList().size() == 0)
                {
                    Toast.makeText(context, "Please create a profile first", Toast.LENGTH_SHORT).show();
                }
                else
                {
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
        spinner = (Spinner)view.findViewById(R.id.spinner_profilelist_alertdialog_create_edit_schedule);
        startTimePicker = (TimePicker)view.findViewById(R.id.timePicker_start_alertdialog_create_edit_schedule);
        endTimePicker = (TimePicker)view.findViewById(R.id.timePicker_end_alertdialog_create_edit_schedule);

        ProfileListViewAdapter profileListViewAdapter = new ProfileListViewAdapter(context, profileDBDataSource.getProfileList());
        spinner.setAdapter(profileListViewAdapter);
        spinner.setOnItemSelectedListener(this);
        startTimePicker.setIs24HourView(DateFormat.is24HourFormat(context));
        endTimePicker.setIs24HourView(DateFormat.is24HourFormat(context));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                oldSchedule.PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerPosition).PROFILE_NAME;
                oldSchedule.PROFILE_ICON = profileDBDataSource.getProfileList().get(spinnerPosition).PROFILE_ICON;
                oldSchedule.START_HOUR = startTimePicker.getCurrentHour();
                oldSchedule.START_MINUTE = startTimePicker.getCurrentMinute();
                oldSchedule.END_HOUR = endTimePicker.getCurrentHour();
                oldSchedule.END_MINUTE = endTimePicker.getCurrentMinute();
                oldSchedule.DAY_OF_WEEK = viewPager.getCurrentItem();

                switch(scheduleDBDataSource.checkIfValidSchedule(oldSchedule))
                {
                    case 0:
                    {
                        scheduleDBDataSource.createSchedule(oldSchedule);
                        scheduleFragmentPagerAdapter.notifyDataSetChanged();

                        dialogInterface.cancel();
                        break;
                    }
                    case 1:
                    {
                        Toast.makeText(context, "Schedule not saved. End time cannot be earlier than start time.", Toast.LENGTH_SHORT).show();

                        dialogInterface.cancel();
                        break;
                    }
                    case 2:
                    {
                        Toast.makeText(context, "Schedule not saved. One or more existing schedules overlap with this one.", Toast.LENGTH_SHORT).show();

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

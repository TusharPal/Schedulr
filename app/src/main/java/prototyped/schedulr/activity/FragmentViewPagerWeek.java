package prototyped.schedulr.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
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
    private final String dayNames[] = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private static Context context;
    private Calendar calendar = Calendar.getInstance();
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private ScheduleFragmentPagerAdapter scheduleFragmentPagerAdapter;
    private AlertDialog alertDialogAddSchedule;
    private ScheduleDBDataSource scheduleDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
    private Schedule newSchedule;
    private Spinner spinner;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private CheckBox checkBoxDay[] = new CheckBox[7];
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

        View rootView = inflater.inflate(R.layout.fragment_view_pager_week, container, false);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager_fragment_view_pager_week);
        pagerTabStrip = (PagerTabStrip)rootView.findViewById(R.id.pagertabstrip_fragment_view_pager_week);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColorResource(android.R.color.holo_blue_dark);
        scheduleFragmentPagerAdapter = new ScheduleFragmentPagerAdapter(context, getFragmentManager());
        viewPager.setAdapter(scheduleFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY?6:(calendar.get(Calendar.DAY_OF_WEEK)-2), true);

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
        viewPager.setCurrentItem(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY?6:(calendar.get(Calendar.DAY_OF_WEEK)-2), true);
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
                    alertDialogAddSchedule = alertDialogAddSchedule();
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
        checkBoxDay[0] = (CheckBox)view.findViewById(R.id.checkBox_monday_alertdialog_create_edit_schedule);
        checkBoxDay[1] = (CheckBox)view.findViewById(R.id.checkBox_tuesday_alertdialog_create_edit_schedule);
        checkBoxDay[2] = (CheckBox)view.findViewById(R.id.checkBox_wednesday_alertdialog_create_edit_schedule);
        checkBoxDay[3] = (CheckBox)view.findViewById(R.id.checkBox_thursday_alertdialog_create_edit_schedule);
        checkBoxDay[4] = (CheckBox)view.findViewById(R.id.checkBox_friday_alertdialog_create_edit_schedule);
        checkBoxDay[5] = (CheckBox)view.findViewById(R.id.checkBox_saturday_alertdialog_create_edit_schedule);
        checkBoxDay[6] = (CheckBox)view.findViewById(R.id.checkBox_sunday_alertdialog_create_edit_schedule);
        checkBoxDay[viewPager.getCurrentItem()].setChecked(true);

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
                newSchedule = new Schedule();
                newSchedule.PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerPosition).PROFILE_NAME;
                newSchedule.PROFILE_ICON = profileDBDataSource.getProfileList().get(spinnerPosition).PROFILE_ICON;
                newSchedule.START_HOUR = startTimePicker.getCurrentHour();
                newSchedule.START_MINUTE = startTimePicker.getCurrentMinute();
                newSchedule.END_HOUR = endTimePicker.getCurrentHour();
                newSchedule.END_MINUTE = endTimePicker.getCurrentMinute();
                newSchedule.DAY_OF_WEEK = viewPager.getCurrentItem();

                boolean itemChecked[] = new boolean[7];
                for(int index=0; index<7; index++)
                {
                    itemChecked[index] = checkBoxDay[index].isChecked();
                }

                switch(scheduleDBDataSource.checkIfValidSchedule(newSchedule, itemChecked))
                {
                    case 0:
                    {
                        for(int dayOfWeek=0; dayOfWeek<7; dayOfWeek++)
                        {
                            if(itemChecked[dayOfWeek])
                            {
                                newSchedule.DAY_OF_WEEK = dayOfWeek;
                                scheduleDBDataSource.createSchedule(newSchedule);
                            }
                        }
                        scheduleFragmentPagerAdapter.notifyDataSetChanged();

                        Intent serviceIntent = new Intent(context, ServiceProfileScheduler.class);
                        serviceIntent.putExtra("cancel_alarms", true);
                        context.startService(serviceIntent);

                        dialogInterface.cancel();
                        break;
                    }
                    case 1:
                    {
                        Toast.makeText(context, "Schedule not saved. Start time cannot be later than or equal to end time.", Toast.LENGTH_SHORT).show();

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

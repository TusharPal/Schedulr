package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ProfileListViewAdapter;
import prototyped.schedulr.database.ProfileDBDataSource;
import prototyped.schedulr.database.Schedule;
import prototyped.schedulr.database.ScheduleDBDataSource;

public class ActivityScheduleEditor extends ActionBarActivity implements View.OnClickListener
{
    private Schedule oldSchedule;
    private Schedule newSchedule;
    private ScheduleDBDataSource scheduleDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
    private Calendar calendar;

    private Spinner spinnerProfiles;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private CheckBox checkBoxDays[] = new CheckBox[7];
    private TimePicker timePicker;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_editor);

        spinnerProfiles = (Spinner)findViewById(R.id.spinner_profiles_activity_schedule_editor);
        editTextStartTime = (EditText)findViewById(R.id.editText_start_time_activity_schedule_editor);
        editTextEndTime = (EditText)findViewById(R.id.editText_end_time_activity_schedule_editor);
        checkBoxDays[0] = (CheckBox)findViewById(R.id.checkBox_monday_activity_schedule_editor);
        checkBoxDays[1] = (CheckBox)findViewById(R.id.checkBox_tuesday_activity_schedule_editor);
        checkBoxDays[2] = (CheckBox)findViewById(R.id.checkBox_wednesday_activity_schedule_editor);
        checkBoxDays[3] = (CheckBox)findViewById(R.id.checkBox_thursday_activity_schedule_editor);
        checkBoxDays[4] = (CheckBox)findViewById(R.id.checkBox_friday_activity_schedule_editor);
        checkBoxDays[5] = (CheckBox)findViewById(R.id.checkBox_saturday_activity_schedule_editor);
        checkBoxDays[6] = (CheckBox)findViewById(R.id.checkBox_sunday_activity_schedule_editor);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        scheduleDBDataSource = new ScheduleDBDataSource(this);
        scheduleDBDataSource.open();
        profileDBDataSource = new ProfileDBDataSource(this);
        profileDBDataSource.open();

        if(getIntent().getExtras().getBoolean("new_schedule"))
        {
            newSchedule = new Schedule();
            calendar.set(Calendar.MINUTE, 0);
            newSchedule.START_HOUR = calendar.get(Calendar.HOUR_OF_DAY);
            newSchedule.START_MINUTE = calendar.get(Calendar.MINUTE);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            newSchedule.END_HOUR = calendar.get(Calendar.HOUR_OF_DAY);
            newSchedule.END_MINUTE = calendar.get(Calendar.MINUTE);

            spinnerProfiles.setAdapter(new ProfileListViewAdapter(this, profileDBDataSource.getProfileList()));
            editTextStartTime.setHint(getTimeStamp(newSchedule.START_HOUR, newSchedule.START_MINUTE));
            editTextEndTime.setHint(getTimeStamp(newSchedule.END_HOUR, newSchedule.END_MINUTE));
            editTextStartTime.setOnClickListener(this);
            editTextEndTime.setOnClickListener(this);
            checkBoxDays[getIntent().getExtras().getInt("current_day")].setChecked(true);
        }
        else
        {
            oldSchedule = scheduleDBDataSource.getSchedule(getIntent().getExtras().getLong("schedule_id"));
            newSchedule = oldSchedule;

            spinnerProfiles.setAdapter(new ProfileListViewAdapter(this, profileDBDataSource.getProfileList()));
            spinnerProfiles.setSelection(profileDBDataSource.getProfilePosition(oldSchedule.PROFILE_NAME));
            editTextStartTime.setHint(getTimeStamp(oldSchedule.START_HOUR, oldSchedule.START_MINUTE));
            editTextEndTime.setHint(getTimeStamp(oldSchedule.END_HOUR, oldSchedule.END_MINUTE));
            editTextStartTime.setOnClickListener(this);
            editTextEndTime.setOnClickListener(this);
            checkBoxDays[0].setChecked(oldSchedule.MONDAY > 0 ? true : false);
            checkBoxDays[1].setChecked(oldSchedule.TUESDAY > 0 ? true : false);
            checkBoxDays[2].setChecked(oldSchedule.WEDNESDAY > 0 ? true : false);
            checkBoxDays[3].setChecked(oldSchedule.THURSDAY > 0 ? true : false);
            checkBoxDays[4].setChecked(oldSchedule.FRIDAY > 0 ? true : false);
            checkBoxDays[5].setChecked(oldSchedule.SATURDAY > 0 ? true : false);
            checkBoxDays[6].setChecked(oldSchedule.SUNDAY > 0 ? true : false);
        }
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
        profileDBDataSource.open();
    }

    @Override
    public void onBackPressed()
    {
        alertDialogSave().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_schedule_editor, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.editText_start_time_activity_schedule_editor:
            {
                alertDialogStartTime().show();

                break;
            }
            case R.id.editText_end_time_activity_schedule_editor:
            {
                alertDialogEndTime().show();

                break;
            }
        }
    }

    private String getTimeStamp(int hour, int minute)
    {
        String s= "";

        if(android.text.format.DateFormat.is24HourFormat(this))
        {
            if(hour == 0)
            {
                s += "00 : ";
            }
            else if(hour>0 && hour<10)
            {
                s += "0" + hour + " : ";
            }
            else
            {
                s += hour + " : ";
            }

            if(minute == 0)
            {
                s += "00";
            }
            else if(minute >0 && minute<10)
            {
                s += "0" + minute;
            }
            else
            {
                s += minute;
            }
        }
        else
        {
            if(hour == 0)
            {
                s += "12 : ";
            }
            else if(hour > 0 && hour < 13)
            {
                s += hour + " : ";
            }
            else
            {
                s += (hour - 12) + " : ";
            }

            if(minute == 0)
            {
                s += "00";
            }
            else if(minute > 0 && minute < 10)
            {
                s += "0" + minute;
            }
            else
            {
                s += minute;
            }

            if(hour<12)
            {
                s += " AM";
            }
            else
            {
                s += " PM";
            }
        }

        return s;
    }

    private AlertDialog alertDialogStartTime()
    {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_timepicker, null);
        timePicker = (TimePicker)view.findViewById(R.id.timePicker_alertdialog_timepicker);
        timePicker.setIs24HourView(android.text.format.DateFormat.is24HourFormat(getApplicationContext()));
        timePicker.setCurrentHour(newSchedule.START_HOUR);
        timePicker.setCurrentMinute(newSchedule.START_MINUTE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                newSchedule.START_HOUR = timePicker.getCurrentHour();
                newSchedule.START_MINUTE = timePicker.getCurrentMinute();
                editTextStartTime.setHint(getTimeStamp(newSchedule.START_HOUR, newSchedule.START_MINUTE));

                dialogInterface.cancel();
            }
        });

        return alertDialogBuilder.create();
    }

    private AlertDialog alertDialogEndTime()
    {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_timepicker, null);
        timePicker = (TimePicker)view.findViewById(R.id.timePicker_alertdialog_timepicker);
        timePicker.setIs24HourView(android.text.format.DateFormat.is24HourFormat(getApplicationContext()));
        timePicker.setCurrentHour(newSchedule.END_HOUR);
        timePicker.setCurrentMinute(newSchedule.END_MINUTE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                newSchedule.END_HOUR = timePicker.getCurrentHour();
                newSchedule.END_MINUTE = timePicker.getCurrentMinute();
                editTextEndTime.setHint(getTimeStamp(newSchedule.END_HOUR, newSchedule.END_MINUTE));

                dialogInterface.cancel();
            }
        });

        return alertDialogBuilder.create();
    }

    private AlertDialog alertDialogSave()
    {
        newSchedule.PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerProfiles.getSelectedItemPosition()).PROFILE_NAME;
        newSchedule.PROFILE_ICON = profileDBDataSource.getProfileList().get(spinnerProfiles.getSelectedItemPosition()).PROFILE_ICON;
        newSchedule.MONDAY = checkBoxDays[0].isChecked()?1:0;
        newSchedule.TUESDAY = checkBoxDays[1].isChecked()?1:0;
        newSchedule.WEDNESDAY = checkBoxDays[2].isChecked()?1:0;
        newSchedule.THURSDAY = checkBoxDays[3].isChecked()?1:0;
        newSchedule.FRIDAY = checkBoxDays[4].isChecked()?1:0;
        newSchedule.SATURDAY = checkBoxDays[5].isChecked()?1:0;
        newSchedule.SUNDAY = checkBoxDays[6].isChecked()?1:0;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if(getIntent().getExtras().getBoolean("new_schedule"))
        {
            alertDialogBuilder.setMessage("Save this schedule?");
        }
        else
        {
            alertDialogBuilder.setMessage("Save changes to this schedule?");
        }
        alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                switch(scheduleDBDataSource.checkIfValidSchedule(newSchedule))
                {
                    case 0:
                    {
                        if(getIntent().getExtras().getBoolean("new_schedule"))
                        {
                            scheduleDBDataSource.createSchedule(newSchedule);
                        }
                        else
                        {
                            scheduleDBDataSource.updateSchedule(newSchedule);
                        }

                        break;
                    }
                    case 1:
                    {
                        Toast.makeText(getApplicationContext(), "Schedule start time cannot be after its end time.", Toast.LENGTH_LONG).show();

                        break;
                    }
                    case 2:
                    {
                        Toast.makeText(getApplicationContext(), "This schedule is clashing with an existing schedule.", Toast.LENGTH_LONG).show();

                        break;
                    }
                }
                dialogInterface.cancel();

                Intent intent = new Intent(getApplicationContext(), ServiceProfileScheduler.class);
                startService(intent);

                intent = new Intent(getApplicationContext(), ActivityMain.class);
                intent.putExtra("fragment_number", 0);
                startActivity(intent);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                dialogInterface.cancel();

                Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                intent.putExtra("fragment_number", 0);
                startActivity(intent);
                finish();
            }
        });

        return alertDialogBuilder.create();
    }
}

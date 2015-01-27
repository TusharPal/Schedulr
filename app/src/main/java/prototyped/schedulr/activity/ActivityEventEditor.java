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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import prototyped.schedulr.R;
import prototyped.schedulr.adapter.ProfileListViewAdapter;
import prototyped.schedulr.database.Event;
import prototyped.schedulr.database.EventDBDataSource;
import prototyped.schedulr.database.ProfileDBDataSource;

public class ActivityEventEditor extends ActionBarActivity implements View.OnClickListener
{
    private EventDBDataSource eventDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
    private Event oldEvent;
    private Event newEvent;
    private Calendar calendar;

    private EditText editTextTitle;
    private EditText editTextNote;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextDate;
    private Spinner spinnerProfiles;
    private TimePicker timePicker;
    private DatePicker datePicker;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_editor);

        eventDBDataSource = new EventDBDataSource(this);
        eventDBDataSource.open();
        profileDBDataSource = new ProfileDBDataSource(this);
        profileDBDataSource.open();
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        editTextTitle = (EditText)findViewById(R.id.editText_title_activity_event_editor);
        editTextNote = (EditText)findViewById(R.id.editText_note_activity_event_editor);
        editTextStartTime = (EditText)findViewById(R.id.editText_start_time_activity_event_editor);
        editTextEndTime = (EditText)findViewById(R.id.editText_end_time_activity_event_editor);
        editTextDate = (EditText)findViewById(R.id.editText_date_activity_event_editor);
        spinnerProfiles = (Spinner)findViewById(R.id.spinner_profiles_activity_event_editor);
        spinnerProfiles.setAdapter(new ProfileListViewAdapter(this, profileDBDataSource.getProfileList()));
        editTextStartTime.setOnClickListener(this);
        editTextEndTime.setOnClickListener(this);
        editTextDate.setOnClickListener(this);

        if(getIntent().getExtras().getBoolean("new_event"))
        {
            newEvent = new Event();
            newEvent.DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
            newEvent.MONTH = calendar.get(Calendar.MONTH);
            newEvent.YEAR = calendar.get(Calendar.YEAR);
            newEvent.START_HOUR = calendar.get(Calendar.HOUR_OF_DAY);
            newEvent.START_MINUTE = 0;
            calendar.add(calendar.HOUR_OF_DAY, 1);
            newEvent.END_HOUR = calendar.get(Calendar.HOUR_OF_DAY);
            newEvent.END_MINUTE = 0;

            editTextStartTime.setHint(getTimeStamp(newEvent.START_HOUR, newEvent.START_MINUTE));
            editTextEndTime.setHint(getTimeStamp(newEvent.END_HOUR, newEvent.END_MINUTE));
            editTextDate.setHint(getDateStamp(newEvent.DAY_OF_MONTH, newEvent.MONTH, newEvent.YEAR));
        }
        else
        {
            oldEvent = eventDBDataSource.getEvent(getIntent().getExtras().getLong("event_id"));
            newEvent = oldEvent;

            spinnerProfiles.setSelection(profileDBDataSource.getProfilePosition(newEvent.PROFILE_NAME));
            editTextTitle.setText(newEvent.TITLE);
            editTextNote.setText(newEvent.NOTE);
            editTextStartTime.setHint(getTimeStamp(newEvent.START_HOUR, newEvent.START_MINUTE));
            editTextEndTime.setHint(getTimeStamp(newEvent.END_HOUR, newEvent.END_MINUTE));
            editTextDate.setHint(getDateStamp(newEvent.DAY_OF_MONTH, newEvent.MONTH, newEvent.YEAR));
        }
    }

    public void onResume()
    {
        super.onResume();

        eventDBDataSource.open();
        profileDBDataSource.open();
    }

    public void onPause()
    {
        super.onPause();

        eventDBDataSource.close();
        profileDBDataSource.close();
    }

    public void onBackPressed()
    {
        if(!editTextTitle.getText().toString().equals(""))
        {
            alertDialogSave().show();
        }
        else
        {
            if(getIntent().getExtras().getBoolean("new_event"))
            {
                Toast.makeText(this, "Empty event discarded", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Changes to event discarded", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(this, ActivityMain.class);
            intent.putExtra("fragment_number", 1);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_event_editor, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_save_activity_event_editor:
            {
                if(!editTextTitle.getText().toString().equals(""))
                {
                    switch(eventDBDataSource.checkIfEventValid(newEvent))
                    {
                        case 0:
                        {
                            if(getIntent().getExtras().getBoolean("new_event"))
                            {
                                newEvent.TITLE = editTextTitle.getText().toString();
                                newEvent.NOTE = editTextNote.getText().toString();
                                newEvent.PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerProfiles.getSelectedItemPosition()).PROFILE_NAME;
                                eventDBDataSource.createEvent(newEvent);

                                Intent intentService = new Intent(this, ServiceProfileScheduler.class);
                                startService(intentService);

                                Intent intent = new Intent(this, ActivityMain.class);
                                intent.putExtra("fragment_number", 1);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                eventDBDataSource.editEvent(newEvent);

                                Intent intentService = new Intent(this, ServiceProfileScheduler.class);
                                startService(intentService);

                                Intent intent = new Intent(this, ActivityMain.class);
                                intent.putExtra("fragment_number", 1);
                                startActivity(intent);
                                finish();
                            }

                            break;
                        }
                        case 1:
                        {
                            Toast.makeText(this, "Start time cannot be after end time.", Toast.LENGTH_LONG).show();

                            break;
                        }
                        case 2:
                        {
                            Toast.makeText(this, "Event is clashing with an existing event.", Toast.LENGTH_LONG).show();

                            break;
                        }
                        case 3:
                        {
                            Toast.makeText(getApplicationContext(), "Event start time has already elapsed.", Toast.LENGTH_LONG).show();

                            break;
                        }
                    }
                }
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.editText_start_time_activity_event_editor:
            {
                alertDialogStartTime().show();

                break;
            }
            case R.id.editText_end_time_activity_event_editor:
            {
                alertDialogEndTime().show();

                break;
            }
            case R.id.editText_date_activity_event_editor:
            {
                alertDialogDate().show();

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

    private String getDateStamp(int dayOfMonth, int month, int year)
    {
        String s = "";
        String monthNames[] = {"Jan", "Feb", "Mar", "Apr", "May", "June",
                                "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

        if(dayOfMonth%10 == 1)
        {
            s= s + dayOfMonth + "st " + monthNames[month] + ", " + year;
        }
        else if(dayOfMonth%10 == 2)
        {
            s= s + dayOfMonth + "nd " + monthNames[month] + ", " + year;
        }
        else if(dayOfMonth%10 == 3)
        {
            s= s + dayOfMonth + "rd " + monthNames[month] + ", " + year;
        }
        else
        {
            s= s + dayOfMonth + "th " + monthNames[month] + ", " + year;
        }

        return  s;
    }

    private AlertDialog alertDialogStartTime()
    {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_timepicker, null);
        timePicker = (TimePicker)view.findViewById(R.id.timePicker_alertdialog_timepicker);
        timePicker.setIs24HourView(android.text.format.DateFormat.is24HourFormat(getApplicationContext()));
        timePicker.setCurrentHour(newEvent.START_HOUR);
        timePicker.setCurrentMinute(newEvent.START_MINUTE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                newEvent.START_HOUR = timePicker.getCurrentHour();
                newEvent.START_MINUTE = timePicker.getCurrentMinute();
                editTextStartTime.setHint(getTimeStamp(newEvent.START_HOUR, newEvent.START_MINUTE));

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
        timePicker.setCurrentHour(newEvent.END_HOUR);
        timePicker.setCurrentMinute(newEvent.END_MINUTE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                newEvent.END_HOUR = timePicker.getCurrentHour();
                newEvent.END_MINUTE = timePicker.getCurrentMinute();
                editTextEndTime.setHint(getTimeStamp(newEvent.END_HOUR, newEvent.END_MINUTE));

                dialogInterface.cancel();
            }
        });

        return alertDialogBuilder.create();
    }

    private AlertDialog alertDialogDate()
    {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_datepicker, null);
        datePicker = (DatePicker)view.findViewById(R.id.datePicker_alertdialog_datepicker);
        datePicker.updateDate(newEvent.YEAR, newEvent.MONTH, newEvent.DAY_OF_MONTH);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                newEvent.DAY_OF_MONTH = datePicker.getDayOfMonth();
                newEvent.MONTH = datePicker.getMonth();
                newEvent.YEAR = datePicker.getYear();
                editTextDate.setHint(getDateStamp(newEvent.DAY_OF_MONTH, newEvent.MONTH, newEvent.YEAR));

                dialogInterface.cancel();
            }
        });

        return alertDialogBuilder.create();
    }

    private AlertDialog alertDialogSave()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if(getIntent().getExtras().getBoolean("new_event"))
        {
            alertDialogBuilder.setMessage("Save this event?");
        }
        else
        {
            alertDialogBuilder.setMessage("Save changes to this event?");
        }
        alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                switch(eventDBDataSource.checkIfEventValid(newEvent))
                {
                    case 0:
                    {
                        if(getIntent().getExtras().getBoolean("new_event"))
                        {
                            newEvent.TITLE = editTextTitle.getText().toString();
                            newEvent.NOTE = editTextNote.getText().toString();
                            newEvent.PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerProfiles.getSelectedItemPosition()).PROFILE_NAME;
                            eventDBDataSource.createEvent(newEvent);

                            Intent intentService = new Intent(getApplicationContext(), ServiceProfileScheduler.class);
                            startService(intentService);

                            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                            intent.putExtra("fragment_number", 1);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            newEvent.TITLE = editTextTitle.getText().toString();
                            newEvent.NOTE = editTextNote.getText().toString();
                            newEvent.PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerProfiles.getSelectedItemPosition()).PROFILE_NAME;
                            eventDBDataSource.editEvent(newEvent);

                            Intent intentService = new Intent(getApplicationContext(), ServiceProfileScheduler.class);
                            startService(intentService);

                            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                            intent.putExtra("fragment_number", 1);
                            startActivity(intent);
                            finish();
                        }

                        break;
                    }
                    case 1:
                    {
                        Toast.makeText(getApplicationContext(), "Event start time cannot be after its end time.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        intent.putExtra("fragment_number", 1);
                        startActivity(intent);
                        finish();

                        break;
                    }
                    case 2:
                    {
                        Toast.makeText(getApplicationContext(), "This event is clashing with an existing event.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        intent.putExtra("fragment_number", 1);
                        startActivity(intent);
                        finish();

                        break;
                    }
                    case 3:
                    {
                        Toast.makeText(getApplicationContext(), "Event start time has already elapsed.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        intent.putExtra("fragment_number", 1);
                        startActivity(intent);
                        finish();

                        break;
                    }
                }

                dialogInterface.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                dialogInterface.cancel();

                Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                intent.putExtra("fragment_number", 1);
                startActivity(intent);
                finish();
            }
        });

        return alertDialogBuilder.create();
    }
}

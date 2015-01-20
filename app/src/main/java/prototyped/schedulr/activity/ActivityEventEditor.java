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
import android.widget.AdapterView;
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

public class ActivityEventEditor extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener
{
    private EventDBDataSource eventDBDataSource;
    private ProfileDBDataSource profileDBDataSource;
    private Event event;
    private Calendar calendar;
    private EditText editTextTitle;
    private EditText editTextNote;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextDate;
    private Spinner spinnerProfiles;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private int spinnerProfilePosition = 0;

    private boolean isSet[] = {false, false, false};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_editor);

        eventDBDataSource = new EventDBDataSource(this);
        eventDBDataSource.open();
        profileDBDataSource = new ProfileDBDataSource(this);
        profileDBDataSource.open();
        calendar = Calendar.getInstance();

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
        spinnerProfiles.setOnItemSelectedListener(this);

        if(!getIntent().getExtras().getBoolean("new_event"))
        {
            event = eventDBDataSource.getEvent(getIntent().getExtras().getLong("event_id"));
            editTextTitle.setText(event.EVENT_TITLE);
            editTextNote.setText(event.EVENT_NOTE);
            editTextStartTime.setHint(getTimeStamp(event.EVENT_START_HOUR, event.EVENT_START_MINUTE));
            editTextEndTime.setHint(getTimeStamp(event.EVENT_END_HOUR, event.EVENT_END_MINUTE));
            editTextDate.setHint(getDateStamp(event.EVENT_DAY_OF_MONTH, event.EVENT_MONTH, event.EVENT_YEAR));
            isSet[0] = true;
            isSet[1] = true;
            isSet[2] = true;
        }
        else
        {
            event = new Event();
            editTextStartTime.setHint(getTimeStamp(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
            calendar.add(calendar.HOUR_OF_DAY, 1);
            editTextEndTime.setHint(getTimeStamp(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
            editTextDate.setHint(getDateStamp(Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.MONTH)+1, Calendar.getInstance().get(Calendar.YEAR)));
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
        if(editTextTitle.getText().toString().trim().length() != 0 && isSet[0] && isSet[1] && isSet[2])
        {
            alertDialogSave().show();
        }
        else
        {
            if(getIntent().getExtras().getBoolean("new_event"))
            {
                Toast.makeText(this, "Empty event discarded", Toast.LENGTH_SHORT).show();
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
                if(verifyFieldsFilled())
                {
                    switch(eventDBDataSource.checkIfEventValid(event))
                    {
                        case 0:
                        {
                            if(getIntent().getExtras().getBoolean("new_event"))
                            {
                                eventDBDataSource.createEvent(event);

                                Intent intentService = new Intent(this, ServiceProfileScheduler.class);
                                intentService.putExtra("cancel_alarms", true);
                                startService(intentService);

                                Intent intent = new Intent(this, ActivityMain.class);
                                intent.putExtra("fragment_number", 1);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                eventDBDataSource.editEvent(event);

                                Intent intentService = new Intent(this, ServiceProfileScheduler.class);
                                intentService.putExtra("cancel_alarms", true);
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
                            Toast.makeText(this, "Start time cannot be after end time.", Toast.LENGTH_SHORT).show();

                            break;
                        }
                        case 2:
                        {
                            Toast.makeText(this, "Event is clashing with an existing event.", Toast.LENGTH_SHORT).show();

                            break;
                        }
                        case 3:
                        {
                            Toast.makeText(getApplicationContext(), "Event start time has already elapsed.", Toast.LENGTH_SHORT).show();

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
    {
        this.spinnerProfilePosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

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

    private boolean verifyFieldsFilled()
    {
        if(editTextTitle.getText().toString().trim().length() == 0)
        {
            Toast.makeText(this, "Please enter a title.", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(!isSet[0])
        {
            Toast.makeText(this, "Please set the event start time.", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(!isSet[1])
        {
            Toast.makeText(this, "Please set the event end time.", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(!isSet[2])
        {
            Toast.makeText(this, "Please set the event date.", Toast.LENGTH_SHORT).show();

            return false;
        }
        else
        {
            event.EVENT_TITLE = editTextTitle.getText().toString();
            event.EVENT_NOTE = editTextNote.getText().toString();
            event.EVENT_PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerProfilePosition).PROFILE_NAME;
        }

        return true;
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
            s= s + dayOfMonth + "st " + monthNames[month-1] + ", " + year;
        }
        else if(dayOfMonth%10 == 2)
        {
            s= s + dayOfMonth + "nd " + monthNames[month-1] + ", " + year;
        }
        else if(dayOfMonth%10 == 3)
        {
            s= s + dayOfMonth + "rd " + monthNames[month-1] + ", " + year;
        }
        else
        {
            s= s + dayOfMonth + "th " + monthNames[month-1] + ", " + year;
        }

        return  s;
    }

    private AlertDialog alertDialogStartTime()
    {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_timepicker, null);
        timePicker = (TimePicker)view.findViewById(R.id.timePicker_alertdialog_timepicker);
        timePicker.setIs24HourView(android.text.format.DateFormat.is24HourFormat(getApplicationContext()));
        if(!getIntent().getExtras().getBoolean("new_event"))
        {
            timePicker.setCurrentHour(event.EVENT_START_HOUR);
            timePicker.setCurrentMinute(event.EVENT_START_MINUTE);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                event.EVENT_START_HOUR = timePicker.getCurrentHour();
                event.EVENT_START_MINUTE = timePicker.getCurrentMinute();
                editTextStartTime.setHint(getTimeStamp(event.EVENT_START_HOUR, event.EVENT_START_MINUTE));
                isSet[0] = true;

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
        if(!getIntent().getExtras().getBoolean("new_event"))
        {
            timePicker.setCurrentHour(event.EVENT_END_HOUR);
            timePicker.setCurrentMinute(event.EVENT_END_MINUTE);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                event.EVENT_END_HOUR = timePicker.getCurrentHour();
                event.EVENT_END_MINUTE = timePicker.getCurrentMinute();
                editTextEndTime.setHint(getTimeStamp(event.EVENT_END_HOUR, event.EVENT_END_MINUTE));
                isSet[1] = true;

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
        if(!getIntent().getExtras().getBoolean("new_event"))
        {
            datePicker.updateDate(event.EVENT_YEAR, event.EVENT_MONTH, event.EVENT_DAY_OF_MONTH);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                event.EVENT_DAY_OF_MONTH = datePicker.getDayOfMonth();
                event.EVENT_MONTH = datePicker.getMonth() + 1;
                event.EVENT_YEAR = datePicker.getYear();
                editTextDate.setHint(getDateStamp(event.EVENT_DAY_OF_MONTH, event.EVENT_MONTH+1, event.EVENT_YEAR));
                isSet[2] = true;

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
                switch(eventDBDataSource.checkIfEventValid(event))
                {
                    case 0:
                    {
                        dialogInterface.cancel();

                        if(getIntent().getExtras().getBoolean("new_event"))
                        {
                            event.EVENT_TITLE = editTextTitle.getText().toString();
                            event.EVENT_NOTE = editTextNote.getText().toString();
                            event.EVENT_PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerProfilePosition).PROFILE_NAME;
                            eventDBDataSource.createEvent(event);

                            Intent intentService = new Intent(getApplicationContext(), ServiceProfileScheduler.class);
                            intentService.putExtra("cancel_alarms", true);
                            startService(intentService);

                            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                            intent.putExtra("fragment_number", 1);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            event.EVENT_TITLE = editTextTitle.getText().toString();
                            event.EVENT_NOTE = editTextNote.getText().toString();
                            event.EVENT_PROFILE_NAME = profileDBDataSource.getProfileList().get(spinnerProfilePosition).PROFILE_NAME;
                            eventDBDataSource.editEvent(event);

                            Intent intentService = new Intent(getApplicationContext(), ServiceProfileScheduler.class);
                            intentService.putExtra("cancel_alarms", true);
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
                        dialogInterface.cancel();
                        Toast.makeText(getApplicationContext(), "Event start time cannot be after its end time.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        intent.putExtra("fragment_number", 1);
                        startActivity(intent);
                        finish();

                        break;
                    }
                    case 2:
                    {
                        dialogInterface.cancel();
                        Toast.makeText(getApplicationContext(), "This event is clashing with an existing event.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        intent.putExtra("fragment_number", 1);
                        startActivity(intent);
                        finish();

                        break;
                    }
                    case 3:
                    {
                        dialogInterface.cancel();
                        Toast.makeText(getApplicationContext(), "Event start time has already elapsed.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        intent.putExtra("fragment_number", 1);
                        startActivity(intent);
                        finish();

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

                Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                intent.putExtra("fragment_number", 1);
                startActivity(intent);
                finish();
            }
        });

        return alertDialogBuilder.create();
    }
}

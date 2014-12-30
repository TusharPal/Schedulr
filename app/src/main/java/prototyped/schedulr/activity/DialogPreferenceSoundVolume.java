package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import prototyped.schedulr.R;

public class DialogPreferenceSoundVolume extends DialogPreference implements DialogInterface.OnClickListener, SeekBar.OnSeekBarChangeListener
{
    private SeekBar seekBarRingtone;
    private SeekBar seekBarApplication;
    private SeekBar seekBarAlarm;
    private int positionRingtone;
    private int positionApplication;
    private int positionAlarm;

    public DialogPreferenceSoundVolume(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_sound_volume);
    }

    protected void onPrepareDialogBuilder(AlertDialog.Builder builder)
    {
        builder.setPositiveButton("Ok", this);
        builder.setNegativeButton(null, null);

        super.onPrepareDialogBuilder(builder);
    }

    @Override
    public void onBindDialogView(View view)
    {
        seekBarRingtone = (SeekBar)view.findViewById(R.id.seekBar_ringtone_dialogpreference_sound_volume);
        seekBarApplication = (SeekBar)view.findViewById(R.id.seekBar_application_dialogpreference_sound_volume);
        seekBarAlarm = (SeekBar)view.findViewById(R.id.seekBar_alarm_dialogpreference_sound_volume);
        seekBarRingtone.setOnSeekBarChangeListener(this);
        seekBarApplication.setOnSeekBarChangeListener(this);
        seekBarAlarm.setOnSeekBarChangeListener(this);
        seekBarRingtone.setProgress(getPreferenceManager().getSharedPreferences().getInt("profile_sound_volume_ringtone", 0));
        seekBarApplication.setProgress(getPreferenceManager().getSharedPreferences().getInt("profile_sound_volume_application", 0));
        seekBarAlarm.setProgress(getPreferenceManager().getSharedPreferences().getInt("profile_sound_volume_alarm", 0));

        super.onBindDialogView(view);
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {
        if(id == DialogInterface.BUTTON_POSITIVE)
        {
            getPreferenceManager().getSharedPreferences().edit().putInt("profile_sound_volume_ringtone", positionRingtone).commit();
            getPreferenceManager().getSharedPreferences().edit().putInt("profile_sound_volume_application", positionApplication).commit();
            getPreferenceManager().getSharedPreferences().edit().putInt("profile_sound_volume_alarm", positionAlarm).commit();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int position, boolean b)
    {
        switch(seekBar.getId())
        {
            case R.id.seekBar_ringtone_dialogpreference_sound_volume:
            {
                this.positionRingtone = position;

                break;
            }
            case R.id.seekBar_application_dialogpreference_sound_volume:
            {
                this.positionApplication = position;

                break;
            }
            case R.id.seekBar_alarm_dialogpreference_sound_volume:
            {
                this.positionAlarm = position;

                break;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {

    }
}

package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import prototyped.schedulr.R;

public class DialogPreferenceDisplayBrightness extends DialogPreference implements DialogInterface.OnClickListener, SeekBar.OnSeekBarChangeListener
{
    private SeekBar seekBarBrightness;
    private int position;

    public DialogPreferenceDisplayBrightness(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_display_brightness);
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
        seekBarBrightness = (SeekBar)view.findViewById(R.id.seekBar_dialogpreference_display_brightness);
        seekBarBrightness.setOnSeekBarChangeListener(this);
        seekBarBrightness.setProgress(getPreferenceManager().getSharedPreferences().getInt("profile_display_brightness", 0));

        super.onBindDialogView(view);
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {
        if(id == DialogInterface.BUTTON_POSITIVE)
        {
            getPreferenceManager().getSharedPreferences().edit().putInt("profile_display_brightness", position).commit();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int position, boolean b)
    {
        this.position = position;
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

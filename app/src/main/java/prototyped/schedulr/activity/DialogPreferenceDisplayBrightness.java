package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import prototyped.schedulr.R;

public class DialogPreferenceDisplayBrightness extends DialogPreference implements DialogInterface.OnClickListener
{
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
        SeekBar seekBarBrightness = (SeekBar)view.findViewById(R.id.seekBar_dialogpreference_display_brightness);

        super.onBindDialogView(view);
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {

        if(id == DialogInterface.BUTTON_POSITIVE)
        {
            // do your stuff to handle positive button

        }
        else if(id == DialogInterface.BUTTON_NEGATIVE)
        {
            // do your stuff to handle negative button
        }
    }
}

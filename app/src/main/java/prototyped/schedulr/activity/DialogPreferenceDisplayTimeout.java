package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import prototyped.schedulr.R;

public class DialogPreferenceDisplayTimeout extends DialogPreference implements DialogInterface.OnClickListener, NumberPicker.OnValueChangeListener
{
    private NumberPicker numberPickerMinute;
    private NumberPicker numberPickerSecond;

    public DialogPreferenceDisplayTimeout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_display_timeout);
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
        numberPickerMinute = (NumberPicker)view.findViewById(R.id.numberPicker_minute_dialogpreference_display_timeout);
        numberPickerSecond = (NumberPicker)view.findViewById(R.id.numberPicker_second_dialogpreference_display_timeout);

        numberPickerMinute.setMinValue(0);
        numberPickerSecond.setMinValue(0);
        numberPickerMinute.setMaxValue(59);
        numberPickerSecond.setMaxValue(59);
        numberPickerMinute.setWrapSelectorWheel(true);
        numberPickerSecond.setWrapSelectorWheel(true);
        numberPickerMinute.setOnValueChangedListener(this);
        numberPickerSecond.setOnValueChangedListener(this);
        super.onBindDialogView(view);
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {
        if(id == DialogInterface.BUTTON_POSITIVE)
        {
            getPreferenceManager().getSharedPreferences().edit().putInt("profile_display_timeout", (numberPickerMinute.getValue()*60)+numberPickerSecond.getValue());
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue)
    {

    }
}

package prototyped.schedulr.activity;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import prototyped.schedulr.R;

public class DialogPreferenceProfileColorPicker extends DialogPreference
{

    public DialogPreferenceProfileColorPicker(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_profile_color);
    }
}

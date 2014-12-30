package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import prototyped.schedulr.R;

public class DialogPreferenceProfileIconPicker extends DialogPreference implements View.OnClickListener
{
    private Button button[];

    public DialogPreferenceProfileIconPicker(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_icon);

        button = new Button[20];
    }

    protected void onPrepareDialogBuilder(AlertDialog.Builder builder)
    {
        builder.setPositiveButton(null, null);
        builder.setNegativeButton(null, null);

        super.onPrepareDialogBuilder(builder);
    }

    @Override
    public void onBindDialogView(View view)
    {
        super.onBindDialogView(view);

        button[0] = (Button)view.findViewById(R.id.button_01_dialogpreference_icon);
        button[1] = (Button)view.findViewById(R.id.button_02_dialogpreference_icon);
        button[2] = (Button)view.findViewById(R.id.button_03_dialogpreference_icon);
        button[3] = (Button)view.findViewById(R.id.button_04_dialogpreference_icon);
        button[4] = (Button)view.findViewById(R.id.button_05_dialogpreference_icon);
        button[5] = (Button)view.findViewById(R.id.button_06_dialogpreference_icon);
        button[6] = (Button)view.findViewById(R.id.button_07_dialogpreference_icon);
        button[7] = (Button)view.findViewById(R.id.button_08_dialogpreference_icon);
        button[8] = (Button)view.findViewById(R.id.button_09_dialogpreference_icon);
        button[9] = (Button)view.findViewById(R.id.button_10_dialogpreference_icon);
        button[10] = (Button)view.findViewById(R.id.button_11_dialogpreference_icon);
        button[11] = (Button)view.findViewById(R.id.button_12_dialogpreference_icon);
        button[12] = (Button)view.findViewById(R.id.button_13_dialogpreference_icon);
        button[13] = (Button)view.findViewById(R.id.button_14_dialogpreference_icon);
        button[14] = (Button)view.findViewById(R.id.button_15_dialogpreference_icon);
        button[15] = (Button)view.findViewById(R.id.button_16_dialogpreference_icon);
        button[16] = (Button)view.findViewById(R.id.button_17_dialogpreference_icon);
        button[17] = (Button)view.findViewById(R.id.button_18_dialogpreference_icon);
        button[18] = (Button)view.findViewById(R.id.button_19_dialogpreference_icon);
        button[19] = (Button)view.findViewById(R.id.button_20_dialogpreference_icon);

        for(int i=0; i<20; i++)
        {
            button[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_01_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_01).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_02_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_02).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_03_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_03).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_04_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_04).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_05_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_05).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_06_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_06).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_07_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_07).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_08_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_08).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_09_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_09).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_10_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_10).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_11_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_11).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_12_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_12).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_13_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_13).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_14_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_14).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_15_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_15).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_16_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_16).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_17_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_17).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_18_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_18).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_19_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_19).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_20_dialogpreference_icon:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_20).commit();
                getDialog().dismiss();

                break;
            }
        }
    }
}

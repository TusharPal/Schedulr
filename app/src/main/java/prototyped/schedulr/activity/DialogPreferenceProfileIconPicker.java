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
    private View indicatorView[];
    private int indicatorPosition;

    public DialogPreferenceProfileIconPicker(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_profile_icon_picker);

        button = new Button[20];
        indicatorView = new View[20];
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

        button[0] = (Button)view.findViewById(R.id.button_01_dialogpreference_profile_icon_picker);
        button[1] = (Button)view.findViewById(R.id.button_02_dialogpreference_profile_icon_picker);
        button[2] = (Button)view.findViewById(R.id.button_03_dialogpreference_profile_icon_picker);
        button[3] = (Button)view.findViewById(R.id.button_04_dialogpreference_profile_icon_picker);
        button[4] = (Button)view.findViewById(R.id.button_05_dialogpreference_profile_icon_picker);
        button[5] = (Button)view.findViewById(R.id.button_06_dialogpreference_profile_icon_picker);
        button[6] = (Button)view.findViewById(R.id.button_07_dialogpreference_profile_icon_picker);
        button[7] = (Button)view.findViewById(R.id.button_08_dialogpreference_profile_icon_picker);
        button[8] = (Button)view.findViewById(R.id.button_09_dialogpreference_profile_icon_picker);
        button[9] = (Button)view.findViewById(R.id.button_10_dialogpreference_profile_icon_picker);
        button[10] = (Button)view.findViewById(R.id.button_11_dialogpreference_profile_icon_picker);
        button[11] = (Button)view.findViewById(R.id.button_12_dialogpreference_profile_icon_picker);
        button[12] = (Button)view.findViewById(R.id.button_13_dialogpreference_profile_icon_picker);
        button[13] = (Button)view.findViewById(R.id.button_14_dialogpreference_profile_icon_picker);
        button[14] = (Button)view.findViewById(R.id.button_15_dialogpreference_profile_icon_picker);
        button[15] = (Button)view.findViewById(R.id.button_16_dialogpreference_profile_icon_picker);
        button[16] = (Button)view.findViewById(R.id.button_17_dialogpreference_profile_icon_picker);
        button[17] = (Button)view.findViewById(R.id.button_18_dialogpreference_profile_icon_picker);
        button[18] = (Button)view.findViewById(R.id.button_19_dialogpreference_profile_icon_picker);
        button[19] = (Button)view.findViewById(R.id.button_20_dialogpreference_profile_icon_picker);
        indicatorView[0] = view.findViewById(R.id.view_indicator_01_dialogpreference_profile_icon_picker);
        indicatorView[1] = view.findViewById(R.id.view_indicator_02_dialogpreference_profile_icon_picker);
        indicatorView[2] = view.findViewById(R.id.view_indicator_03_dialogpreference_profile_icon_picker);
        indicatorView[3] = view.findViewById(R.id.view_indicator_04_dialogpreference_profile_icon_picker);
        indicatorView[4] = view.findViewById(R.id.view_indicator_05_dialogpreference_profile_icon_picker);
        indicatorView[5] = view.findViewById(R.id.view_indicator_06_dialogpreference_profile_icon_picker);
        indicatorView[6] = view.findViewById(R.id.view_indicator_07_dialogpreference_profile_icon_picker);
        indicatorView[7] = view.findViewById(R.id.view_indicator_08_dialogpreference_profile_icon_picker);
        indicatorView[8] = view.findViewById(R.id.view_indicator_09_dialogpreference_profile_icon_picker);
        indicatorView[9] = view.findViewById(R.id.view_indicator_10_dialogpreference_profile_icon_picker);
        indicatorView[10] = view.findViewById(R.id.view_indicator_11_dialogpreference_profile_icon_picker);
        indicatorView[11] = view.findViewById(R.id.view_indicator_12_dialogpreference_profile_icon_picker);
        indicatorView[12] = view.findViewById(R.id.view_indicator_13_dialogpreference_profile_icon_picker);
        indicatorView[13] = view.findViewById(R.id.view_indicator_14_dialogpreference_profile_icon_picker);
        indicatorView[14] = view.findViewById(R.id.view_indicator_15_dialogpreference_profile_icon_picker);
        indicatorView[15] = view.findViewById(R.id.view_indicator_16_dialogpreference_profile_icon_picker);
        indicatorView[16] = view.findViewById(R.id.view_indicator_17_dialogpreference_profile_icon_picker);
        indicatorView[17] = view.findViewById(R.id.view_indicator_18_dialogpreference_profile_icon_picker);
        indicatorView[18] = view.findViewById(R.id.view_indicator_19_dialogpreference_profile_icon_picker);
        indicatorView[19] = view.findViewById(R.id.view_indicator_20_dialogpreference_profile_icon_picker);

        for(int i=0; i<20; i++)
        {
            button[i].setOnClickListener(this);
        }

        switch(getPreferenceManager().getSharedPreferences().getInt("profile_icon", R.drawable.ic_profile_01))
        {
            case R.drawable.ic_profile_01:
            {
                indicatorPosition = 1;

                break;
            }
            case R.drawable.ic_profile_02:
            {
                indicatorPosition = 2;

                break;
            }
            case R.drawable.ic_profile_03:
            {
                indicatorPosition = 3;

                break;
            }
            case R.drawable.ic_profile_04:
            {
                indicatorPosition = 4;

                break;
            }
            case R.drawable.ic_profile_05:
            {
                indicatorPosition = 5;

                break;
            }
            case R.drawable.ic_profile_06:
            {
                indicatorPosition = 6;

                break;
            }
            case R.drawable.ic_profile_07:
            {
                indicatorPosition = 7;

                break;
            }
            case R.drawable.ic_profile_08:
            {
                indicatorPosition = 8;

                break;
            }
            case R.drawable.ic_profile_09:
            {
                indicatorPosition = 9;

                break;
            }
            case R.drawable.ic_profile_10:
            {
                indicatorPosition = 10;

                break;
            }
            case R.drawable.ic_profile_11:
            {
                indicatorPosition = 11;

                break;
            }
            case R.drawable.ic_profile_12:
            {
                indicatorPosition = 12;

                break;
            }
            case R.drawable.ic_profile_13:
            {
                indicatorPosition = 13;

                break;
            }
            case R.drawable.ic_profile_14:
            {
                indicatorPosition = 14;

                break;
            }
            case R.drawable.ic_profile_15:
            {
                indicatorPosition = 15;

                break;
            }
            case R.drawable.ic_profile_16:
            {
                indicatorPosition = 16;

                break;
            }
            case R.drawable.ic_profile_17:
            {
                indicatorPosition = 17;

                break;
            }
            case R.drawable.ic_profile_18:
            {
                indicatorPosition = 18;

                break;
            }
            case R.drawable.ic_profile_19:
            {
                indicatorPosition = 19;

                break;
            }
            case R.drawable.ic_profile_20:
            {
                indicatorPosition = 20;

                break;
            }
        }
        indicatorView[indicatorPosition-1].setBackgroundResource(android.R.color.white);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_01_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_01).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_02_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_02).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_03_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_03).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_04_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_04).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_05_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_05).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_06_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_06).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_07_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_07).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_08_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_08).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_09_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_09).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_10_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_10).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_11_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_11).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_12_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_12).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_13_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_13).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_14_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_14).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_15_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_15).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_16_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_16).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_17_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_17).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_18_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_18).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_19_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_19).commit();
                getDialog().dismiss();

                break;
            }
            case R.id.button_20_dialogpreference_profile_icon_picker:
            {
                getPreferenceManager().getSharedPreferences().edit().putInt("profile_icon", R.drawable.ic_profile_20).commit();
                getDialog().dismiss();

                break;
            }
        }
    }
}

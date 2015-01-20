package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import prototyped.schedulr.R;

public class DialogPreferenceDisplayTimeout extends DialogPreference implements DialogInterface.OnClickListener
{
    private Context context;
    private final String options[] = {"10 seconds", "20 seconds", "30 seconds", "1 minute", "2 minutes", "5 minutes", "10 minutes", "20 minutes", "30 minutes", "1 hour"};
    private final int timePeriods[] = {10000, 20000, 30000, 60000, 120000, 300000, 600000, 1200000, 1800000, 3600000};
    private ListView listView;

    public DialogPreferenceDisplayTimeout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_display_timeout);

        this.context = context;
    }

    protected void onPrepareDialogBuilder(AlertDialog.Builder builder)
    {
        builder.setPositiveButton("Ok", this);
        builder.setNegativeButton(null, null);

        super.onPrepareDialogBuilder(builder);
    }

    public void onBindDialogView(View view)
    {
        listView = (ListView)view.findViewById(R.id.listView_dialogpreference_display_timeout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_single_choice, android.R.id.text1, options);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        switch(getPreferenceManager().getSharedPreferences().getInt("profile_display_timeout", 30000))
        {
            case 10000:
            {
                listView.setItemChecked(0, true);

                break;
            }
            case 20000:
            {
                listView.setItemChecked(1, true);

                break;
            }
            case 30000:
            {
                listView.setItemChecked(2, true);

                break;
            }
            case 60000:
            {
                listView.setItemChecked(3, true);

                break;
            }
            case 120000:
            {
                listView.setItemChecked(4, true);

                break;
            }
            case 300000:
            {
                listView.setItemChecked(5, true);

                break;
            }
            case 600000:
            {
                listView.setItemChecked(6, true);

                break;
            }
            case 1200000:
            {
                listView.setItemChecked(7, true);

                break;
            }
            case 1800000:
            {
                listView.setItemChecked(8, true);

                break;
            }
            case 3600000:
            {
                listView.setItemChecked(9, true);

                break;
            }
        }

        super.onBindDialogView(view);
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {
        if(id == DialogInterface.BUTTON_POSITIVE)
        {
            getPreferenceManager().getSharedPreferences().edit().putInt("profile_display_timeout", timePeriods[listView.getCheckedItemPosition()]).apply();
        }
    }
}

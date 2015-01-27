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

public class DialogPreferenceSoundRingMode extends DialogPreference implements DialogInterface.OnClickListener
{
    private Context context;
    private String options[] = {"Silent", "Vibrate", "Normal"};
    private ListView listView;

    public DialogPreferenceSoundRingMode(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_sound_ring_mode);

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
        listView = (ListView)view.findViewById(R.id.listView_dialogpreference_sound_ring_mode);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_single_choice, android.R.id.text1, options);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(getPreferenceManager().getSharedPreferences().getInt("profile_sound_ring_mode", 2), true);

        super.onBindDialogView(view);
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {
        if(id == DialogInterface.BUTTON_POSITIVE)
        {
            getPreferenceManager().getSharedPreferences().edit().putInt("profile_sound_ring_mode", listView.getCheckedItemPosition()).apply();
        }
    }
}


package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.media.RingtoneManager;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import prototyped.schedulr.R;

public class DialogPreferenceSoundRingtone extends DialogPreference
{
    private Context context;
    private RingtoneManager ringtoneManager;

    public DialogPreferenceSoundRingtone(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_sound_volume);

        this.context = context;
        ringtoneManager = new RingtoneManager(context);
    }

    protected void onPrepareDialogBuilder(AlertDialog.Builder builder)
    {
        builder.setPositiveButton("Ok", this);
        builder.setNegativeButton(null, null);

        super.onPrepareDialogBuilder(builder);
    }

    public void onBindDialogView(View view)
    {
        ListView listView = (ListView)view.findViewById(R.id.listView_dialogpreference_sound_ringtone);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_single_choice, ringtoneManager.getCursor());

        super.onBindDialogView(view);
    }
}

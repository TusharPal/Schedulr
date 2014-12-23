package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import prototyped.schedulr.R;

public class DialogPreferenceLocationMode extends DialogPreference implements DialogInterface.OnClickListener
{
    public DialogPreferenceLocationMode(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_location_mode);
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
        LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        ListView listViewProviders = (ListView)view.findViewById(R.id.listView_dialogpreference_location_mode);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice, android.R.id.text1, locationManager.getAllProviders());
        listViewProviders.setAdapter(arrayAdapter);

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

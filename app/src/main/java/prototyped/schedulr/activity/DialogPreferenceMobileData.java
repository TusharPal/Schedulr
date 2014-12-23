package prototyped.schedulr.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import prototyped.schedulr.R;

public class DialogPreferenceMobileData extends DialogPreference implements DialogInterface.OnClickListener
{
    public DialogPreferenceMobileData(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialogpreference_mobile_data_mode);
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
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        ListView listViewModes = (ListView)view.findViewById(R.id.listView_dialogpreference_mobile_data_mode);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice, android.R.id.text1, connectivityManager.getAllNetworks().toString());
//        listViewModes.setAdapter(arrayAdapter);

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

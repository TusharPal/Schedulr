package prototyped.schedulr.adapter;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import java.util.List;

import prototyped.schedulr.R;

public class RingtoneListViewAdapter extends BaseAdapter
{
    private Context context;
    private RingtoneManager ringtoneManager;
    private List<RingtoneItem> list;
    private int checkedPosition;

    public RingtoneListViewAdapter(Context context)
    {
        this.context = context;
        checkedPosition = 0;
        ringtoneManager = new RingtoneManager(context);
        Cursor cursor = ringtoneManager.getCursor();
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            RingtoneItem item = new RingtoneItem();
            item.id = cursor.getInt(RingtoneManager.ID_COLUMN_INDEX);
            item.title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            item.Uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            list.add(item);

            cursor.moveToNext();
        }
        cursor.close();
    }

    @Override
    public int getCount()
    {
        return 0;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_ringtone, parent, false);
        }
        else
        {

        }

        return view;
    }

    private class RingtoneItem
    {
        public int id = 0;
        public String title = "";
        public String Uri = "";
    }

    static class ViewHolder
    {
        RadioButton radioButton;
    }
}

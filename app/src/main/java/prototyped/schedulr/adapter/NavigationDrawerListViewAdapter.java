package prototyped.schedulr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import prototyped.schedulr.R;

public class NavigationDrawerListViewAdapter extends BaseAdapter
{
    private final int ITEM_COUNT = 4;
    private Context context;
    private final String navigationDrawerItems[] = {"Schedule", "Events", "Profiles"};

    public NavigationDrawerListViewAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return navigationDrawerItems.length;
    }

    @Override
    public Object getItem(int position)
    {
        return navigationDrawerItems[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_navigationdrawer, parent, false);
            convertView.setTag(viewHolder);

            TextView textView = (TextView)convertView.findViewById(R.id.textView_list_item_navigationdrawer);
            textView.setText(navigationDrawerItems[position]);

            viewHolder.textView = textView;
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
            TextView textView = (TextView)convertView.findViewById(R.id.textView_list_item_navigationdrawer);
            textView = viewHolder.textView;
        }

        return null;
    }

    static class ViewHolder
    {
        TextView textView;
    }
}

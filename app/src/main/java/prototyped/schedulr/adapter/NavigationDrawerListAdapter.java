package prototyped.schedulr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import prototyped.schedulr.R;

public class NavigationDrawerListAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater layoutInflater;
    private String items[];
    private int icons[] = {R.drawable.ic_profile_08, R.drawable.ic_action_event, R.drawable.ic_profile_09, R.drawable.ic_action_help};
    private int itemSelectedPosition;

    public NavigationDrawerListAdapter(Context context, String items[])
    {
        this.context = context;
        this.items = items;
        this.itemSelectedPosition = 0;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return items.length;
    }

    @Override
    public Object getItem(int position)
    {
        return items[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        View viewItemIcon;
        View viewIndicator;
        TextView textViewItemName;
        view = layoutInflater.inflate(R.layout.list_item_navigation_drawer_list, viewGroup, false);
        viewIndicator = view.findViewById(R.id.view_indicator_navigation_drawer_list);
        viewItemIcon = view.findViewById(R.id.view_icon_list_item_navigation_drawer_list);
        textViewItemName = (TextView)view.findViewById(R.id.textView_name_list_item_navigation_drawer_list);
        viewItemIcon.setBackgroundResource(icons[position]);
        textViewItemName.setText(items[position]);
        if(position == itemSelectedPosition)
        {
            viewIndicator.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }

        return view;
    }

    public void setItemSelected(int position)
    {
        this.itemSelectedPosition = position;
    }
}

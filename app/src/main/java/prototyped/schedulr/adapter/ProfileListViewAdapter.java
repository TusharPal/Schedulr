package prototyped.schedulr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.database.Profile;

public class ProfileListViewAdapter extends BaseAdapter
{
    private Context context;
    private List<Profile> list;
    private LayoutInflater layoutInflater;

    public ProfileListViewAdapter(Context context, List<Profile> list)
    {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
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
        TextView textViewIcon;
        TextView textViewName;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_item_fragment_profiles, parent, false);
            convertView.setTag(viewHolder);

            textViewIcon = (TextView)convertView.findViewById(R.id.textView_icon_list_item_fragment_profiles);
            textViewName = (TextView)convertView.findViewById(R.id.textView_name_list_item_fragment_profiles);
            textViewIcon.setBackgroundResource(list.get(position).PROFILE_ICON);
            textViewName.setText(list.get(position).PROFILE_NAME.toString());

            viewHolder.textViewIcon = textViewIcon;
            viewHolder.textViewName = textViewName;
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();

            textViewIcon = (TextView)convertView.findViewById(R.id.textView_icon_list_item_fragment_profiles);
            textViewName = (TextView)convertView.findViewById(R.id.textView_name_list_item_fragment_profiles);
            textViewIcon = viewHolder.textViewIcon;
            textViewName = viewHolder.textViewName;
        }

        return convertView;
    }

    static class ViewHolder
    {
        TextView textViewIcon;
        TextView textViewName;
    }
}

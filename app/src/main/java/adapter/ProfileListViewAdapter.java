package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

    public ProfileListViewAdapter(Context context, List<Profile> list)
    {
        this.context = context;
        this.list = list;
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

        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item_fragment_profiles, parent, false);
            convertView.setTag(viewHolder);

            TextView textViewColor = (TextView)convertView.findViewById(R.id.textView_color_list_item_fragment_profiles);
            TextView textViewIcon = (TextView)convertView.findViewById(R.id.textView_icon_list_item_fragment_profiles);
            TextView textViewName = (TextView)convertView.findViewById(R.id.textView_name_list_item_fragment_profiles);
            textViewColor.setBackgroundColor(Color.parseColor(list.get(position).PROFILE_COLOR));
            textViewIcon.setText(list.get(position).PROFILE_ICON_ID);
            textViewName.setText(list.get(position).PROFILE_NAME);

            viewHolder.textViewColor = textViewColor;
            viewHolder.textViewIcon = textViewIcon;
            viewHolder.textViewName = textViewName;
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();

            TextView textViewColor = (TextView)convertView.findViewById(R.id.textView_color_list_item_fragment_profiles);
            TextView textViewIcon = (TextView)convertView.findViewById(R.id.textView_icon_list_item_fragment_profiles);
            TextView textViewName = (TextView)convertView.findViewById(R.id.textView_name_list_item_fragment_profiles);
            textViewColor = viewHolder.textViewColor;
            textViewIcon = viewHolder.textViewIcon;
            textViewName = viewHolder.textViewName;
        }

        return convertView;
    }

    static class ViewHolder
    {
        TextView textViewColor;
        TextView textViewIcon;
        TextView textViewName;
    }
}
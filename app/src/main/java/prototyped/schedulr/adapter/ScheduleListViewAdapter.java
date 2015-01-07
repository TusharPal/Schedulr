package prototyped.schedulr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.database.Schedule;

public class ScheduleListViewAdapter extends BaseAdapter
{
    private Context context;
    private List<Schedule> list;
    private LayoutInflater inflater;

    public ScheduleListViewAdapter(Context context, List<Schedule> list)
    {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        ViewHolder holder;
        TextView textViewIcon;
        TextView textViewProfileName;
        TextView textViewStartTime;
        TextView textViewEndTime;

        if(view == null)
        {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.list_item_fragment_day_schedule, viewGroup, false);
            textViewIcon = (TextView)view.findViewById(R.id.textView_icon_list_item_fragment_day_schedule);
            textViewProfileName = (TextView)view.findViewById(R.id.textView_profile_name_list_item_fragment_day_schedule);
            textViewStartTime = (TextView)view.findViewById(R.id.textView_start_time_list_item_fragment_day_schedule);
            textViewEndTime = (TextView)view.findViewById(R.id.textView_end_time_list_item_fragment_day_schedule);

            textViewIcon.setBackgroundResource(list.get(position).PROFILE_ICON);
            textViewProfileName.setText(list.get(position).PROFILE_NAME);
            textViewStartTime.setText(list.get(position).START_TIME);
            textViewEndTime.setText(list.get(position).END_TIME);

            holder.textViewIcon = textViewIcon;
            holder.textViewProfileName = textViewProfileName;
            holder.textViewStartTime = textViewStartTime;
            holder.textViewEndTime = textViewEndTime;

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();

            textViewIcon = (TextView)view.findViewById(R.id.textView_icon_list_item_fragment_day_schedule);
            textViewProfileName = (TextView)view.findViewById(R.id.textView_profile_name_list_item_fragment_day_schedule);
            textViewStartTime = (TextView)view.findViewById(R.id.textView_start_time_list_item_fragment_day_schedule);
            textViewEndTime = (TextView)view.findViewById(R.id.textView_end_time_list_item_fragment_day_schedule);

            textViewIcon = holder.textViewIcon;
            textViewProfileName = holder.textViewProfileName;
            textViewStartTime = holder.textViewStartTime;
            textViewEndTime = holder.textViewEndTime;
        }

        return view;
    }

    static class ViewHolder
    {
        TextView textViewIcon;
        TextView textViewProfileName;
        TextView textViewStartTime;
        TextView textViewEndTime;
    }
}

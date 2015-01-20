package prototyped.schedulr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import prototyped.schedulr.R;
import prototyped.schedulr.database.Event;

public class EventListViewAdapter extends BaseAdapter
{
    private List<Event> list;
    private Context context;

    public EventListViewAdapter(Context context, List<Event> list)
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
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        ViewHolder holder;
        TextView textViewTitle;
        TextView textViewStartTime;
        TextView textViewDate;

        if(view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_fragment_events, viewGroup, false);
            textViewTitle = (TextView)view.findViewById(R.id.textView_title_list_item_fragment_events);
            textViewStartTime = (TextView)view.findViewById(R.id.textView_start_time_list_item_fragment_events);
            textViewDate = (TextView)view.findViewById(R.id.textView_date_list_item_fragment_events);

            textViewTitle.setText(list.get(position).EVENT_TITLE);
            textViewStartTime.setText(getTimeStamp(list.get(position).EVENT_START_HOUR, list.get(position).EVENT_START_MINUTE));
            textViewDate.setText(getDateStamp(list.get(position).EVENT_DAY_OF_MONTH, list.get(position).EVENT_MONTH, list.get(position).EVENT_YEAR));

            holder.textViewTitle = textViewTitle;
            holder.textViewStartTime = textViewStartTime;
            holder.textViewDate = textViewDate;

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();

            textViewTitle = (TextView)view.findViewById(R.id.textView_title_list_item_fragment_events);
            textViewStartTime = (TextView)view.findViewById(R.id.textView_start_time_list_item_fragment_events);
            textViewDate = (TextView)view.findViewById(R.id.textView_date_list_item_fragment_events);

            textViewTitle = holder.textViewTitle;
            textViewStartTime = holder.textViewStartTime;
            textViewDate = holder.textViewDate;
        }

        return view;
    }

    private String getTimeStamp(int hour, int minute)
    {
        String s= "";

        if(android.text.format.DateFormat.is24HourFormat(context))
        {
            if(hour == 0)
            {
                s += "00 : ";
            }
            else if(hour>0 && hour<10)
            {
                s += "0" + hour + " : ";
            }
            else
            {
                s += hour + " : ";
            }

            if(minute == 0)
            {
                s += "00";
            }
            else if(minute >0 && minute<10)
            {
                s += "0" + minute;
            }
            else
            {
                s += minute;
            }
        }
        else
        {
            if(hour == 0)
            {
                s += "12 : ";
            }
            else if(hour > 0 && hour < 13)
            {
                s += hour + " : ";
            }
            else
            {
                s += (hour - 12) + " : ";
            }

            if(minute == 0)
            {
                s += "00";
            }
            else if(minute > 0 && minute < 10)
            {
                s += "0" + minute;
            }
            else
            {
                s += minute;
            }

            if(hour<12)
            {
                s += " AM";
            }
            else
            {
                s += " PM";
            }
        }

        return s;
    }

    private String getDateStamp(int dayOfMonth, int month, int year)
    {
        String s = "";
        String monthNames[] = {"Jan", "Feb", "Mar", "Apr", "May", "June",
                                "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

        if(dayOfMonth%10 == 1)
        {
            s= s + dayOfMonth + "st " + monthNames[month-1] + ", " + year;
        }
        else if(dayOfMonth%10 == 2)
        {
            s= s + dayOfMonth + "nd " + monthNames[month-1] + ", " + year;
        }
        else if(dayOfMonth%10 == 3)
        {
            s= s + dayOfMonth + "rd " + monthNames[month-1] + ", " + year;
        }
        else
        {
            s= s + dayOfMonth + "th " + monthNames[month-1] + ", " + year;
        }

        return  s;
    }

    static class ViewHolder
    {
        TextView textViewTitle;
        TextView textViewStartTime;
        TextView textViewDate;
    }
}

package io.arsh.pweather;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Created by Student on October 8 2015
 */
public class DayAdapter extends BaseAdapter {

    private Context myContext;
    private Day[] myDays;

    public DayAdapter(Context context, Day[] days) {
        myContext = context;
        myDays = days;
    }

    @Override
    public int getCount() {
        return myDays.length;
    }

    @Override
    public Object getItem(int position) {
        return myDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; //we arent going to use this. It is used for taggin items for easy reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            //brand new
            convertView = LayoutInflater.from(myContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        Day day = myDays[position];

        holder.iconImageView.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");

        if (position == 0) {
            holder.dayLabel.setText("Today");
        } else {
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }

        return convertView;
    }


    /**
     * Holds the views that we add to the list item layout
     */
    private static class ViewHolder {
        ImageView iconImageView;  //public by default
        TextView temperatureLabel;
        TextView dayLabel;
    }

}
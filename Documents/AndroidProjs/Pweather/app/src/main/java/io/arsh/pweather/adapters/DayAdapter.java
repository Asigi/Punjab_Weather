package io.arsh.pweather.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import io.arsh.pweather.weather.Day;
import io.arsh.pweather.R;


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
            holder.dayLabel.setText("ਅੱਜ");
        } else {
            holder.dayLabel.setText(getTheDay(day.getDayOfTheWeek()));
        }

        return convertView;
    }

    protected String getTheDay(String theDay) {
        String punjabiDay = "";
        Log.e("DAY ADAPTER", "The day is: " + theDay);
        if (theDay.toLowerCase().equals("sunday")) {
            punjabiDay = "ਐਤਵਾਰ";
        } else if (theDay.toLowerCase().equals("monday")) {
            punjabiDay = "ਸੋਮਵਾਰ";
        } else if (theDay.toLowerCase().equals("tuesday")) {
            punjabiDay = "ਮੰਗਲਵਾਰ";
        } else if (theDay.toLowerCase().equals("wednesday")){
            punjabiDay = "ਬੁੱਧਵਾਰ";
        } else if (theDay.toLowerCase().equals("thursday")) {
            punjabiDay = "ਵੀਰਵਾਰ";
        } else if (theDay.toLowerCase().equals("friday")) {
            punjabiDay = "ਸ਼ੁੱਕਰਵਾਰ";
        } else { //saturday
            punjabiDay = "ਸ਼ਨੀਵਾਰ";
        }

        Log.e("DAY ADAPTER", "returning : " + punjabiDay);

        return punjabiDay;
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
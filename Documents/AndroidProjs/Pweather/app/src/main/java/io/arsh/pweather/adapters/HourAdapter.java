package io.arsh.pweather.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.arsh.pweather.weather.Hour;
import io.arsh.pweather.R;


/**
 * Created by Student on October 8 2015
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Hour[] myHours;
    private Context myContext;

    public HourAdapter(Context context, Hour[] hours) {
        myContext = context;
        myHours = hours;
    }


    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_list_item, parent,false);
        HourViewHolder viewHolder = new HourViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(myHours[position]);
    }

    @Override
    public int getItemCount() {
        return myHours.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView myTimeLabel;
        public TextView mySummaryLabel;
        public TextView myTemperatureLabel;
        public ImageView myIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            myTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mySummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            myTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            myIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);

            itemView.setOnClickListener(this);
        }

        public void bindHour(Hour hour) {
            myTimeLabel.setText(hour.getHour());
            mySummaryLabel.setText(hour.getSummary());
            myTemperatureLabel.setText(hour.getTemperature() + "");
            myIconImageView.setImageResource(hour.getIconId());

        }


        @Override
        public void onClick(View v) {
            String time = myTimeLabel.getText().toString();
            String temperature = myTemperatureLabel.getText().toString();
            String summary = mySummaryLabel.getText().toString();
            String message = String.format("At %s is will be %s and %s",
                    time, temperature, summary);

            Toast.makeText(myContext, message, Toast.LENGTH_LONG).show();
        }
    }
}
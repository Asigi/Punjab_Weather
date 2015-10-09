package io.arsh.pweather.weather;

import io.arsh.pweather.R;
import io.arsh.pweather.weather.Current;
import io.arsh.pweather.weather.Day;
import io.arsh.pweather.weather.Hour;

/**
 * Created by arshdeep on 8/10/15.
 */
public class Forecast {


    private Current myCurrent;
    private Hour[] myHourlyForecast;
    private Day[] myDailyForecast;


    public Current getCurrent() {
        return myCurrent;
    }

    public void setCurrent(Current myCurrent) {
        this.myCurrent = myCurrent;
    }

    public Hour[] getHourlyForecast() {
        return myHourlyForecast;
    }

    public void setHourlyForecast(Hour[] myHourlyForecast) {
        this.myHourlyForecast = myHourlyForecast;
    }

    public Day[] getDailyForecast() {
        return myDailyForecast;
    }

    public void setDailyForecast(Day[] myDailyForecast) {
        this.myDailyForecast = myDailyForecast;
    }



    public static int getIconId(String iconString) {

        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        int iconId = R.drawable.clear_day;

        if (iconString.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        } else if(iconString.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        } else if (iconString.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (iconString.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (iconString.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (iconString.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (iconString.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (iconString.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (iconString.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (iconString.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }
}

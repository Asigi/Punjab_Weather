package io.arsh.pweather.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by arshdeep on 8/10/15.
 */
public class Day implements Parcelable {

    private long myTime;
    private String mySummary;
    private double myTemperatureMax;
    private String myIcon;
    private String myTimezone;

    public long getTime() {
        return myTime;
    }

    public void setTime(long myTime) {
        this.myTime = myTime;
    }

    public String getSummary() {
        return mySummary;
    }

    public void setSummary(String mySummary) {
        this.mySummary = mySummary;
    }

    public int getTemperatureMax() {
        return (int) Math.round(myTemperatureMax);
    }

    public void setTemperatureMax(double myTemperatureMax) {
        this.myTemperatureMax = myTemperatureMax;
    }

    public String getIcon() {
        return myIcon;
    }

    public void setIcon(String myIcon) {
        this.myIcon = myIcon;
    }

    public String getTimezone() {
        return myTimezone;
    }

    public void setTimezone(String myTimezone) {
        this.myTimezone = myTimezone;
    }

    public int getIconId() {
        return Forecast.getIconId(myIcon);
    }

    public String getDayOfTheWeek() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(myTimezone));
        Date dateTime = new Date(myTime*1000);

        return formatter.format(dateTime);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(myTime);
        dest.writeString(mySummary);
        dest.writeDouble(myTemperatureMax);
        dest.writeString(myIcon);
        dest.writeString(myTimezone);

    }

    private Day(Parcel in) {
        myTime = in.readLong();
        mySummary = in.readString();
        myTemperatureMax = in.readDouble();
        myIcon = in.readString();
        myTimezone = in.readString();
    }

    public Day() {}

    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}

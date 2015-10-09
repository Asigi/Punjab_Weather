package io.arsh.pweather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Arsh October 8 2015
 */
public class Hour implements Parcelable {

    private long myTime;
    private String mySummary;
    private double myTemperature;
    private String myIcon;
    private String myTimezone;

    public Hour() {
        //empty constructor
    }

    public int getTemperature() {
        return (int) Math.round(myTemperature);
    }

    public void setTemperature(double myTemperature) {
        this.myTemperature = myTemperature;
    }

    public String getSummary() {
        return mySummary;
    }

    public void setSummary(String mySummary) {
        this.mySummary = mySummary;
    }

    public long getTime() {
        return myTime;
    }

    public void setTime(long myTime) {
        this.myTime = myTime;
    }

    public String getIcon() {
        return myIcon;
    }

    public int getIconId() {
        return Forecast.getIconId(myIcon);
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



    @Override
    public int describeContents() {
        return 0;
    }


    public String getHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        Date date = new Date(myTime * 1000);
        return formatter.format(date);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(myTime);
        dest.writeString(mySummary);
        dest.writeDouble(myTemperature);
        dest.writeString(myIcon);
        dest.writeString(myTimezone);
    }

    public Hour(Parcel theParc) {
        myTime = theParc.readLong();
        mySummary = theParc.readString();
        myTemperature = theParc.readDouble();
        myIcon = theParc.readString();
        myTimezone = theParc.readString();
    }

    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel source) {
            return new Hour(source);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };


}

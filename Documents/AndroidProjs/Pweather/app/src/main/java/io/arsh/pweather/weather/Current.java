package io.arsh.pweather.weather;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by Student on October 8 2015
 */
public class Current {

    private String myIcon;
    private long myTime;
    private double myTemperature;
    private double myHumidity;
    private double myPrecipChance;
    private String mySummary;
    private String myTimeZone;

    public String getMyTimeZone() {
        return myTimeZone;
    }

    public void setMyTimeZone(String myTimeZone) {
        this.myTimeZone = myTimeZone;
    }



    public long getMyTime() {
        return myTime;
    }

    /**
     * I think this fixes Unix time into something more readable
     * @return a string representation of the time
     */
    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getMyTimeZone()));
        Date dateTime = new Date(getMyTime() * 1000); //convert to milliseconds from seconds
        String timeString = formatter.format(dateTime);

        return timeString;
    }

    public void setMyTime(long myTime) {
        this.myTime = myTime;
    }

    public String getMyIcon() {
        return myIcon;
    }

    public int getIconId() {
        return Forecast.getIconId(myIcon);
    }

    public void setMyIcon(String myIcon) {
        this.myIcon = myIcon;
    }

    public int getMyTemperature() {
        return (int) Math.round(myTemperature);
    }

    public void setMyTemperature(double myTemperature) {
        this.myTemperature = myTemperature;
    }

    public double getMyHumidity() {
        return myHumidity;
    }

    public void setMyHumidity(double myHumidity) {
        this.myHumidity = myHumidity;
    }

    public int getMyPrecipChance() {
        double precipPercentage = myPrecipChance * 100;
        return (int) Math.round(precipPercentage);
    }

    public void setMyPrecipChance(double myPrecipChance) {
        this.myPrecipChance = myPrecipChance;
    }

    public String getMySummary() {
        return mySummary;
    }

    public void setMySummary(String mySummary) {
        this.mySummary = mySummary;
    }


}

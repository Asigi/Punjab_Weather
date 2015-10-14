package io.arsh.pweather.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.arsh.pweather.R;
import io.arsh.pweather.utils.TheLocUtil;
import io.arsh.pweather.utils.TheNetUtil;
import io.arsh.pweather.weather.Current;
import io.arsh.pweather.weather.Day;
import io.arsh.pweather.weather.Forecast;
import io.arsh.pweather.weather.Hour;


public class TempActivity extends AppCompatActivity {

    @Bind(R.id.timeLabel) TextView myTimeLabel;
    @Bind(R.id.temperatureLabel) TextView myTemperatureLabel;
    @Bind(R.id.humidityValue) TextView myHumidityValue;
    //@Bind(R.id.precipValue) TextView myPrecipValue; not available in Open, so no longer needed.
    @Bind(R.id.summaryLabel) TextView mySummaryLabel;
    @Bind(R.id.iconImageView) ImageView myIconImageView;
    @Bind(R.id.refreshImageView) ImageView myRefreshImageView;
    @Bind(R.id.progressBar) ProgressBar myProgressBar;
    @Bind(R.id.locationLabel) TextView myLocation;


    private static final String TAG = TempActivity.class.getSimpleName();
    private Forecast myOpenForecast; //OPEN
    private Forecast myDarkForecast; //DARK SKY
    private Forecast officialForecast; //combined

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        ButterKnife.bind(this); //wrote this so that butterknife would run

        myProgressBar.setVisibility(View.INVISIBLE); //originally not seen.

        final double latitude = TheLocUtil.getLatitude();
        final double longitude = TheLocUtil.getLongitude();

        myRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO check if data is connected, if not then change summary label to say "connect to net"
                getForecast(latitude, longitude);
            }
        });


        //TODO check if data is connected, if not then change summary label to say "connect to net"
        getForecast(latitude, longitude);

        //TODO summary label should change to white after data is loaded
    }


    private void getForecast(final double theLat, final double theLong) {
        String apiKeyW = "c6b2ddbf6c41031ec01ee83c14dca99a"; //open weather, use for current temperature
        String forecastURLW = "http://api.openweathermap.org/data/2.5/weather?"
                + "lat=" + theLat
                + "&lon=" + theLong
                + "&APPID=" + apiKeyW + "";

        //http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID={APIKEY}
        //api.openweathermap.org/data/2.5/weather?lat=35&lon=139
        //api.openweathermap.org/data/2.5/find?q=London&units=metric

        if (TheNetUtil.isNetworkAvailable(TempActivity.this)) {
            toggleRefresh();
            Log.e(TAG, "getForecast(), network is available (1)");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastURLW)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    Log.e(TAG, "getForecast(), FAILURE (1)");
                    Log.e(TAG, "request: " + request.toString());
                    Log.e(TAG, "Exception: " + e.getMessage());
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Log.e(TAG, "getForecast(), RESPONSE (1)");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            myOpenForecast = parseOpenForecastDetails(jsonData);//todo

                            //NOW DO THE LOAD FROM DARK SKYS

                            String apiKey = "18131678eb944e61639caac84cb622b6"; //forecast.io, use for hourly/7day
                            final String forecastURL = "https://api.forecast.io/forecast/" + apiKey +
                                    "/" + theLat + "," + theLong;


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    OkHttpClient client2 = new OkHttpClient();
                                    Request request2 = new Request.Builder()
                                            .url(forecastURL)
                                            .build();
                                    Call call2 = client2.newCall(request2);
                                    call2.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Request request, IOException e) {
                                            Log.e(TAG, "getForecast(), FAILURE (2)");
                                            Log.e(TAG, "request: " + request.toString());
                                            Log.e(TAG, "Exception: " + e.getMessage());
                                            alertUserAboutError();
                                        }

                                        @Override
                                        public void onResponse(Response response) throws IOException {
                                            Log.e(TAG, "getForecast(), RESPONSE (2)");
                                            try {
                                                String jsonData = response.body().string();
                                                Log.v(TAG, jsonData);
                                                if (response.isSuccessful()) {
                                                    myDarkForecast = parseForecastDetails(jsonData); //TODO This line must be called BEFORE the line that calls parseOpenForecastDetails method which is about 30 lines up from here (because of setting the time of forecasts)!!!

                                                    Log.e(TAG, myDarkForecast.getHourlyForecast() + "");
                                                    Log.e(TAG, myOpenForecast.getCurrent().getMySummary() + "");

                                                    //NOW COMBINE THEM INTO ONE

                                                    officialForecast = combineForecast(myDarkForecast, myOpenForecast);

                                                } else {
                                                    alertUserAboutError();
                                                }
                                            } catch (IOException e) {
                                                Log.e(TAG, "Exception caught: ", e);
                                            } catch (JSONException e) {
                                                Log.e(TAG, "Exception caught: ", e);
                                            }
                                        }
                                    });
                                }
                            });

                            runOnUiThread(new Runnable() { //this code will cause whatever is in the inner run method to run on the main thread.
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }

                }
            });


        } else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view) {
        try {
            Intent intent = new Intent(this, DailyForecastActivity.class);
            intent.putExtra(MainActivity.DAILY_FORECAST, myDarkForecast.getDailyForecast());
            startActivity(intent);
        } catch (NullPointerException e) {
            Toast.makeText(TempActivity.this, "Please wait", Toast.LENGTH_SHORT);
        }
    }

    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view) {

        try {
            Intent intent = new Intent(this, HourlyForecastActivity.class);
            intent.putExtra(MainActivity.HOURLY_FORECAST, myDarkForecast.getHourlyForecast());
            startActivity(intent);
        } catch (NullPointerException e){
            Toast.makeText(TempActivity.this, "Please wait", Toast.LENGTH_SHORT);
        }
    }

    private void toggleRefresh() {
        if(myProgressBar.getVisibility() == View.INVISIBLE) {
            myProgressBar.setVisibility(View.VISIBLE);
            myRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            myProgressBar.setVisibility(View.INVISIBLE);
            myRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        Current current = myOpenForecast.getCurrent();
        myLocation.setText(TheLocUtil.getCurrentVillage() + "\n" + TheLocUtil.getCurrentDistrict());

        myTemperatureLabel.setText((current.getMyTemperature() - 273) + "");
        myTimeLabel.setText(current.getFormattedTime() + " ਨੂ ਤਾਪਮਾਨ ਹੋਵੇਗਾ");
        myHumidityValue.setText((int)current.getMyHumidity() + "%");
        //myPrecipValue.setText(current.getMyPrecipChance() + "%");
        mySummaryLabel.setText(current.getMySummary());
        Drawable drawable = getResources().getDrawable(current.getIconId());
        myIconImageView.setImageDrawable(drawable);
    }

    /**
     * Combines the two forecasts into one.
     * @param theDark dark forecast
     * @param theOpen open forecast
     * @return official combined forecast
     */
    private Forecast combineForecast(Forecast theDark, Forecast theOpen) {
        Forecast forecast = new Forecast();

        //get current from theOpen
        Current oc = theOpen.getCurrent();
        //set that current's time
        Date date = new Date();
        oc.setMyTime(date.getTime());
        //set current value of forecast.
        forecast.setCurrent(oc);

        forecast.setDailyForecast(theDark.getDailyForecast());
        forecast.setHourlyForecast(theDark.getHourlyForecast());


        return forecast;
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        //forecast.setCurrent(getCurrentDetails(jsonData));

        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;
    }

    private Forecast parseOpenForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();

        forecast.setCurrent(getOpenCurrentDetails(jsonData));

        return forecast;
    }

    /**
     * Sets the current information for the dark forecast.
     * @param jsonData a string of json
     * @return current data object to be entered into forecast object.
     * @throws JSONException
     */
    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        Current current = new Current();

        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently"); //get currently JSON from within forecast JSON
        current.setMyTime(currently.getLong("time"));
        current.setMyHumidity(currently.getDouble("humidity"));
        current.setMyIcon(currently.getString("icon"));
        current.setMyPrecipChance(currently.getDouble("precipProbability"));
        current.setMySummary(currently.getString("summary"));
        current.setMyTemperature(currently.getDouble("temperature"));
        current.setMyTimeZone(timezone);

        Log.d(TAG, current.getFormattedTime());

        return current;
    }

    /**
     * Sets the current information for the open forecast
     * @param jsonData a string of json
     * @return current data object to be entered into forecast object.
     * @throws JSONException
     */
    private Current getOpenCurrentDetails(String jsonData) throws JSONException {
        Current current = new Current();
        JSONObject forecast = new JSONObject(jsonData);

        JSONObject Sys = forecast.getJSONObject("sys");
        String sunrise = Sys.getString("sunrise");
        String sunset = Sys.getString("sunset"); //TODO, maybe use these in premium version of app

        JSONObject main = forecast.getJSONObject("main");
        current.setMyHumidity(main.getDouble("humidity"));
        current.setMyTemperature(main.getDouble("temp"));

        JSONArray weath = forecast.getJSONArray("weather");
        JSONObject weather = weath.getJSONObject(0);
        current.setMySummary(weather.getString("main"));
        current.setMyIcon(weather.getString("icon"));
        current.setMyTimeZone("IST");


        return current;
    }


    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for(int i = 0; i<data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));;
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;
        }

        return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours= new Hour[data.length()];

        for(int i =0; i <data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;
        }

        return hours;
    }



    private void alertUserAboutError() {
        Log.v(TAG, "alerUserAboutError");
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }


/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //this prevents you from getting back to the previous page.
            startActivity(intent);
        }

        return super.onKeyDown(keyCode, event);
    }
    */
}

package io.arsh.pweather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TempActivity extends AppCompatActivity {

    @Bind(R.id.timeLabel) TextView myTimeLabel;
    @Bind(R.id.temperatureLabel) TextView myTemperatureLabel;
    @Bind(R.id.humidityValue) TextView myHumidityValue;
    @Bind(R.id.precipValue) TextView myPrecipValue;
    @Bind(R.id.summaryLabel) TextView mySummaryLabel;
    @Bind(R.id.iconImageView) ImageView myIconImageView;
    @Bind(R.id.refreshImageView) ImageView myRefreshImageView;
    @Bind(R.id.progressBar) ProgressBar myProgressBar;

    private static final String TAG = TempActivity.class.getSimpleName();
    private Forecast myForcast;

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


    private void getForecast(double theLat, double theLong) {
        String apiKey = "c6b2ddbf6c41031ec01ee83c14dca99a";

        String forecastURL = "";

        if (TheNetUtil.isNetworkAvailable(TempActivity.this)) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastURL)
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
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
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
                            myForcast = parseForecastDetails(jsonData);
                            //myCurWeather = getCurrentDetails(jsonData);
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
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(MainActivity.DAILY_FORECAST, myForcast.getDailyForecast());
        startActivity(intent);
    }

    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view) {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(MainActivity.HOURLY_FORECAST, myForcast.getHourlyForecast());
        startActivity(intent);
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
        Current current = myForcast.getCurrent();
        myTemperatureLabel.setText(current.getMyTemperature() + "");
        myTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
        myHumidityValue.setText(current.getMyHumidity() + "");
        myPrecipValue.setText(current.getMyPrecipChance() + "%");
        mySummaryLabel.setText(current.getMySummary());
        Drawable drawable = getResources().getDrawable(current.getIconId());
        myIconImageView.setImageDrawable(drawable);

    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));

        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;
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


    private Current getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setMyHumidity(currently.getDouble("humidity"));
        current.setMyTime(currently.getLong("time"));
        current.setMyIcon(currently.getString("icon"));
        current.setMyPrecipChance(currently.getDouble("precipProbability"));
        current.setMySummary(currently.getString("summary"));
        current.setMyTemperature(currently.getDouble("temperature"));
        current.setMyTimeZone(timezone);

        Log.d(TAG, current.getFormattedTime());

        return current;
    }


    private void alertUserAboutError() {
        Log.v(TAG, "alerUserAboutError Log");
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

package io.arsh.pweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;


public class TempActivity extends AppCompatActivity {

    @Bind(R.id.timeLabel) TextView myTimeLabel;
    @Bind(R.id.temperatureLabel) TextView myTemperatureLabel;
    @Bind(R.id.humidityValue) TextView myHumidityValue;
    @Bind(R.id.precipValue) TextView myPrecipValue;
    @Bind(R.id.summaryLabel) TextView mySummaryLabel;
    @Bind(R.id.iconImageView) ImageView myIconImageView;
    @Bind(R.id.refreshImageView) ImageView myRefreshImageView;
    @Bind(R.id.progressBar) ProgressBar myProgressBar;




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

                //check if data is connected, if not then change summary label to say "connect to net"

                getForecast(latitude, longitude);
            }
        });


        //check if data is connected, if not then change summary label to say "connect to net"

        getForecast(latitude, longitude);



        //summary label should change to white after data is loaded


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

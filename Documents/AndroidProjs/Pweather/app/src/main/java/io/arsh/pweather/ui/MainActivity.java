package io.arsh.pweather.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.arsh.pweather.R;
import io.arsh.pweather.ui.LocationActivity;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.splashButton) Button mainButton;

    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        //When user clicks "show me weather"
        //check to see if the user has a registered location, it should be stored in shared preferences.
        //if s/he does, then send them to the temperature screen.
        //else send them to the location selector screen





    }

    @OnClick (R.id.splashButton)
    public void mainClick() {
        Intent intent = new Intent(this, LocationActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //this prevents you from getting back to the previous page.
        startActivity(intent);
    }


}

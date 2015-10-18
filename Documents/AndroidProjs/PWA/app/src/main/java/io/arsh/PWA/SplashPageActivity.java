package io.arsh.PWA;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import io.arsh.punjabweatherapp.R;

public class SplashPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        ButterKnife.bind(this);



    }


}

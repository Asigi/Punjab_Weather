package io.arsh.pweather;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationActivity extends AppCompatActivity {

    @Bind(R.id.LocationDistrictSpinner) Spinner districtSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.GreenTheme); dont use this here
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.district_names, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        districtSpinner.setAdapter(adapter);

        //take in information about user's district.
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new Toaster(LocationActivity.this, parent, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //then after getting the district info, make the village/city question appear

        //Q: what happens if the user enters in their village and then changes the district?
        //A: I'll figure it out after I try doing what is said above.


    }

    //add a submit button
    //check to see if the user is connected to the internet
    //if so, add their village to sharedPreferences.
    //send the user to the temperature information screen.


    public class Toaster {
        public Toaster(Activity activity, AdapterView<?> parent, int position) {
            Toast.makeText(activity, "chose: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
        }
    }



}

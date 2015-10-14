package io.arsh.pweather.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.arsh.pweather.R;
import io.arsh.pweather.utils.TheLocUtil;

public class LocationActivity extends AppCompatActivity {

    @Bind(R.id.LocationDistrictSpinner) Spinner districtSpinner;
    @Bind(R.id.LocationVillageSpinner) Spinner villageSpinner;

    protected String myDistrict = "";
    protected String myVillage = ""; //this represents both the villages and the cities within the district


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.district_names, android.R.layout.simple_spinner_item); // Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  // Specify the layout to use when the list of choices appears
        districtSpinner.setAdapter(adapter);  // Apply the adapter to the spinner

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //take in information about user's district.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myDistrict = parent.getItemAtPosition(position).toString();

                //Toast.makeText(LocationActivity.this, "array: " + getResources().getStringArray(arraySetter()).length, Toast.LENGTH_SHORT).show();

                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(LocationActivity.this,
                        arraySetter(), android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                villageSpinner.setAdapter(adapter2);
                villageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        myVillage = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(LocationActivity.this, "array int#: " + arraySetter(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }



    @OnClick(R.id.LocationButton)
    public void submitLocation() {
        if (myDistrict.equals("(ਇੱਥੇ ਇੱਕ ਚੁਣੋ)")) {
            String explanation = "ਤੁਹਾਨੂੰ ਆਪਣੇ ਜ਼ਿਲ੍ਹੇ ਦੀ ਚੋਣ ਕਰਨੀ ਚਾਹੀਦੀ ਹੈ";
            Toast.makeText(this, explanation , Toast.LENGTH_SHORT).show();
        } else {
            TheLocUtil.setHomeVillDist(myVillage, myDistrict, LocationActivity.this);
            //if so, add their village to sharedPreferences.

            //send the user to the temperature information screen.
            Intent intent = new Intent(this, TempActivity.class);

            startActivity(intent);


        }
    }





    /**
     * This method returns the array from which the villages will be taken.
     */
    private int arraySetter() {
        if (myDistrict.equals("ਲੁਧਿਆਣਾ")) {
            return R.array.Ludhiana_cv;
        } else if (myDistrict.equals("ਅੰਮ੍ਰਿਤਸਰ")) {
            return R.array.Amritsar_cv;
        } else if (myDistrict.equals("ਗੁਰਦਾਸਪੁਰ")) {
            return R.array.Gurdaspur_cv;
        } else if (myDistrict.equals("ਜਲੰਧਰ")) {
            return R.array.Jalandar_cv;
        } else if (myDistrict.equals("ਚੰਡੀਗੜ੍ਹ")) {
            return R.array.Chandigarh_cv;
        } else if (myDistrict.equals("ਫ਼ਿਰੋਜ਼ਪੁਰ")) {
            return R.array.Firozpur_cv;
        } else if (myDistrict.equals("ਪਟਿਆਲਾ")) {
            return R.array.Patiala_cv;
        } else if (myDistrict.equals("ਸੰਗਰੂਰ")) {
            return R.array.Sangrur_cv;
        } else if (myDistrict.equals("ਹੁਸ਼ਿਆਰਪੁਰ")) {
            return R.array.Hoshiarpur_cv;
        } else if (myDistrict.equals("ਬਠਿੰਡਾ")) {
            return R.array.Batinda_cv;
        } else if (myDistrict.equals("ਤਰਨ ਤਾਰਨ")) {
            return R.array.TarnTaran_cv;
        } else if (myDistrict.equals("ਮੋਗਾ")) {
            return R.array.Moga_cv;
        } else if (myDistrict.equals("ਸਾਹਿਬਜ਼ਾਦਾ ਅਜੀਤ ਸਿੰਘ ਨਗਰ")) {
            return R.array.SASnagar_cv;
        } else if (myDistrict.equals("ਮੁਕਤਸਰ")) {
            return R.array.Muktsar_cv;
        } else if (myDistrict.equals("ਕਪੂਰਥਲਾ")) {
            return R.array.Kapurthala_cv;
        } else if (myDistrict.equals("ਮਾਨਸਾ")) {
            return R.array.Mansa_cv;
        } else if (myDistrict.equals("ਬਰਨਾਲਾ")) {
            return R.array.Barnala_cv;
        } else if (myDistrict.equals("ਫਰੀਦਕੋਟ")) {
            return R.array.Faridkot_cv;
        } else if (myDistrict.equals("ਫਤਿਹਗੜ੍ਹ ਸਾਹਿਬ")) {
            return R.array.Fatigarh_cv;
        } else if (myDistrict.equals("ਫਾਜ਼ਿਲਕਾ")) {
            return R.array.Fazilka_cv;
        } else if (myDistrict.equals("ਪਠਾਨਕੋਟ")) {
            return R.array.Patankot_cv;
        } else if (myDistrict.equals("ਰੋਪੜ")) {
            return R.array.Ropur_cv;
        } else if (myDistrict.equals("ਸ਼ਹੀਦ ਭਗਤ ਸਿੰਘ ਨਗਰ")) {
            return R.array.SBSnagar_cv;
        } else {
            return R.array.empty_list;
        }
    }



}

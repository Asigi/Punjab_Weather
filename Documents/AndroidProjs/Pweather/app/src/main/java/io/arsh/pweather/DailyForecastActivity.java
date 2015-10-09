package io.arsh.pweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import butterknife.ButterKnife;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


import butterknife.ButterKnife;
import butterknife.Bind;

public class DailyForecastActivity extends Activity {
    private Day[] myDays;
    @Bind(android.R.id.list)
    ListView myListView;
    @Bind(android.R.id.empty)
    TextView myEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        myDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);

        DayAdapter adapter = new DayAdapter(this, myDays);
        //setListAdapter(adapter); this was used when this class extended ListActivity instead of Activity

        //The rest of this onCreate method below was added in after changing to Activity from ListActivity
        myListView.setAdapter(adapter);
        myListView.setEmptyView(myEmptyTextView);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * @param l is the listview where the tap occured
                 * @param v is the specific item that was clicked
                 * @param position is the numerical index of the item in the list
                 * @param id is an opitonal item id that we can set though we arent using it.
                 */
                String dayOfTheWeek = myDays[position].getDayOfTheWeek();
                String conditions = myDays[position].getSummary();
                String highTemp = myDays[position].getTemperatureMax() + "";
                String message = String.format("On %s the high will be %s and it will be %s",
                        dayOfTheWeek, highTemp, conditions);
                Toast.makeText(DailyForecastActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });

    }

}
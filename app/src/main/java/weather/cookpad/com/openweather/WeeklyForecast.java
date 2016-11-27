package weather.cookpad.com.openweather;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class WeeklyForecast extends AppCompatActivity {
    private double latitude;
    private double longitude;
    private static String url_get_data = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=";

    TextView textView12;
    TextView textView13;

    TextView textView18;
    TextView textView19;

    TextView textView25;
    TextView textView26;

    TextView textView15;
    TextView textView16;

    TextView textView21;
    TextView textView22;

    TextView textView27;
    TextView textView29;

    TextView textView31;
    TextView textView32;

    TextView textView35;

    TextView textView11;
    TextView textView17;
    TextView textView23;
    TextView textView14;
    TextView textView20;
    TextView textView24;
    TextView textView30;

    String city = "";

    ProgressDialog pDialog;
    JSONArray lst = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_forecast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView12 = (TextView) findViewById(R.id.textView12);
        textView13 = (TextView) findViewById(R.id.textView13);

        textView18 = (TextView) findViewById(R.id.textView18);
        textView19 = (TextView) findViewById(R.id.textView19);

        textView25 = (TextView) findViewById(R.id.textView25);
        textView26 = (TextView) findViewById(R.id.textView26);

        textView15 = (TextView) findViewById(R.id.textView15);
        textView16 = (TextView) findViewById(R.id.textView16);

        textView21 = (TextView) findViewById(R.id.textView21);
        textView22 = (TextView) findViewById(R.id.textView22);

        textView27 = (TextView) findViewById(R.id.textView27);
        textView29 = (TextView) findViewById(R.id.textView29);

        textView31 = (TextView) findViewById(R.id.textView31);
        textView32 = (TextView) findViewById(R.id.textView32);

        textView35 = (TextView) findViewById(R.id.textView35);

        textView11 = (TextView) findViewById(R.id.textView11);
        textView17 = (TextView) findViewById(R.id.textView17);
        textView23 = (TextView) findViewById(R.id.textView23);
        textView14 = (TextView) findViewById(R.id.textView14);
        textView20 = (TextView) findViewById(R.id.textView20);
        textView24 = (TextView) findViewById(R.id.textView24);
        textView30 = (TextView) findViewById(R.id.textView30);

        Calendar calendar = Calendar.getInstance();
        Date day1 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day2 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day3 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day4 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day5 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day6 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day7 = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        textView11.setText(dateFormat.format(day1));
        textView17.setText(dateFormat.format(day2));
        textView23.setText(dateFormat.format(day3));
        textView14.setText(dateFormat.format(day4));
        textView20.setText(dateFormat.format(day5));
        textView24.setText(dateFormat.format(day6));
        textView30.setText(dateFormat.format(day7));

        GPSTracker gps = new GPSTracker(this);
        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        new Data().execute(url_get_data);
    }

    class Data extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(WeeklyForecast.this);
            pDialog.setMessage("Loading ..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            // Building Parameters
            HashMap<String, String> params = new HashMap<>();
            JSONParser jsonParser = new JSONParser();
            // getting JSON Object
            // Note that create product url accepts POST method
            // check for success tag
            try {
                JSONObject json = jsonParser.makeHttpRequest(url_get_data + Double.toString(latitude) + "&lon=" + Double.toString(longitude)+ "&cnt=7&mode=json" + "&appid=eb0843ac4aa000d3fabe535ebe0098aa", "POST", params);
                city = json.getJSONObject("city").get("name").toString();

                // check log cat for response
                Log.d("Create Response1", json.toString());
                lst= json.getJSONArray("list");

                Log.d("Create Response4", lst.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            try{
                textView35.setText("You are at: "  + city);

                textView12.setText("min: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(0).getJSONObject("temp").get("min").toString()) -273) + 32)*100.0)/100.0) + "F");
                textView13.setText("Max: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(0).getJSONObject("temp").get("max").toString()) -273) + 32)*100.0)/100.0) + "F");

                textView18.setText("min: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(1).getJSONObject("temp").get("min").toString()) -273) + 32)*100.0)/100.0) + "F");
                textView19.setText("Max: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(1).getJSONObject("temp").get("max").toString()) -273) + 32)*100.0)/100.0) + "F");

                textView25.setText("min: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(2).getJSONObject("temp").get("min").toString()) -273) + 32)*100.0)/100.0) + "F");
                textView26.setText("Max: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(2).getJSONObject("temp").get("max").toString()) -273) + 32)*100.0)/100.0) + "F");

                textView15.setText("min: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(3).getJSONObject("temp").get("min").toString()) -273) + 32)*100.0)/100.0) + "F");
                textView16.setText("Max: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(3).getJSONObject("temp").get("max").toString()) -273) + 32)*100.0)/100.0) + "F");

                textView21.setText("min: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(4).getJSONObject("temp").get("min").toString()) -273) + 32)*100.0)/100.0) + "F");
                textView22.setText("Max: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(4).getJSONObject("temp").get("max").toString()) -273) + 32)*100.0)/100.0) + "F");

                textView27.setText("min: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(5).getJSONObject("temp").get("min").toString()) -273) + 32)*100.0)/100.0) + "F");
                textView29.setText("Max: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(5).getJSONObject("temp").get("max").toString()) -273) + 32)*100.0)/100.0) + "F");

                textView31.setText("min: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(6).getJSONObject("temp").get("min").toString()) -273) + 32)*100.0)/100.0) + "F");
                textView32.setText("Max: "+ Double.toString(Math.round((1.8*(Double.parseDouble(lst.getJSONObject(6).getJSONObject("temp").get("max").toString()) -273) + 32)*100.0)/100.0) + "F");

            }catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

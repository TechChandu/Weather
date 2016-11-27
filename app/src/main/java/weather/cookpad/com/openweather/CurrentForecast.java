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

import java.util.HashMap;

public class CurrentForecast extends AppCompatActivity {

    private double latitude;
    private double longitude;
    private static String url_get_data = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=";
    public String Current_temp="";
    public String current_temp_min = "";
    public String current_temp_max = "";

    public String tomorrow_temp_min = "";
    public String tomorrow_temp_max = "";
    String city = "";

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView34;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_forecast);
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
        textView1 = (TextView) findViewById(R.id.textView4);
        textView2 = (TextView) findViewById(R.id.textView5);
        textView3 = (TextView) findViewById(R.id.textView6);
        textView4 = (TextView) findViewById(R.id.textView8);
        textView5 = (TextView) findViewById(R.id.textView9);
        textView34 = (TextView) findViewById(R.id.textView34);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            pDialog = new ProgressDialog(CurrentForecast.this);
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
                JSONObject json = jsonParser.makeHttpRequest(url_get_data + Double.toString(latitude) + "&lon=" + Double.toString(longitude)+ "&cnt=2&mode=json" + "&appid=eb0843ac4aa000d3fabe535ebe0098aa", "POST", params);

                // check log cat for response
                Log.d("Create Response1", json.toString());
                city = json.getJSONObject("city").get("name").toString();
                JSONArray lst= json.getJSONArray("list");

                Log.d("Create Response4", lst.toString());
                Current_temp = lst.getJSONObject(0).getJSONObject("temp").get("day").toString();
                current_temp_max = lst.getJSONObject(0).getJSONObject("temp").get("min").toString();
                current_temp_min = lst.getJSONObject(0).getJSONObject("temp").get("max").toString();

                tomorrow_temp_max = lst.getJSONObject(1).getJSONObject("temp").get("min").toString();
                tomorrow_temp_min = lst.getJSONObject(1).getJSONObject("temp").get("max").toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            Double fa = 1.8*(Double.parseDouble(Current_temp) - 273) + 32;
            Double fa1 = 1.8*(Double.parseDouble(current_temp_max) - 273) + 32;
            Double fa2 = 1.8*(Double.parseDouble(current_temp_min) - 273) + 32;

            Double fa3 = 1.8*(Double.parseDouble(tomorrow_temp_max) - 273) + 32;
            Double fa4 = 1.8*(Double.parseDouble(tomorrow_temp_min) - 273) + 32;


            textView1.setText("Current: " + Double.toString(Math.round(fa*100.0)/100.0) + "F");
            textView2.setText("temp_max: " + Double.toString(Math.round(fa1*100.0)/100.0) + "F");
            textView3.setText("temp_min: " + Double.toString(Math.round(fa2*100.0)/100.0) + "F");

            textView4.setText("temp_max: " + Double.toString(Math.round(fa3*100.0)/100.0) + "F");
            textView5.setText("temp_min: " + Double.toString(Math.round(fa4*100.0)/100.0) + "F");

            textView34.setText("You are at: " + city);

        }
    }
}

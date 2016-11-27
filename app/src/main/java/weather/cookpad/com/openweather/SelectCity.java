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
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

public class SelectCity extends AppCompatActivity {

    Button btnBlr;
    Button btnChennai;
    Button btnTokyo;
    Button btnKol;
    Button btnMum;
    Button btnDelhi;

    String city = "";
    String url_get_data = "";

    String current_temp_max = "";
    String current_temp_min = "";

    TextView textView10;
    TextView textView28;
    TextView textView33;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
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

        btnBlr = (Button) findViewById(R.id.button);
        btnDelhi = (Button) findViewById(R.id.button2);
        btnKol = (Button) findViewById(R.id.button3);
        btnMum = (Button) findViewById(R.id.button4);
        btnChennai = (Button) findViewById(R.id.button5);
        btnTokyo = (Button) findViewById(R.id.button6);

        textView10 = (TextView) findViewById(R.id.textView10);
        textView28 = (TextView) findViewById(R.id.textView28);
        textView33 = (TextView) findViewById(R.id.textView33);

        btnBlr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Bangalore";
                url_get_data = "http://api.openweathermap.org/data/2.5/forecast/daily?id=1277333&cnt=1&appid=eb0843ac4aa000d3fabe535ebe0098aa";
                new Data().execute(url_get_data);
            }
        });

        btnTokyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Tokyo";
                url_get_data = "http://api.openweathermap.org/data/2.5/forecast/daily?id=1850147&cnt=1&mode=json&appid=eb0843ac4aa000d3fabe535ebe0098aa";
                new Data().execute(url_get_data);
            }
        });

        btnChennai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Chennai";
                url_get_data = "http://api.openweathermap.org/data/2.5/forecast/daily?id=1264527&cnt=1&appid=eb0843ac4aa000d3fabe535ebe0098aa";
                new Data().execute(url_get_data);
            }
        });

        btnMum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Mumbai";
                url_get_data = "http://api.openweathermap.org/data/2.5/forecast/daily?id=6619347&cnt=1&appid=eb0843ac4aa000d3fabe535ebe0098aa";
                new Data().execute(url_get_data);
            }
        });

        btnDelhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Delhi";
                url_get_data = "http://api.openweathermap.org/data/2.5/forecast/daily?id=1273292&cnt=1&appid=eb0843ac4aa000d3fabe535ebe0098aa";
                new Data().execute(url_get_data);
            }
        });

        btnKol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = "Kolkata";
                url_get_data = "http://api.openweathermap.org/data/2.5/forecast/daily?id=1275004&cnt=1&appid=eb0843ac4aa000d3fabe535ebe0098aa";
                new Data().execute(url_get_data);
            }
        });
    }


    class Data extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SelectCity.this);
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
                JSONObject json = jsonParser.makeHttpRequest(url_get_data, "POST", params);

                // check log cat for response
                Log.d("Create Response1", json.toString());
                JSONArray lst= json.getJSONArray("list");

                Log.d("Create Response4", lst.toString());

                //Current_temp = lst.getJSONObject(0).getJSONObject("temp").get("day").toString();
                current_temp_max = lst.getJSONObject(0).getJSONObject("temp").get("min").toString();
                current_temp_min = lst.getJSONObject(0).getJSONObject("temp").get("max").toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            Double fa = 1.8*(Double.parseDouble(current_temp_max) - 273) + 32;
            Double fa1 = 1.8*(Double.parseDouble(current_temp_min) - 273) + 32;

            textView10.setText("You have selected: " + city) ;
            textView28.setText("Min Temp: " + Double.toString(Math.round(fa*100.0)/100.0) + "F");
            textView33.setText("Max Temp: " + Double.toString(Math.round(fa1*100.0)/100.0) + "F");
        }
    }

}

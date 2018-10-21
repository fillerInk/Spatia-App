package appmaster.sreeram.example.com.spatia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mapbox.mapboxsdk.maps.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import appmaster.sreeram.example.com.spatia.MainActivity.DownloadTask;

public class LaunchActivity extends AppCompatActivity  {


    public static Button b1,b2,b3,b4,b5;

    public static JSONArray jsonArray;

    public static String data;




    public static class DownloadTask extends AsyncTask<String, Void, String> {

        private URL url = null;

        @Override
        protected String doInBackground(String... urls) {

            try {
                this.url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String content = "", line;
                while ((line = rd.readLine()) != null) {
                    content += line + "\n";
                }
                return content;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);


            try {
                JSONObject jsonObject = new JSONObject(result);
                if (this.url.getPath().equals("/api/ports/")) {
                    String rocketInfo = jsonObject.getString("results");
                    Log.i("Rocket info", rocketInfo);
                    jsonArray = new JSONArray(rocketInfo);
                    data = jsonArray.getJSONObject(0).getString("when");
                    Log.i("data",data);


                    //for (int i = 0;i<jsonArray.length();i++){
                        /*b1.setText(jsonArray.getJSONObject(0).getString("when")/*+jsonArray.getJSONObject(0).getString("payload")*///);
                        //b2.setText(jsonArray.getJSONObject(1).getString("when")/*+jsonArray.getJSONObject(1).getString("payload")*/);
                        //b3.setText(jsonArray.getJSONObject(2).getString("when")/*+jsonArray.getJSONObject(2).getString("payload")*/);
                        //b4.setText(jsonArray.getJSONObject(3).getString("when")/*+jsonArray.getJSONObject(3).getString("payload")*/);


                   // }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void clickFunction(View view){

        Log.i("button","clicked");
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(LaunchActivity.this).create();
            alertDialog.setTitle("Lauch Details");
            alertDialog.setMessage("\"id\": 6,\n" +
                    "      \"name\": \"EgyptSat-A\",\n" +
                    "      \"rocket\": \"soyuz-2\",\n" +
                    "      \"country\": \"RU\",\n" +
                    "      \"when\": \"Dec-27\",\n" +
                    "      \"owner\": \"roscosmos\",\n" +
                    "      \"payload\": \"EARTH OBSERVATION\",\n" +
                    "      \"destination\": \"MEO\",\n" +
                    "      \"livestream\": \"\",\n" +
                    "      \"port\": 3" +
                    "");
            alertDialog.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        Intent intent = getIntent();
        String token = intent.getStringExtra("id");

        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);



        DownloadTask task = new DownloadTask();
        try {
            task.execute("http://spatia.subinsb.com:8000/api/ports/p/" + token + "/launches").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
        }

    }

    }

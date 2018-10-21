package appmaster.sreeram.example.com.spatia;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


public class MainActivity extends AppCompatActivity {

    private static MapView  mapView;

    public static JSONObject jsonPart;
    public static int arrayLength =0;

    public static Boolean isFirstRun;
    public static SharedPreferences counterPreference;

    public static JSONArray jsonArray;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;



    public  class DownloadTask extends AsyncTask<String,Void,String>{

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

            mapView = (MapView) findViewById(R.id.mapView);

            try {
                Log.i("Rocket ", this.url.getPath());
                JSONObject jsonObject = new JSONObject(result);

                if (this.url.getPath().equals("/api/ports/")) {

                    String rocketInfo = jsonObject.getString("results");
                    Log.i("Rocket info", rocketInfo);

                    jsonArray = new JSONArray(rocketInfo);

                    arrayLength = jsonArray.length();

                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(MapboxMap mapboxMap) {
                            // One way to add a marker view

                            try {
                                for (int i = 0; i < arrayLength; i++) {
                                    jsonPart = jsonArray.getJSONObject(i);

                                    mapboxMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.valueOf(jsonPart.getString("lat")), Double.valueOf(jsonPart.getString("lng"))))
                                            .title(String.valueOf(i))
                                            .snippet(jsonPart.getString("name"))

                                    );
                                    Log.i("name",jsonPart.getString("name"));
                                    Log.i("lat",jsonPart.getString("lat"));
                                    Log.i("lang",jsonPart.getString("lng"));
                                }
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }

                            mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(@NonNull Marker marker) {
                                   try {

                                       final Marker a = marker;
                                        Log.i("id",String.valueOf(marker.getTitle()));
                                       AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                       alertDialog.setTitle("Information");
                                       alertDialog.setMessage("Name :" + jsonArray.getJSONObject(Integer.valueOf(marker.getTitle())).getString("name") + "\n" + "Latitude :" + jsonArray.getJSONObject(Integer.valueOf(marker.getTitle())).getString("lat") +"\n"+"Longtitude :"+jsonArray.getJSONObject(Integer.valueOf(marker.getTitle())).getString("lng")+ "\n"+"Country :" + jsonArray.getJSONObject(Integer.valueOf(marker.getTitle())).getString("country") + "\n" +"Website :" + jsonArray.getJSONObject(Integer.valueOf(marker.getTitle())).getString("website") + "\n" +"ViewPoints :" + jsonArray.getJSONObject(Integer.valueOf(marker.getTitle())).getString("viewpoints") + "\n");
                                       alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                               new DialogInterface.OnClickListener() {
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       dialog.dismiss();
                                                   }
                                               });
                                       alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SEE ALL LAUNCHES",
                                               new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       Intent intent = new Intent(MainActivity.this,LaunchActivity.class);
                                                       intent.putExtra("id",String.valueOf(a.getTitle()));
                                                       startActivity(intent);

                                                   }
                                               });
                                       alertDialog.show();


                                       //+"Rocket :" + jsonPart.getString("rocket") + "\n""Country :" + jsonPart.getString("country") + "\n"+"Payload :" + jsonPart.getString("payload") + "\n"+"Destination :" + jsonPart.getString("destination") + "\n"+"Time :" + jsonPart.getString("when") + "\n"+"Livestream :" + jsonPart.getString("livestream") + "\n"
                                   }
                                   catch (JSONException e){
                                       e.printStackTrace();
                                   }
                                    return true;
                                }

                            });
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onStart()
    {   super.onStart();
        mapView.onStart();

    }

    @Override
    protected void onStop(){
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Mapbox.getInstance(this,"pk.eyJ1Ijoic3JlZXJhbTc4NyIsImEiOiJjam5od2wzdXYwaWc4M3BvM3lmNjh0eTd5In0.ak9jacQe3Q8yrhN417CpGg");

        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.setStyleUrl(Style.MAPBOX_STREETS);

       /* mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,Integer.valueOf("Open"),Integer.valueOf("Close"));
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();*/




        isFirstRun = getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getBoolean("isFirstRun",true);

        counterPreference = this.getSharedPreferences("appmaster.sreeram.example.com.spatia",MODE_PRIVATE);

        if(isFirstRun)
        {
            startActivity(new Intent(MainActivity.this,wizard.class));
        }

        getSharedPreferences("PREFERENCE",MODE_PRIVATE).edit()
                .putBoolean("isFirstRun",false).apply();


       DownloadTask task = new DownloadTask();
        try {
            task.execute("http://spatia.subinsb.com:8000/api/ports/").get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch(java.util.concurrent.ExecutionException e)
        {
            e.printStackTrace();
        }


    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(String.valueOf(item.getItemId())=="about")
        {
            new AlertDialog.Builder(this)
                    .setTitle("Team Spatia")
                    .setMessage("Made as part of NASA SpaceApps Challenge 2018")
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }*/
}





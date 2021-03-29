package com.project.car_accident;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class weatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Button maps=(Button)findViewById(R.id.maps);
        maps.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(weatherActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        Button weather=(Button)findViewById(R.id.how);
        weather.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(weatherActivity.this, weatherActivity.class);
                startActivity(intent);
            }
        });

        Intent intent=getIntent();
        Double lat=intent.getDoubleExtra("lat",0);
        Double lng=intent.getDoubleExtra("lng",0);
        getWeatherData(lat,lng);
    }
    private void getWeatherData(double lat, double lng){
        String url="http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lng+"&units=metric&lang=kr&appid=7cb064f98d125108a0372a58e20a0d52";
        weatherActivity.ReceiveWeatherTask receiveUseTask=new weatherActivity.ReceiveWeatherTask();
        receiveUseTask.execute(url);
    }
    private class ReceiveWeatherTask extends AsyncTask<String,Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... datas) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(datas[0]).openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.connect();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader in = new BufferedReader(reader);

                    String readed;
                    while ((readed = in.readLine()) != null) {
                        JSONObject jObject = new JSONObject(readed);
                        String result = jObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                        return jObject;
                    }

                } else {
                    return null;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            // Log.i(TAG,result.toString());
            if (result != null) {
                String iconName = "";
                String nowTemp = "";
                String maxTemp = "";
                String minTemp = "";
                String humidity = "";
                String speed = "";
                String main = "";
                String description = "";

                try {
                    iconName = result.getJSONArray("weather").getJSONObject(0).getString("icon");
                    nowTemp = result.getJSONObject("main").getString("temp");
                    humidity = result.getJSONObject("main").getString("humidity");
                    minTemp = result.getJSONObject("main").getString("temp_min");
                    maxTemp = result.getJSONObject("main").getString("temp_max");
                    speed = result.getJSONObject("wind").getString("speed");
                    main = result.getJSONArray("weather").getJSONObject(0).getString("main");
                    description = result.getJSONArray("weather").getJSONObject(0).getString("description");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ImageView iconimg;
                TextView weather=findViewById(R.id.weather);
                TextView Temp=findViewById(R.id.currentTemp);
                TextView max=findViewById(R.id.max);
                TextView min=findViewById(R.id.min);
                iconimg=findViewById(R.id.iconimg);
                String imageUrl="http://openweathermap.org/img/w/"+iconName+".png";
                Glide.with(weatherActivity.this).load(imageUrl).into(iconimg);
                weather.setText(main);
                Temp.setText("현재온도: "+nowTemp);
                max.setText("최고: "+maxTemp);
                min.setText("최저: "+minTemp);
                //description=transferWeather(description);
                // final String msg = iconimg+"날씨"+main + "습도" + humidity + "%,풍속" + speed + "m/s" + "온도 현재:" + nowTemp + "/최저" + minTemp + "/최고:" + maxTemp;
                // Toast.makeText(MapsActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        }

    }
}


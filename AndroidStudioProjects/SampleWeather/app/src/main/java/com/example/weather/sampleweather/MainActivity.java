package com.example.weather.sampleweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView city, weather, advise, temperature, temperature_range;
    Parameters parameters;
    JSONObject jsonObject, jsonResult;
    JSONArray jsonArray, jsonArrayResult;

    String url = "http://apis.baidu.com/heweather/pro/weather";
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();
    }

    public void InitView() {
        city = (TextView)findViewById(R.id.city);
        weather = (TextView)findViewById(R.id.weather);
        advise = (TextView)findViewById(R.id.advise);
        temperature = (TextView)findViewById(R.id.temperature);
        temperature_range = (TextView)findViewById(R.id.temperature_range);

        SetCityName("北京");          //调用函数

        parameters = new Parameters();
        parameters.put("city", cityName);    //这里城市固定

        ApiStoreSDK.execute(url, ApiStoreSDK.GET, parameters, new ApiCallBack() {

            @Override
            public void onSuccess(int i, String s) {
                Log.i("ApiStoreSDK", "onSuccess");

                try {
                    jsonObject = new JSONObject(s);
                    jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
                    jsonResult = jsonArray.getJSONObject(0);

                    city.setText(jsonResult.getJSONObject("basic").getString("city"));
                    weather.setText(jsonResult.getJSONObject("now").getJSONObject("cond").getString("txt"));
                    temperature.setText(jsonResult.getJSONObject("now").getString("tmp") + "°");
                    advise.setText(jsonResult.getJSONObject("suggestion").getJSONObject("comf").getString("brf"));

                    jsonArrayResult = jsonResult.getJSONArray("daily_forecast");
                    jsonResult = jsonArrayResult.getJSONObject(0).getJSONObject("tmp");

                    temperature_range.setText(jsonResult.getString("max") + "°/" + jsonResult.getString("min") + "°");


                }catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onComplete() {
                Log.i("ApiStoreSDK", "onComplete");
            }

            @Override
            public void onError(int i, String s, Exception e) {
                Log.i("ApiStoreSDK", "onError, status: " + i);
                Log.i("ApiStoreSDK", "errMsg: " + (e == null ? "" : e.getMessage()));


            }
        });
    }

    public void SetCityName(String cityname) {

        this.cityName = cityname;
    }
}

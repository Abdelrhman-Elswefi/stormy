package com.example.elswefi.stormy.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.elswefi.stormy.R;
import com.example.elswefi.stormy.model.CurrentWeather;
import com.example.elswefi.stormy.model.Day;
import com.example.elswefi.stormy.model.Forecast;
import com.example.elswefi.stormy.model.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String DAYS_FORECAST = "DAYS_FORECAST";
    public static final String HOURS_FORECAST = "HOURS_FORECAST";
    private Forecast mForecast;
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.humidityValue)
    TextView mHumidity;
    @BindView(R.id.tempLabel)
    TextView mTemperature;
    @BindView(R.id.timeLabel)
    TextView mTime;
    @BindView(R.id.locationLabel)
    TextView mLocation;
    @BindView(R.id.precipValue)
    TextView mPrecipChance;
    @BindView(R.id.summaryLabel)
    TextView mSummary;
    @BindView(R.id.weatherIconImageView)
    ImageView mWeatherIcon;
    @BindView(R.id.refreshImageView)
    ImageView mRefreshImageView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        final double latitude = 37.8267;
        final double longitude = -122.4233;
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getForecast(latitude, longitude);
                toggleRefresh();
            }
        });

        getForecast(latitude, longitude);
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }

    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "5bf205067672bf5fdf7a5b2b416895c0";

        String forecastUrl = "https://api.darksky.net/forecast/"
                + apiKey + "/" + latitude + "," + longitude;

        if (networkIsAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    alertUserAboutError();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonDate = response.body().string();
                        Log.v(TAG, jsonDate);
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonDate);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught :", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught :", e);
                    }
                }
            });
        } else {
            AlertUserAboutNetwork();
        }
    }

    private void updateDisplay() {
        CurrentWeather mCurrentWeather = mForecast.getCurrentWeathers();
        mTime.setText("At" + mCurrentWeather.getFormattedTime() + "it will be");
        mTemperature.setText(mCurrentWeather.getTemperature() + "");
        mHumidity.setText(mCurrentWeather.getHumidity() + "");
        mSummary.setText(mCurrentWeather.getSummary());
        mPrecipChance.setText(mCurrentWeather.getPrecipProbability() + "%");
        Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
        mWeatherIcon.setImageDrawable(drawable);
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        forecast.setCurrentWeather(getCurrentDetails(jsonData));
        forecast.setDailyForecast(getDailyDetails(jsonData));
        forecast.setHourlyForecast(getHourlyDetails(jsonData));
        return forecast;
    }

    private Hour[] getHourlyDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");
        Hour[] hours = new Hour[data.length()];
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonHours = data.getJSONObject(i);
            Hour hour = new Hour();
            hour.setIcon(jsonHours.getString("icon"));
            hour.setSummary(jsonHours.getString("summary"));
            hour.setTemperature(jsonHours.getDouble("temperature"));
            hour.setTime(jsonHours.getLong("time"));
            hour.setTimezone(forecast.getString("timezone"));
            hours[i] = hour;
        }
        return hours;
    }

    private Day[] getDailyDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");
        Day[] days = new Day[data.length()];
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDays = data.getJSONObject(i);
            Day day = new Day();
            day.setIcon(jsonDays.getString("icon"));
            day.setSummary(jsonDays.getString("summary"));
            day.setTempMax(jsonDays.getDouble("temperatureMax"));
            day.setTempMin(jsonDays.getDouble("temperatureMin"));
            day.setTime(jsonDays.getLong("time"));
            day.setTimezone(forecast.getString("timezone"));
            days[i] = day;
        }
        return days;

    }

    private CurrentWeather getCurrentDetails(String jsonDate) throws JSONException {
        JSONObject forecast = new JSONObject(jsonDate);
        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setPrecipProbability(currently.getDouble("precipProbability"));
        currentWeather.setTimezone(forecast.getString("timezone"));

        return currentWeather;
    }


    private void AlertUserAboutNetwork() {
        NetworkDialogFragment dialogFragment = new NetworkDialogFragment();
//        dialogFragment.setDialogButtonText(getString(R.string.network_dialog_button_text));
//        dialogFragment.setDialogTitle(getString(R.string.network_dialog_title));
//        dialogFragment.setDialogMessage(getString(R.string.network_dialog_message));
        dialogFragment.show(getFragmentManager(), getString(R.string.network_dialog_tag));
    }

    private boolean networkIsAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (info != null && info.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
//        dialogFragment.setDialogButtonText(getString(R.string.dialog_button_text));
//        dialogFragment.setDialogTitle(getString(R.string.errorTitle));
//        dialogFragment.setDialogMessage(getString(R.string.dialogMessage));
        dialogFragment.show(getFragmentManager(), getString(R.string.error_dialog));
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyActivity.class);
        intent.putExtra(DAYS_FORECAST , mForecast.getDailyForecast());
        startActivity(intent);

    }
    @OnClick (R.id.hourlyButton)
    public void startHourlyActivity(View view) {
        Intent intent = new Intent(this, HourlyActivity.class);
        intent.putExtra(HOURS_FORECAST, mForecast.getHourlyForecast());
        startActivity(intent);
    }
}

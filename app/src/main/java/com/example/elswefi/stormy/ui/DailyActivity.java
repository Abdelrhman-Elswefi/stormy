package com.example.elswefi.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.elswefi.stormy.R;
import com.example.elswefi.stormy.model.Day;
import com.example.elswefi.stormy.adapters.DayAdapter;

import java.util.Arrays;

public class DailyActivity extends ListActivity {
    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAYS_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter dayAdapter = new DayAdapter(this, mDays);
        setListAdapter(dayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String dayOfTheWeek = mDays[position].getDayOfTheWeek();
        String summary = mDays[position].getSummary();
        String maxTemp = mDays[position].getTempMax() + "";
        String message = String.format("On %s the high will be %s and it will be %s",
                dayOfTheWeek, maxTemp, summary);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

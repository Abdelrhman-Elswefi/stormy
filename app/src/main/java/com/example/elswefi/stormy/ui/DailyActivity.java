package com.example.elswefi.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.elswefi.stormy.R;
import com.example.elswefi.stormy.model.Day;
import com.example.elswefi.stormy.model.DayAdapter;

import java.util.Arrays;
import java.util.List;

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
}

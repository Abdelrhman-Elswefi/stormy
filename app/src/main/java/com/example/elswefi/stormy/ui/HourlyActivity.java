
package com.example.elswefi.stormy.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.elswefi.stormy.R;
import com.example.elswefi.stormy.adapters.HourAdapter;
import com.example.elswefi.stormy.model.Hour;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyActivity extends AppCompatActivity {
    private Hour[] mHours ;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURS_FORECAST);
        mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);
        HourAdapter hourAdapter = new HourAdapter(this, mHours);
        mRecyclerView.setAdapter(hourAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


    }
}

package com.example.elswefi.stormy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elswefi.stormy.R;
import com.example.elswefi.stormy.model.Hour;

/**
 * Created by elswe on 02-Apr-18 At 4:36 PM.
 */

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    private Hour[] mHours;
    private Context mContext;

    public HourAdapter(Context context,Hour[] hours) {
        mHours = hours;
        mContext = context;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item,
                parent, false);
        return new HourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(mHours[position]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }


    public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTimeLabel;
        private TextView mHourlySummary;
        private TextView mHourlyTemp;
        private ImageView mHourlyIcon;

        public HourViewHolder(View itemView) {
            super(itemView);
            mTimeLabel = itemView.findViewById(R.id.hourLabel);
            mHourlySummary = itemView.findViewById(R.id.hourSummaryLabel);
            mHourlyTemp = itemView.findViewById(R.id.hourTempLabel);
            mHourlyIcon = itemView.findViewById(R.id.hourIcon);

            itemView.setOnClickListener(this);

        }

        public void bindHour(Hour hour) {
            mTimeLabel.setText(hour.getFormattedHour());
            mHourlySummary.setText(hour.getSummary());
            mHourlyTemp.setText(hour.getTemperature());
            mHourlyIcon.setImageResource(hour.getIconId());
        }

        @Override
        public void onClick(View view) {
            String time = mTimeLabel.getText().toString();
            String summary = mHourlySummary.getText().toString();
            String temp = mHourlyTemp.getText().toString();
            String message = String.format("At %s it will be %s and %s");
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

}

package com.example.elswefi.stormy.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elswefi.stormy.R;

/**
 * Created by elswe on 02-Apr-18 At 5:28 AM.
 */

public class DayAdapter extends BaseAdapter {
    private Context mContext;
    private Day[] mDays;

    public DayAdapter(Context context, Day[] days) {
        mContext = context;
        mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int i) {
        return mDays[i];
    }

    @Override
    public long getItemId(int i) {
        return 0; // we aren't gonna use this method, it's used to reference the list items
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.dayIconImage = view.findViewById(R.id.dayIcon);
            holder.dayLabel = view.findViewById(R.id.dayLabel);
            holder.dayMaxTempLabel = view.findViewById(R.id.dayMaxTemp);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Day day = mDays[i];
        holder.dayMaxTempLabel.setText(day.getTempMax() + "");
        holder.dayIconImage.setImageResource(Forecast.getIconId(day.getIcon()));
        if (i == 0) {
            holder.dayLabel.setText("today");
        } else {
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }
        return view;
    }

    public static class ViewHolder {
        ImageView dayIconImage;
        TextView dayMaxTempLabel;
        TextView dayLabel;
    }
}

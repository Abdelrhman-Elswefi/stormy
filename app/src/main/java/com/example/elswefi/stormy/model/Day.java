package com.example.elswefi.stormy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.elswefi.stormy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by elswe on 01-Apr-18 At 6:32 AM.
 */

public class Day implements Parcelable{
    private double mTempMax;
    private double mTempMin;
    private String mIcon;
    private String mSummary;
    private long mTime;
    private String mTimezone;

    public Day() {
    }

    public double getTempMax() {
        return mTempMax;
    }

    public void setTempMax(double tempMax) {
        mTempMax = tempMax;
    }

    public int getTempMin() {
        return (int) Math.round(mTempMin);
    }

    public void setTempMin(double tempMin) {
        mTempMin = tempMin;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }


    public String getDayOfTheWeek() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));
        Date date = new Date(mTime *1000);
        return formatter.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTime);
        parcel.writeString(mSummary);
        parcel.writeString(mIcon);
        parcel.writeString(mTimezone);
        parcel.writeDouble(mTempMax);
        parcel.writeDouble(mTempMin);

    }

    private Day(Parcel in) {
        mTime = in.readLong();
        mSummary = in.readString();
        mIcon = in.readString();
        mTimezone = in.readString();
        mTempMax = in.readDouble();
        mTempMin = in.readDouble();
    }
    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel parcel) {
            return new Day(parcel);
        }

        @Override
        public Day[] newArray(int i) {
            return new Day[i];
        }
    };
}

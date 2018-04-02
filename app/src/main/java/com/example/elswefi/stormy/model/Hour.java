package com.example.elswefi.stormy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by elswe on 01-Apr-18 At 6:27 AM.
 */

public class Hour implements Parcelable {
    private String mSummary;
    private long mTime;
    private String mIcon;
    private String mTimezone;
    private double mTemperature;

    public Hour() {
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

    public String getFormattedHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));
        Date date = new Date(getTime() * 1000);
        return formatter.format(date);
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getIcon() {
        return mIcon;
    }

    public int getIconId() {
       return Forecast.getIconId(mIcon);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel in) {
            return new Hour(in);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSummary);
        parcel.writeLong(mTime);
        parcel.writeString(mIcon);
        parcel.writeString(mTimezone);
        parcel.writeDouble(mTemperature);

    }
    protected Hour(Parcel in) {
        mSummary = in.readString();
        mTime = in.readLong();
        mIcon = in.readString();
        mTimezone = in.readString();
        mTemperature = in.readDouble();
    }
}

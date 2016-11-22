package com.udacity.stockhawk.ui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David on 21/11/2016.
 */
final class HistoryValues implements Parcelable {

    private long timeInMillis;
    private double priceInUSD;

    HistoryValues(long timeInMillis, double priceInUSD) {
        this.timeInMillis = timeInMillis;
        this.priceInUSD = priceInUSD;
    }

    private HistoryValues(Parcel in) {
        timeInMillis = in.readLong();
        priceInUSD = in.readDouble();
    }

    long getTimeInMillis() {
        return timeInMillis;
    }

    double getPriceInUSD() {
        return priceInUSD;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(timeInMillis);
        parcel.writeDouble(priceInUSD);
    }

    public static final Parcelable.Creator<HistoryValues> CREATOR
            = new Parcelable.Creator<HistoryValues>() {
        public HistoryValues createFromParcel(Parcel in) {
            return new HistoryValues(in);
        }

        public HistoryValues[] newArray(int size) {
            return new HistoryValues[size];
        }
    };

}

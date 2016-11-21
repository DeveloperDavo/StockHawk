package com.udacity.stockhawk.ui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David on 21/11/2016.
 */
class HistoryValues implements Parcelable {

    private int time;
    private int price;

    public HistoryValues(int x, int y) {
        this.time = x;
        this.price = y;
    }

    public HistoryValues(Parcel in) {
        time = in.readInt();
        price = in.readInt();
    }

    public int getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(time);
        parcel.writeInt(price);
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

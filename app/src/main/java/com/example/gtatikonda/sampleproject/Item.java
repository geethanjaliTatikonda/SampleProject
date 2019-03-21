package com.example.gtatikonda.sampleproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gtatikonda on 3/20/2019.
 */

public class Item implements Parcelable{
    public String title;
    public boolean isActive;

    public Item() {
    }

    protected Item(Parcel in) {
        title = in.readString();
        isActive = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeByte((byte) (isActive ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}

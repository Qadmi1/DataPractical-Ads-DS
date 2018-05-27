package com.example.appty.datapractical1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by appty on 05/05/18.
 */

public class Workout implements Parcelable{

    private String title;
    private String sets;
    private String reps;
    private String desc;

    public Workout(String title, String sets, String reps, String desc) {
        this.title = title;
        this.sets = sets;
        this.reps = reps;
        this.desc = desc;
    }

    protected Workout(Parcel in) {
        title = in.readString();
        sets = in.readString();
        reps = in.readString();
        desc = in.readString();
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getSets() {
        return sets;
    }

    public String getReps() {
        return reps;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(sets);
        parcel.writeString(reps);
        parcel.writeString(desc);
    }
}

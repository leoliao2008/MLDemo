package com.tgi.mldemo.bean;

public class DietLog {
    private String mName;
    private long mDate;
    private String mThumbNail;
    private double mWeight;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        this.mDate = date;
    }

    public String getThumbNail() {
        return mThumbNail;
    }

    public void setThumbNail(String thumbNail) {
        mThumbNail = thumbNail;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double weight) {
        mWeight = weight;
    }
}

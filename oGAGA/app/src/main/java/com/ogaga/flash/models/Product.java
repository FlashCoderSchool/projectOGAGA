package com.ogaga.flash.models;

/**
 * Created by Kanet on 4/13/2016.
 */
public class Product {
    private long mId;
    private String mProducer;
    private String mName;
    private String mOrigin;
    private String mImage;
    private String mPrice;
    private String mStatus;

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmProducer() {
        return mProducer;
    }

    public void setmProducer(String mProducer) {
        this.mProducer = mProducer;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmOrigin() {
        return mOrigin;
    }

    public void setmOrigin(String mOrigin) {
        this.mOrigin = mOrigin;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    //To String

    @Override
    public String toString() {
        return "Product{" +
                "mId=" + mId +
                ", mProducer='" + mProducer + '\'' +
                ", mName='" + mName + '\'' +
                ", mOrigin='" + mOrigin + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", mStatus='" + mStatus + '\'' +
                '}';
    }
}

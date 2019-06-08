package com.example.slatielly.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String addressStorage;
    private String downloadLink;

    public Image() {

    }

    protected Image(Parcel in) {
        addressStorage = in.readString();
        downloadLink = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getaddressStorage() {
        return addressStorage;
    }

    public void setaddressStorage(String addressStorage) {
        this.addressStorage = addressStorage;
    }

    public String getdownloadLink() {
        return downloadLink;
    }

    public void setdownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressStorage);
        dest.writeString(downloadLink);
    }
}

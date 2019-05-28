package com.example.slatielly.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private int cep;
    private String city;
    private String neighborhood;
    private String street;
    private int number;
    private String complement;

    public Address() {

    }

    public Address(int cep, String city, String neighborhood, String street, int number, String complement) {
        this.cep = cep;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.number = number;
        this.complement = complement;
    }

    protected Address(Parcel in) {
        cep = in.readInt();
        city = in.readString();
        neighborhood = in.readString();
        street = in.readString();
        number = in.readInt();
        complement = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cep);
        dest.writeString(city);
        dest.writeString(neighborhood);
        dest.writeString(street);
        dest.writeInt(number);
        dest.writeString(complement);
    }
}

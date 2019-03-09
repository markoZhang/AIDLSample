package com.example.marko.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Marko
 * @date 2019/3/9
 */

public class Book implements Parcelable {

    private String name;

    public Book(String name) {
        this.name = name;
    }
    public Book() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "book name" + name;
    }

    protected Book(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
    }

    public void readFromParcel(Parcel reply) {
        name = reply.readString();
    }

}

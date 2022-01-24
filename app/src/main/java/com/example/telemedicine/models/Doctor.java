package com.example.telemedicine.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable
{
    private int id;
    private String name;
    private float rating;
    private String specialty;
    private String address;
    private String about;
    private String base64photo;

    public Doctor(int id, String name, float rating, String specialty, String address,
                  String about, String base64photo)
    {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.specialty = specialty;
        this.address = address;
        this.about = about;
        this.base64photo = base64photo;
    }

    public Doctor()
    {

    }

    protected Doctor(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        rating = in.readFloat();
        specialty = in.readString();
        address = in.readString();
        about = in.readString();
        base64photo = in.readString();
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>()
    {
        @Override
        public Doctor createFromParcel(Parcel in)
        {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size)
        {
            return new Doctor[size];
        }
    };

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getSpecialty()
    {
        return specialty;
    }

    public void setSpecialty(String specialty)
    {
        this.specialty = specialty;
    }

    public float getRating()
    {
        return rating;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAbout()
    {
        return about;
    }

    public void setAbout(String about)
    {
        this.about = about;
    }

    public String getBase64photo()
    {
        return base64photo;
    }

    public void setBase64photo(String base64photo)
    {
        this.base64photo = base64photo;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeFloat(rating);
        dest.writeString(specialty);
        dest.writeString(address);
        dest.writeString(about);
        dest.writeString(base64photo);
    }
}

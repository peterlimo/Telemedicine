package com.example.telemedicine.Interfaces;

import com.example.telemedicine.models.Doctor;

import java.util.ArrayList;

public interface OnGetDocListListener
{
    void onGetDocListSuccess(ArrayList<Doctor> doctors);
}

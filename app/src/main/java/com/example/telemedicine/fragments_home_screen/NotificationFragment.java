package com.example.telemedicine.fragments_home_screen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.telemedicine.Interfaces.OnGetDoctorListener;
import com.example.telemedicine.R;
import com.example.telemedicine.helpers.Base64Handler;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.example.telemedicine.models.Doctor;
import com.example.telemedicine.models.UserConsultationRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationFragment
        extends Fragment
        implements OnGetDoctorListener
{
    private TextView userName, disease,
            address, description;
    private CircleImageView avatar;
    private TextView doctorName, specialty, rating;
    private ArrayList<ImageView> stars = new ArrayList<>();
    private HttpRequestSender httpRequestSender;

    public NotificationFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        userName = view.findViewById(R.id.tv_name_output);
        disease = view.findViewById(R.id.tv_desease_output);
        address = view.findViewById(R.id.tv_location_output);
        description = view.findViewById(R.id.tv_description_output);
        avatar = view.findViewById(R.id.civ_avatar);
        doctorName = view.findViewById(R.id.tv_name);
        specialty = view.findViewById(R.id.tv_specialty);
        rating = view.findViewById(R.id.tv_rating);

        stars.add((ImageView) view.findViewById(R.id.iv_star0));
        stars.add((ImageView) view.findViewById(R.id.iv_star1));
        stars.add((ImageView) view.findViewById(R.id.iv_star2));
        stars.add((ImageView) view.findViewById(R.id.iv_star3));
        stars.add((ImageView) view.findViewById(R.id.iv_star4));

        httpRequestSender = new HttpRequestSender();
        httpRequestSender.setOnGetDoctorListener(this);

        setFields();

        return view;
    }

    private void setFields()
    {
        httpRequestSender.getDoc(getActivity());
        userName.setText(UserConsultationRequest.getName());
        disease.setText(UserConsultationRequest.getDisease());
        address.setText(UserConsultationRequest.getAddress());
        description.setText(UserConsultationRequest.getDescription());
    }

    private void setStars(Doctor doctor)
    {
        float float_rating = doctor.getRating();
        int rating = (int) Math.ceil(float_rating);
        for (int i = 0; i < 5; i++)
        {
            stars.get(i).setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < rating; i++)
        {
            stars.get(i).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetDoctorSuccess(Doctor doctor)
    {
        doctorName.setText(doctor.getName());
        specialty.setText(doctor.getSpecialty());
        rating.setText(String.valueOf(doctor.getRating()));
        setStars(doctor);
        Base64Handler base64Handler = new Base64Handler();
        avatar.setImageBitmap(base64Handler.
                base64ToBitmap(doctor.getBase64photo()));
    }
}

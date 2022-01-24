package com.example.telemedicine.fragments_home_screen;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telemedicine.R;
import com.example.telemedicine.helpers.Base64Handler;
import com.example.telemedicine.models.Doctor;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorDetailsFragment extends Fragment
{
    private Doctor doctor = new Doctor();
    private CircleImageView avatar;
    private TextView name, specialty,
            rating, about, address;
    private ArrayList<ImageView> stars;


    public DoctorDetailsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            doctor = bundle.getParcelable("clickedDoctor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_details, container, false);
        stars = new ArrayList<>();

        avatar = view.findViewById(R.id.civ_avatar);
        name = view.findViewById(R.id.tv_name);
        specialty = view.findViewById(R.id.tv_specialty);

        stars.add((ImageView) view.findViewById(R.id.iv_star0));
        stars.add((ImageView) view.findViewById(R.id.iv_star1));
        stars.add((ImageView) view.findViewById(R.id.iv_star2));
        stars.add((ImageView) view.findViewById(R.id.iv_star3));
        stars.add((ImageView) view.findViewById(R.id.iv_star4));

        rating = view.findViewById(R.id.tv_rating);
        about = view.findViewById(R.id.tv_description_output);
        address = view.findViewById(R.id.tv_location_output);
        setFields();
        return view;
    }

    private void setStars()
    {
        float float_rating = doctor.getRating();
        int rating = (int) Math.ceil(float_rating);
        for (int i = 0; i < 5; i++){
            stars.get(i).setVisibility(View.INVISIBLE);
        }
        for(int i = 0; i < rating; i++){
            stars.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void setFields()
    {
        name.setText(doctor.getName());
        specialty.setText(doctor.getSpecialty());
        rating.setText(String.valueOf(doctor.getRating()));
        about.setText(doctor.getAbout());
        address.setText(doctor.getAddress());
        setStars();
        Base64Handler base64Handler = new Base64Handler();
        avatar.setImageBitmap(base64Handler.
                base64ToBitmap(doctor.getBase64photo()));
    }
}

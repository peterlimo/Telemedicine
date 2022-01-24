package com.example.telemedicine.fragments_home_screen;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemedicine.Interfaces.CardOnClickListener;
import com.example.telemedicine.Interfaces.IHomeFragment;
import com.example.telemedicine.Interfaces.OnGetDocListListener;
import com.example.telemedicine.R;
import com.example.telemedicine.helpers.HomeRecyclerViewAdapter;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.example.telemedicine.models.Doctor;

import java.util.ArrayList;

public class HomeFragment
        extends Fragment
        implements CardOnClickListener, OnGetDocListListener
{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerViewAdapter recyclerViewAdapter;
    private IHomeFragment iHomeFragment;
    private HttpRequestSender httpRequestSender;

    public HomeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d("log", "HomeFragment: onCreateView");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.rv_doctor_list);

        httpRequestSender = new HttpRequestSender();
        httpRequestSender.setOnGetDocListListener(this);

        httpRequestSender.getDocList(getActivity());

        return view;
    }

    @Override
    public void onClick(Doctor doctor)
    {
        if (iHomeFragment != null)
        {
            iHomeFragment.onCardClick(doctor);
        }
    }

    public void setIHomeFragment(IHomeFragment iHomeFragment)
    {
        this.iHomeFragment = iHomeFragment;
    }

    @Override
    public void onGetDocListSuccess(ArrayList<Doctor> doctors)
    {
        if (recyclerViewAdapter == null)
        {
            Log.d("log", "initRecyclerView: init recyclerView.");
            linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerViewAdapter = new HomeRecyclerViewAdapter(getActivity(), doctors);
            recyclerViewAdapter.setCardOnClickListener(this);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }
}

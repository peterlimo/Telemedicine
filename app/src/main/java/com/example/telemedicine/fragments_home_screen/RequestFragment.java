package com.example.telemedicine.fragments_home_screen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.telemedicine.Interfaces.IRequestFragment;
import com.example.telemedicine.Interfaces.OnUserConsultationRequestListener;
import com.example.telemedicine.R;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.example.telemedicine.models.UserConsultationRequest;


public class RequestFragment
        extends Fragment
        implements View.OnClickListener, OnUserConsultationRequestListener
{
    private EditText name, disease,
            address, description;
    private Button request;
    private HttpRequestSender httpRequestSender;
    private IRequestFragment iRequestFragment = null;

    public RequestFragment()
    {
        // Required empty public constructor
    }

    public void setIRequestFragment(IRequestFragment iRequestFragment)
    {
        this.iRequestFragment = iRequestFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        name = view.findViewById(R.id.et_name);
        disease = view.findViewById(R.id.et_desease);
        address = view.findViewById(R.id.et_location);
        description = view.findViewById(R.id.et_description);
        request = view.findViewById(R.id.btn_request);

        request.setOnClickListener(this);

        httpRequestSender = new HttpRequestSender();
        httpRequestSender.setConsultationRequestListener(this);

        return view;
    }

    private void getFilledFields()
    {
        UserConsultationRequest.setConsId(1);
//        UserConsultationRequest.setName("1test");
//        UserConsultationRequest.setDisease("vederea");
//        UserConsultationRequest.setAddress("1test");
//        UserConsultationRequest.setDescription("1test");
        UserConsultationRequest.setName(name.getText().toString());
        UserConsultationRequest.setDisease(disease.getText().toString());
        UserConsultationRequest.setAddress(address.getText().toString());
        UserConsultationRequest.setDescription(description.getText().toString());
        UserConsultationRequest.setDocId(1);
        UserConsultationRequest.setIsConfirmed(false);
    }

    @Override
    public void onClick(View v)
    {
        getFilledFields();
        httpRequestSender.userRequestConsultation(getActivity());
    }

    @Override
    public void onUserConsultationRequestSuccess()
    {
        Toast.makeText(getActivity(),
                "request successful",
                Toast.LENGTH_SHORT).show();
        if (iRequestFragment != null)
            iRequestFragment.onSuccessfulRequest();
    }

    @Override
    public void onUserConsultationRequestFailure()
    {
        Toast.makeText(getActivity(),
                "An error occurred",
                Toast.LENGTH_SHORT).show();
    }
}

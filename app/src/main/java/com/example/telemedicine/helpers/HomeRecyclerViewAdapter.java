package com.example.telemedicine.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemedicine.Interfaces.CardOnClickListener;
import com.example.telemedicine.R;
import com.example.telemedicine.models.Doctor;

import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends
        RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<Doctor> doctors;
    private CardOnClickListener cardOnClickListener;

    public HomeRecyclerViewAdapter(Context context, ArrayList<Doctor> doctors)
    {
        this.context = context;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d("log", "onCreateViewHolder");

        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.layout_doctor_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        Log.d("log", "onBindViewHolder");

        holder.name.setText(doctors.get(position).getName());
        holder.rating.setText(String.valueOf(doctors.get(position).getRating()));
        holder.specialty.setText(doctors.get(position).getSpecialty());
        holder.address.setText(doctors.get(position).getAddress());

        Base64Handler base64Handler = new Base64Handler();
        holder.avatar.setImageBitmap(base64Handler.
                base64ToBitmap(doctors.get(position).getBase64photo()));

//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.ic_launcher_background);

//        Glide.with(context).
//                load(R.drawable.avatar)
//                .apply(requestOptions)
//                .into(holder.avatar);

        holder.doctorCard.setOnClickListener(new CardView.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("log", "onClick: clicked on: " +
                        doctors.get(position).getName() +
                        " position " + position);
                if (cardOnClickListener != null)
                {
                    cardOnClickListener.onClick(doctors.get(position));
                    Log.d("log", "cardOnClickListener != null");
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return doctors.size();
    }

    public void setCardOnClickListener(CardOnClickListener cardOnClickListener)
    {
        this.cardOnClickListener = cardOnClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView avatar, star;
        public TextView name, rating;

        public TextView specialty;

        public ImageView icon;
        public TextView address;

        public CardView doctorCard;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            avatar = itemView.findViewById(R.id.civ_avatar);
            star = itemView.findViewById(R.id.iv_star);
            name = itemView.findViewById(R.id.tv_name);
            rating = itemView.findViewById(R.id.tv_rating);
            specialty = itemView.findViewById(R.id.tv_specialty);
            icon = itemView.findViewById(R.id.iv_icon);
            address = itemView.findViewById(R.id.tv_address);
            doctorCard = itemView.findViewById(R.id.cv_doctor_card);
        }
    }
}

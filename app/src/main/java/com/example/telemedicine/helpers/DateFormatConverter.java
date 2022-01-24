package com.example.telemedicine.helpers;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatConverter
{
    public String convert(String date)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date formatted_date;
        try
        {
            formatted_date = originalFormat.parse(date);
            return targetFormat.format(formatted_date);

        } catch (ParseException ex)
        {
            // Handle Exception.
        }
        return date;
    }
}

package com.example.telemedicine.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Base64Handler
{

    public String bitmapToBase64(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 75, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    public Bitmap base64ToBitmap(String string)
    {
        byte[] byteArray = Base64.decode(string, Base64.DEFAULT);
        string.replaceAll("\\s+","");

        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}

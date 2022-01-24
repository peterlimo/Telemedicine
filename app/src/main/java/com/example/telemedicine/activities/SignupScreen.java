package com.example.telemedicine.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.telemedicine.Interfaces.OnRegListener;
import com.example.telemedicine.R;
import com.example.telemedicine.helpers.Base64Handler;
import com.example.telemedicine.helpers.DateFormatConverter;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.example.telemedicine.models.User;

import java.io.IOException;

import static com.example.telemedicine.models.Constants.PICK_IMAGE;


public class SignupScreen
        extends AppCompatActivity
        implements View.OnClickListener, OnRegListener
{

    private Toolbar toolbar;
    private Button next, addPhotos;
    private EditText fullName, birthday, email,
            phone, location, username, password;
    private HttpRequestSender httpRequestSender;
    private DateFormatConverter dateFormatConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar = findViewById(R.id.toolbar);
        addPhotos = findViewById(R.id.btn_add_photos);
        fullName = findViewById(R.id.et_full_name);
        birthday = findViewById(R.id.et_birthday);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_phone_number);
        location = findViewById(R.id.et_location);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        next = findViewById(R.id.btn_next);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        addPhotos.setOnClickListener(this);
        next.setOnClickListener(this);

        httpRequestSender = new HttpRequestSender();
        httpRequestSender.setOnRegListener(this);

        dateFormatConverter = new DateFormatConverter();
    }

    private void goToWelcomeScreen()
    {
        Intent intent = new
                Intent(SignupScreen.this, WelcomeScreen.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finishAffinity();
    }

    private void getFilledFields()
    {
        User.setFullName(fullName.getText().toString());

        String date = birthday.getText().toString();
        String formattedDate = dateFormatConverter.convert(date);

        User.setBirthday(formattedDate);
        User.setEmail(email.getText().toString());
        User.setPhone(phone.getText().toString());
        User.setAddress(location.getText().toString());
        User.setUsername(username.getText().toString());
        User.setPassword(password.getText().toString());

//        User.setBase64photo("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");
//        User.setFullName("4test");
//        User.setBirthday("11/11/1990");
//        User.setEmail("4test@mail.ru");
//        User.setPhone("1111");
//        User.setAddress("4test");
//        User.setUsername("4test");
//        User.setPassword("4test");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case (R.id.btn_next):
                getFilledFields();
                if (allFieldsFilled())
                    httpRequestSender.reg(this);
                else
                    Toast.makeText(this,
                            "Please, fill all fields",
                            Toast.LENGTH_SHORT).show();
                break;
            case (R.id.btn_add_photos):
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Picture"),
                        PICK_IMAGE);
                break;
        }
    }

    private boolean allFieldsFilled()
    {
        boolean result = true;
        if (fullName.getText() == null || fullName.getText().toString().isEmpty())
        {
            result = false;
            fullName.setError("Full name is required");
        }
        if (birthday.getText() == null || birthday.getText().toString().isEmpty())
        {
            result = false;
            birthday.setError("Birthday is required");
        }
        if (email.getText() == null || email.getText().toString().isEmpty())
        {
            result = false;
            email.setError("Email is required");
        }
        if (phone.getText() == null || phone.getText().toString().isEmpty())
        {
            result = false;
            phone.setError("Phone number is required");
        }
        if (location.getText() == null || location.getText().toString().isEmpty())
        {
            result = false;
            location.setError("Location is required");
        }
        if (username.getText() == null || username.getText().toString().isEmpty())
        {
            result = false;
            username.setError("Username is required");
        }
        if (password.getText() == null || password.getText().toString().isEmpty())
        {
            result = false;
            password.setError("Password is required");
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE)
        {
            if (data != null)
            {
                Uri selectedImage = data.getData();
                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    Base64Handler base64Handler = new Base64Handler();
                    User.setBase64photo(base64Handler.bitmapToBase64(bitmap));
                } catch (IOException e)
                {
                    Log.i("TAG", "SignUpScreen encode to base64 problem" + e);
                }
            }
        }
    }

    @Override
    public void onRegSuccess()
    {
        Toast.makeText(this,
                "Registration successful",
                Toast.LENGTH_SHORT).show();
        goToWelcomeScreen();
    }

    @Override
    public void onRegFailure()
    {
        Toast.makeText(this,
                "An error occurred",
                Toast.LENGTH_SHORT).show();
    }

}

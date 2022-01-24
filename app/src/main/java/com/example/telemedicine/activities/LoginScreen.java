package com.example.telemedicine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telemedicine.Interfaces.OnLoginListener;
import com.example.telemedicine.R;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.example.telemedicine.models.User;

public class LoginScreen extends AppCompatActivity
        implements View.OnClickListener, OnLoginListener
{

    private Button loginBtn, signUpBtn;
    private EditText email, password;
    private HttpRequestSender httpRequestSender;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.btn_login);
        signUpBtn = findViewById(R.id.btn_sign_up);

        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);

        httpRequestSender = new HttpRequestSender();
        httpRequestSender.setOnLoginListener(this);
    }

    private void getFilledFields()
    {
        User.setEmail(email.getText().toString());
        User.setPassword(password.getText().toString());
//        User.setEmail("1test@mail.ru");
//        User.setPassword("1test");
    }

    private void goToHomeScreen()
    {
        Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finishAffinity();
    }

    private void goToSignupScreen()
    {
        Intent intent = new Intent(LoginScreen.this, SignupScreen.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case (R.id.btn_login):
                getFilledFields();
                httpRequestSender.auth(this);
                break;
            case (R.id.btn_sign_up):
                goToSignupScreen();
                break;
        }
    }

    @Override
    public void onLoginSuccess()
    {
        goToHomeScreen();
    }

    @Override
    public void onLoginFailure()
    {
        Toast.makeText(this,
                "An error occurred",
                Toast.LENGTH_SHORT).show();
    }

}

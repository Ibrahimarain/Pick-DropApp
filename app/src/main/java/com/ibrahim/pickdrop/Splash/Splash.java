package com.ibrahim.pickdrop.Splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ibrahim.pickdrop.CustomClasses.LocalStoragePreferences;
import com.ibrahim.pickdrop.Login.view.LoginActivity;
import com.ibrahim.pickdrop.MapsActivity;
import com.ibrahim.pickdrop.R;

public class Splash extends AppCompatActivity {

    LocalStoragePreferences localStoragePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        localStoragePreferences = LocalStoragePreferences.getInstance(this);

        if (localStoragePreferences.isLoggedIn()){
            startActivity(new Intent(Splash.this, MapsActivity.class));
            finish();
        }
        else {

            startActivity(new Intent(Splash.this, LoginActivity.class));
            finish();


        }
    }
}

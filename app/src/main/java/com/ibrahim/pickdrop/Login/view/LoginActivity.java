package com.ibrahim.pickdrop.Login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ibrahim.pickdrop.CustomClasses.Constants;
import com.ibrahim.pickdrop.CustomClasses.LocalStoragePreferences;
import com.ibrahim.pickdrop.MapsActivity;
import com.ibrahim.pickdrop.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText edittext_companycode,edittext_username,edittext_password;
    LovelyProgressDialog lovelyProgressDialog;
    ImageView companylogo;
    ImageView homeLogo;
    RelativeLayout companylogoLayout;
    LocalStoragePreferences localStoragePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_login = findViewById(R.id.btn_login);
        edittext_companycode = findViewById(R.id.edittext_companycode);
        edittext_username = findViewById(R.id.edittext_username);
        edittext_password = findViewById(R.id.edittext_password);
        lovelyProgressDialog = new LovelyProgressDialog(this);
        lovelyProgressDialog.setCancelable(false);
        lovelyProgressDialog.setTopColorRes(R.color.colorPrimary);
        lovelyProgressDialog.setIcon(R.drawable.ic_home_white_24dp);
        companylogo = findViewById(R.id.companylogo);
        homeLogo = findViewById(R.id.homeLogo);
        companylogoLayout = findViewById(R.id.companylogoLayout);
        btn_login.setEnabled(false);
        localStoragePreferences = LocalStoragePreferences.getInstance(this);


        if (!localStoragePreferences.getCompanyCode().equals("")){
            btn_login.setEnabled(true);
            edittext_companycode.setText(localStoragePreferences.getCompanyCode());
        }


        edittext_companycode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    lovelyProgressDialog.setTitle("Please Wait");
                    lovelyProgressDialog.show();
                    sendCompanyCodeRequest(edittext_companycode.getText().toString());

                }
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lovelyProgressDialog.setTitle("Logging in..");
                lovelyProgressDialog.show();
                sendLoginRequest(edittext_companycode.getText().toString(),
                        edittext_username.getText().toString(),
                        edittext_password.getText().toString());
            }
        });

    }


    void sendCompanyCodeRequest (final String companyCode){
        if (companyCode.length() <3 ){
            Toast.makeText(this, "CompanyCode short", Toast.LENGTH_SHORT).show();
            lovelyProgressDialog.dismiss();
        }else {

            String urlCompany = "http://" + companyCode + ".trackdrives.com/Mobile/Type/CompanyCode/?c=" + companyCode;
            Toast.makeText(this, "CompanyCode", Toast.LENGTH_SHORT).show();
            AsyncHttpClient client = new AsyncHttpClient();

            client.post(urlCompany, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    if (response.startsWith("http:")) {
                        lovelyProgressDialog.dismiss();
                        homeLogo.setVisibility(View.GONE);
                        companylogo.setVisibility(View.VISIBLE);
                        Glide.with(LoginActivity.this).load(response).into(companylogo);
                        btn_login.setEnabled(true);
                        companylogoLayout.setBackgroundColor(getResources().getColor(R.color.mainbgupper));
                        localStoragePreferences.saveCompanyCode(companyCode);


                    }else {
                        lovelyProgressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }
//login
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(LoginActivity.this, "Something Failure", Toast.LENGTH_SHORT).show();
                    lovelyProgressDialog.dismiss();
                }
            });

        }

    }
    void sendLoginRequest (String companyCode, final String username , String password){

        String url = "http://" +companyCode+".trackdrives.com/Mobile/Type/Login/";

        Log.i("resp", "sendLoginRequest: " + url);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        requestParams.put("u",username);
        requestParams.put("p",password);

        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String resp  = new String (responseBody);
                Log.i("resp", "onSuccess: " + resp);
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String response =  jsonObject.getString("response");
                    if (response.equalsIgnoreCase("SUCCESS")){
                        localStoragePreferences.saveUserName(username);
                        localStoragePreferences.saveIsLoggedIn(true);
                        parseData(resp);

                        startActivity(new Intent(LoginActivity.this,MapsActivity.class));
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, ""+resp, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

//                String resp  = new String (responseBody);
//                Toast.makeText(LoginActivity.this, ""+ resp, Toast.LENGTH_SHORT).show();
                Log.i("resp", "onFailure: " );



            }
        });

    }

    void parseData(String json){

        try {
            JSONObject mainObject = new JSONObject(json);
            int userType = mainObject.getInt("UserType");
            localStoragePreferences.saveUserType(userType);
            JSONObject dataObj = mainObject.getJSONObject("data");

            String dataString = dataObj.toString();
            localStoragePreferences.saveUserData(dataString);

            if (userType == Constants.USER_TYPE_DRIVER){


            }else if (userType == Constants.USER_TYPE_EMPLOYEE){


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





}

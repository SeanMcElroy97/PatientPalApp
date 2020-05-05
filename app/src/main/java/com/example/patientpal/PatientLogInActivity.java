package com.example.patientpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.patientpal.services.TokenHandler;
import com.example.patientpal.services.VolleySingletonRequestQueue;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import maes.tech.intentanim.CustomIntent;

public class PatientLogInActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    ProgressBar progressBar;

    RequestQueue mRequestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_log_in);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);

        progressBar = findViewById(R.id.PatientLogInProgressBar);



    }

    public void finish(){
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    public void signUpActivity(View v){
        finish();
    }

    public void patientLogIn(View v){
        String email = textInputEmail.getEditText().getText().toString().trim();
        String password = textInputPassword.getEditText().getText().toString().trim();


//        if(!validateEmail(email) || !validatePassword(password)){
//            return;
//        }
//        Intent intentToHome = new Intent(PatientLogInActivity.this, PatientHome.class);
//        startActivity(intentToHome);


        //Request Object to Spring boot backend

        org.json.JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRequestQueue = VolleySingletonRequestQueue.getInstance(this).getRequestQueue();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.spring_boot_url) + "/authenticate", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


//                Gson g = new Gson();
//                String responseStr  = g.fromJson(response);

                try {
                    TokenHandler.setToken(response.getString("jwt"));
                    Toast.makeText(getApplicationContext(), "Response: " + response.getString("jwt"), Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(PatientLogInActivity.this, PatientHome.class);
                    startActivity(loginIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Or boolean
                //JWT will be
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Invalid Credentials" , Toast.LENGTH_SHORT).show();
            }
        });


        mRequestQueue.add(postRequest);


    }


    private boolean validateEmail(String email){

        if(email.isEmpty()){
            textInputEmail.setError("Email can't be empty");
            return false;
        }else if(!email.contains("@")){
            textInputEmail.setError("Not valid email. (please include '@')");
            return false;
        }else{
            textInputEmail.setError(null);
            return true;
        }

    }

    private boolean validatePassword(String pword){

        if(pword.isEmpty()){
            textInputPassword.setError("Password can't be empty");
            return false;
        }else if(pword.length()<6){
            textInputPassword.setError("Password must be at least 6 characters");
            return false;
        }else{
            textInputPassword.setError(null);
            return true;
        }

    }
}

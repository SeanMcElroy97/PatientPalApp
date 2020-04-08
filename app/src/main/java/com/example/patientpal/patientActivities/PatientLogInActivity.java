package com.example.patientpal.patientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.patientpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import maes.tech.intentanim.CustomIntent;

public class PatientLogInActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    ProgressBar progressBar;

    FirebaseAuth fbauthObj;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_log_in);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);

        progressBar = findViewById(R.id.PatientLogInProgressBar);

        fbauthObj= FirebaseAuth.getInstance();
        //db = FirebaseFirestore.getInstance();

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
        //Toast.makeText(this, "Tedt" +email + password,Toast.LENGTH_LONG).show();


        if(!validateEmail(email) || !validatePassword(password)){
            return;
        }
        Intent intentToHome = new Intent(PatientLogInActivity.this, PatientHome.class);
        startActivity(intentToHome);
//        progressBar.setVisibility(View.VISIBLE);
        //Called onComplete listener when task is complete
//        fbauthObj.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                progressBar.setVisibility(View.INVISIBLE);
//
//                if(task.isSuccessful()){
//                    //System.out.println(fbauthObj.getCurrentUser().getEmail());
//                    Intent intentToHome = new Intent(PatientLogInActivity.this, PatientHome.class);
//                    //intentToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intentToHome);
//                }else{
//                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
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

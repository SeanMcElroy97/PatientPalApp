package com.example.patientpal.patientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.patientpal.R;
import com.example.patientpal.model.PatientUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import maes.tech.intentanim.CustomIntent;

public class PatientSignUpActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPhoneNumber;
    private TextInputLayout textInputFirstName;
    private TextInputLayout textInputSecondName;
    private TextInputLayout textInputPassword;

    private FirebaseFirestore db;
    private FirebaseAuth auth;


    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);

        progressBar = findViewById(R.id.PatientSignUpProgressBar);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPhoneNumber = findViewById(R.id.text_input_phone_number);
        textInputFirstName = findViewById(R.id.text_input_firstname);
        textInputSecondName = findViewById(R.id.text_input_surname);
        textInputPassword = findViewById(R.id.text_input_password);


        //Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        //go to homepage
        //updateUI(currentUser);
    }

    public void finish(){
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    public void LoginInstead(View v){
        Intent intentToPatientLogin = new Intent(this, PatientLogInActivity.class);
        startActivity(intentToPatientLogin);
        CustomIntent.customType(this, "left-to-right");
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

    private boolean validateFirstName(String fName){

        if(fName.isEmpty()){
            textInputFirstName.setError("Can't be empty");
            return false;
        }else if(!fName.matches("[a-zA-Z]+")){
            textInputFirstName.setError("Invalid Name");
            return false;
        }else{
            textInputFirstName.setError(null);
            return true;
        }

    }

    private boolean validateLastName(String lName){

        if(lName.isEmpty()){
            textInputSecondName.setError("Can't be empty");
            return false;
        }else if(!lName.matches("[a-zA-Z]+")){
            textInputSecondName.setError("Invalid Name");
            return false;
        }else{
            textInputSecondName.setError(null);
            return true;
        }

    }

    private boolean validatePhoneNumber(String phoneNumber){

        if(phoneNumber.isEmpty()){
            textInputPhoneNumber.setError("Can't be empty");
            return false;
        }else if(!phoneNumber.matches("[0-9]+")){
            textInputPhoneNumber.setError("Numbers only");
            return false;
        }else if(phoneNumber.length()!=10){
            textInputPhoneNumber.setError("Must be 10 digits");
            return false;
        }else{
            textInputPhoneNumber.setError(null);
            return true;
        }

    }



    public void signUp(View v){

       String firstNameInput = textInputFirstName.getEditText().getText().toString().trim();
       String secondNameInput = textInputSecondName.getEditText().getText().toString().trim();
       String emailInput = textInputEmail.getEditText().getText().toString().trim();
       String phoneNumber = textInputPhoneNumber.getEditText().getText().toString().trim();
       String password = textInputPassword.getEditText().getText().toString().trim();

        if(!validateEmail(emailInput) || !validatePassword(password) || !validateFirstName(firstNameInput) || !validateLastName(secondNameInput) || !validatePhoneNumber(phoneNumber)) {
            return;
        }

            final CollectionReference dbPatientUsers = db.collection("patientUsers");

            final PatientUser pUser = new PatientUser(firstNameInput, secondNameInput, emailInput, phoneNumber, password);

            progressBar.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(pUser.getEmail(), pUser.getPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {


                                dbPatientUsers.add(pUser);
                                progressBar.setVisibility(View.GONE);
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(PatientSignUpActivity.this, "User Registered Successful.", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = auth.getCurrentUser();


                                Intent signUpToHomeIntent = new Intent(PatientSignUpActivity.this, PatientHome.class);
                                signUpToHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(signUpToHomeIntent);


                            } else {

                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    textInputEmail.setError("Existing account with email address");
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });


//       //Firestore db
//        dbPatientUsers.add(pUser)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(PatientSignUpActivity.this, "User Saved", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(PatientSignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//      //Toast.makeText(this, "GUCCI", Toast.LENGTH_LONG).show();


    }


}

package com.example.patientpal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.Date;

import maes.tech.intentanim.CustomIntent;

public class PatientSignUpActivity extends AppCompatActivity {

    private TextInputLayout textInputFirstName;
    private TextInputLayout textInputSurname;
    private TextInputLayout textInputEmail;
    private CountryCodePicker mCountryCodePicker;
    private TextInputLayout textInputPhoneNumber;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputPPSNumber;
    private TextInputLayout textInputAddress;

    private Button dateOfBirthBtn;
    private DatePickerDialog.OnDateSetListener mDatePickerListener;
    private TextView dateOfBirthTxtView;
    private Date dateOfBirth;

    private RadioGroup mRadioGroupGender;


    Button mSignUpBtn;

    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);

       // progressBar = findViewById(R.id.PatientSignUpProgressBar);

        textInputFirstName = findViewById(R.id.text_input_firstname);
        textInputSurname = findViewById(R.id.text_input_surname);
        textInputEmail = findViewById(R.id.text_input_email);
        mCountryCodePicker = findViewById(R.id.countryCodePrefix);
        textInputPhoneNumber = findViewById(R.id.text_input_phone_number);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputPPSNumber = findViewById(R.id.text_input_ppsNumber);
        textInputAddress = findViewById(R.id.text_input_address);
        dateOfBirthBtn = findViewById(R.id.chooseDateOfBirthButton);
        dateOfBirthTxtView = findViewById(R.id.dateOfBirthTextValue);


        dateOfBirthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(PatientSignUpActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDatePickerListener,2000,1,1);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });


        mDatePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                int monthDisplayed = month+1;

                String dateDisplayForUser = day + "/" + monthDisplayed + "/" + year;
                dateOfBirthTxtView.setText(dateDisplayForUser);

                dateOfBirth = new Date(year, month, day);

            }
        };


        mSignUpBtn = findViewById(R.id.signUpBtn);
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = auth.getCurrentUser();
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


    private boolean validatePhoneNumber(String phoneNumber){

        if(phoneNumber.isEmpty()){
            textInputPhoneNumber.setError("Can't be empty");
            return false;
        }else if(!phoneNumber.matches("[0-9]+")){
            textInputPhoneNumber.setError("Numbers only");
            return false;
        }else if(phoneNumber.length()<8){
            textInputPhoneNumber.setError("Not enough digits");
            return false;
        }else{
            textInputPhoneNumber.setError(null);
            return true;
        }

    }



    public void signUp(){

//       String firstNameInput = textInputFirstName.getEditText().getText().toString().trim();
//       String surname = textInputSurname.getEditText().getText().toString().trim();
//       String emailInput = textInputEmail.getEditText().getText().toString().trim();
       String countryCodeIndex = mCountryCodePicker.getSelectedCountryCodeWithPlus();
//       String phoneNumber = textInputPhoneNumber.getEditText().getText().toString().trim();
//       String password = textInputPassword.getEditText().getText().toString().trim();
//
//        if(!validateEmail(emailInput) || !validatePassword(password) || !validateFirstName(firstNameInput) || !validatePhoneNumber(phoneNumber)) {
//            return;
//        }

        Toast.makeText(this, "herrro " + countryCodeIndex, Toast.LENGTH_SHORT).show();




    }


}

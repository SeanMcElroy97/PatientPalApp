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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.patientpal.model.Patient;
import com.example.patientpal.services.VolleySingletonRequestQueue;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;


import org.json.JSONException;
import org.json.JSONObject;


import maes.tech.intentanim.CustomIntent;

public class PatientSignUpActivity extends AppCompatActivity {

    RequestQueue mRequestQueue;

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


    private RadioGroup mRadioGroupGender;
    private RadioButton mRadioButtonClicked;

    private String mdateofBirthStr;


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
                DatePickerDialog dialog = new DatePickerDialog(PatientSignUpActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDatePickerListener,1980,1,1);
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

                String monthBeforeStr = String.valueOf(monthDisplayed);
                String dayBeforeStr = String.valueOf(day);

                String monthFormat =(monthBeforeStr.length()<2 ? "0" + monthBeforeStr : monthBeforeStr );
                String dayFormat = (dayBeforeStr.length()<2? "0"+dayBeforeStr : dayBeforeStr);


                mdateofBirthStr= year + "-" + monthFormat + "-" + dayFormat;

            }
        };

        mRadioGroupGender = findViewById(R.id.gender_radio_group);

        mSignUpBtn = findViewById(R.id.signUpBtn);
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }


//
//    public void checkButton(View v){
//        int radioId = mRadioGroupGender.getCheckedRadioButtonId();
//
//        mRadioButtonClicked = findViewById(radioId);
//    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = auth.getCurrentUser();
        //go to homepage
        //updateUI(currentUser);

        //Check shared preferences
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

//    public boolean ValidatePPS(String ppsNumber) {
//
//        Pattern p = Pattern.compile("/^(\d{7})([A-Z]{1,2})$/i");
//        Matcher m = p.matcher(ppsNumber);
//
//        boolean b = m.matches();
//        return b;
//    }



    public void signUp(){

       String firstNameInput = textInputFirstName.getEditText().getText().toString().trim();
       String surname = textInputSurname.getEditText().getText().toString().trim();
       String emailInput = textInputEmail.getEditText().getText().toString().trim();
       String countryCodePrefix = mCountryCodePicker.getSelectedCountryCodeWithPlus();
       String phoneNumber = textInputPhoneNumber.getEditText().getText().toString().trim();
       String password = textInputPassword.getEditText().getText().toString().trim();
       String address = textInputAddress.getEditText().getText().toString().trim();

       String ppsNumber = textInputPPSNumber.getEditText().getText().toString().trim();

        int radioId = mRadioGroupGender.getCheckedRadioButtonId();
        mRadioButtonClicked = findViewById(radioId);
       String gender = mRadioButtonClicked.getText().toString();
//

//        dateOfBirth;
//
//        if(!validateEmail(emailInput) || !validatePassword(password) || !validateFirstName(firstNameInput) || !validatePhoneNumber(phoneNumber)) {
//            return;
//        }

        System.out.println("firstName " + firstNameInput);
        System.out.println("lastName " + surname);
        System.out.println("patientEmail " + emailInput);
        System.out.println("phoneNumber" + countryCodePrefix+phoneNumber);
        System.out.println("patientPassword " + password);
        System.out.println("patientPPSNumber " + ppsNumber);
        System.out.println("patientAddress " + address);
        System.out.println("dateOfBirth " + mdateofBirthStr);
        System.out.println("gender " + gender);

//        Patient patientToSend = new Patient();
//        patientToSend.setFirstName(firstNameInput);
//        patientToSend.setLastName(surname);
//        patientToSend.setPatientEmail(emailInput);
//        patientToSend.setPhoneNumber(countryCodePrefix+phoneNumber);
//        patientToSend.setPatientPassword(password);
//        patientToSend.setPatientPPSNumber(ppsNumber);
//        patientToSend.setPatientAddress(address);
//        patientToSend.setDateOfBirth(mdateofBirthStr);
//        patientToSend.setDateOfBirth(gender);




        org.json.JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("firstName", firstNameInput);
            jsonBody.put("lastName", surname);
            jsonBody.put("patientEmail", emailInput);
            jsonBody.put("phoneNumber", countryCodePrefix+phoneNumber);
            jsonBody.put("patientPassword", password);
            jsonBody.put("patientPPSNumber", ppsNumber);
            jsonBody.put("patientAddress", address);
            jsonBody.put("dateOfBirth", mdateofBirthStr);
            jsonBody.put("gender", gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }




        mRequestQueue = VolleySingletonRequestQueue.getInstance(this).getRequestQueue();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.spring_boot_url) + "/createNewPatient", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


//                Gson g = new Gson();
//                String responseStr  = g.fromJson(response);

                Toast.makeText(getApplicationContext(), "Response: " + response.toString(), Toast.LENGTH_SHORT).show();

                //Or boolean
                //JWT will be
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        mRequestQueue.add(postRequest);

    }


    //After sign Up
    public void Login(){
        
    }

}

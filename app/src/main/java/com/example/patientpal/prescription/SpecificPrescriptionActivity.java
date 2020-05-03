package com.example.patientpal.prescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.patientpal.R;
import com.example.patientpal.adapters.PrescriptionLineItemAdapter;
import com.example.patientpal.adapters.PrescriptionListAdapter;
import com.example.patientpal.model.LineItem;
import com.example.patientpal.model.Prescription;
import com.example.patientpal.services.VolleySingletonRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpecificPrescriptionActivity extends AppCompatActivity {

    //Recycler view
    RecyclerView mLineItemRecView;
    RecyclerView.Adapter mAdapter;

    //API call
    RequestQueue mRequestQueue;
    List<LineItem> mLineItems;
    List<LineItem> mLineItemsTest;
    int mPrescriptionId;

    TextView prescriptionStatus;
    TextView prescriptionPharmacy;
    TextView prescriptionDoctorNameText;
    TextView prescriptionCreationDateText;
    TextView prescriptionFulfillmentDateText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_prescription);

        DateFormat df = new SimpleDateFormat("HH:mm,   dd/MM/yy");

        prescriptionStatus = findViewById(R.id.prescription_status_txt);
        prescriptionPharmacy = findViewById(R.id.prescription_pharmacy_txt);
        prescriptionDoctorNameText = findViewById(R.id.doctor_name_txt_view);
        prescriptionCreationDateText = findViewById(R.id.prescription_creation_date_text_view);
        prescriptionFulfillmentDateText = findViewById(R.id.fulfillment_date_text_view);

        Intent receiverIntent = getIntent();
        Prescription prescription = receiverIntent.getParcelableExtra("prescription_obj");

        mPrescriptionId = prescription.getPrescriptionID();

        prescriptionStatus.setText(prescription.getStatus());
        backgroundColorPicker(prescription.getStatus());
        prescriptionPharmacy.setText(prescription.getPharmacyNameStr());
        prescriptionPharmacy.setEnabled(true);

        if(prescription.getDoctor().equalsIgnoreCase("null") || prescription.getDoctor().equalsIgnoreCase("")){
            prescriptionDoctorNameText.setText("TBD");
        }else{
            prescriptionDoctorNameText.setText(prescription.getDoctor());
        }
        prescriptionDoctorNameText.setEnabled(true);

        Date creationDate = new Date(prescription.getPrescriptionCreationTime());
        prescriptionCreationDateText.setText(df.format(creationDate));


        System.out.println(" prescription id" + prescription.getPrescriptionID());
//        System.out.println(" prescription fulfillment time " + prescription.getPrescriptionFulfillmentTime());

        if(prescription.getPrescriptionFulfillmentTime()==0 || prescription.getPrescriptionFulfillmentTime()==null){
            prescriptionFulfillmentDateText.setText("TBD");
        }else{
            Date fulfilmentDate = new Date(prescription.getPrescriptionFulfillmentTime());
            prescriptionFulfillmentDateText.setText(df.format(fulfilmentDate));
        }

        callLineItemsApi();


        mLineItemRecView= findViewById(R.id.lineItemsRecView);
        mLineItemRecView.setHasFixedSize(true);
        mLineItemRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mLineItemRecView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
    }


    public void callLineItemsApi(){
        mLineItems= new ArrayList<>();
        mRequestQueue = VolleySingletonRequestQueue.getInstance(this).getRequestQueue();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getString(R.string.spring_boot_url) + "mobile/getMyPrescriptionLineItems/"+mPrescriptionId, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject JSONlineItemObj = response.getJSONObject(i);
                        LineItem lineItem = new LineItem();
                        lineItem.setLineItemId(Integer.parseInt(JSONlineItemObj.getString("prescriptionLineItemID")));
                        lineItem.setMedicineName(JSONlineItemObj.getString("lineItemMedicineTradeName"));
                        lineItem.setQuantity(Integer.parseInt(JSONlineItemObj.getString("prescriptionLineItemQty")));
                        lineItem.setMedInstructions(JSONlineItemObj.getString("prescriptionLineItemInstructions"));

                        mLineItems.add(lineItem);
                    }

                    updateRecyclerView(mLineItems);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(jsonArrayRequest);
    }


    public void updateRecyclerView(List lineItems){
//        System.out.println("line item size  " + lineItems.size());
        mAdapter = new PrescriptionLineItemAdapter(lineItems);
        mLineItemRecView.setAdapter(mAdapter);
    }

    public void backgroundColorPicker(String status){
        RelativeLayout mRelativeLayout = findViewById(R.id.prescriptionLayout);

        switch (status){
            case "cancelled":
                mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.verylightRed));
                break;
            case "ready":
                mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.verylightGreen));
                break;
            case "submitted":
                mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.verylightGold));
                break;
            case "fulfilled":
                mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.verylightPurpleGrey));
                break;
            case "beingprepared":
                mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.verylightBlue));
                break;
        }

    }
}

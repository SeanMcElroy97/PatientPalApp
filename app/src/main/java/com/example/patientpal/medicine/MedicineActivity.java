package com.example.patientpal.medicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.patientpal.R;
import com.example.patientpal.adapters.PrescriptionLineItemAdapter;
import com.example.patientpal.model.LineItem;
import com.example.patientpal.services.VolleySingletonRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class MedicineActivity extends AppCompatActivity {

    RequestQueue mRequestQueue;

    ImageView threeMonthImageView;
    ImageView sixMonthImageView;
    ImageView allTimeImageView;
    TextView mNumberOfMonthsTextView;

    RecyclerView mMyMedicineRecyclerView;
    PrescriptionLineItemAdapter mAdapter;


    List<LineItem> mLineItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        threeMonthImageView = findViewById(R.id.three_months_btnView);
        sixMonthImageView = findViewById(R.id.six_months_btnView);
        allTimeImageView = findViewById(R.id.all_time_btnView);
        mNumberOfMonthsTextView = findViewById(R.id.numberOfMonthsMedicineTxt);


        mMyMedicineRecyclerView = findViewById(R.id.allMedicineRecyclerView);
        mMyMedicineRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mMyMedicineRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));


        GetMyMedicineFromAPI(3);

        mNumberOfMonthsTextView.setText("Medicine prescribed in past " + 3 +" months");

        threeMonthImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetMyMedicineFromAPI(3);
                mNumberOfMonthsTextView.setText("Medicine prescribed in past " + 3 +" months");
            }
        });

        sixMonthImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetMyMedicineFromAPI(6);
                mNumberOfMonthsTextView.setText("Medicine prescribed in past " + 6 +" months");
            }
        });

        allTimeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetMyMedicineFromAPI(500);
                mNumberOfMonthsTextView.setText("All Medicine prescribed");
            }
        });
    }



    public void finish(){
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    public void finish(View view){
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    public void GetMyMedicineFromAPI(int months){
        mLineItems= new ArrayList<>();
        mRequestQueue = VolleySingletonRequestQueue.getInstance(this).getRequestQueue();



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getString(R.string.spring_boot_url) + "mobile/allMyLineItems/"+months, null, new Response.Listener<JSONArray>() {
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
        mMyMedicineRecyclerView.setAdapter(mAdapter);
    }

}

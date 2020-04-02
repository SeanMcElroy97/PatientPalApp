package com.example.patientpal.prescription;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.patientpal.R;
import com.example.patientpal.adapters.PrescriptionListAdapter;
import com.example.patientpal.model.LocationCovidStats;
import com.example.patientpal.model.Prescription;
import com.example.patientpal.patientActivities.PatientHome;
import com.example.patientpal.patientActivities.covidActivities.CovidMainActivity;
import com.example.patientpal.services.VolleySingletonRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class MyPrescriptionsFragment extends Fragment {

    private ArrayList<Prescription> mPrescriptions;
    private RecyclerView mRecyclerView;
    private PrescriptionListAdapter mPrescriptionListAdapter;

    //API call
    RequestQueue mRequestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        callPrescriptionsAPI();

        View v = inflater.inflate(R.layout.my_prescriptions_frag_layout, container, false);
        //goBackToMainPrescription = v.findViewById(R.id.goBackToPreBtn);

        //Create RecyclerView view + Create Adapter. Set Adapter in the api method
        mRecyclerView = v.findViewById(R.id.recycler_view_prescriptions);
        mPrescriptionListAdapter = new PrescriptionListAdapter(mPrescriptions, getContext());

        //Set layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Set Decorator
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        return v;
    }



       public void callPrescriptionsAPI() {
        mRequestQueue = VolleySingletonRequestQueue.getInstance(getContext()).getRequestQueue();
        mPrescriptions = new ArrayList<>();
           JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getString(R.string.spring_boot_url) + "mobile/myprescriptions", null, new Response.Listener<JSONArray>() {


               @Override
               public void onResponse(JSONArray response) {

                   try {
                       for (int i = 0; i < response.length(); i++) {

                           JSONObject JSONprescriptionOBJ = response.getJSONObject(i);

                           Prescription prescription = new Prescription();
                           prescription.setPrescriptionID(Integer.parseInt(JSONprescriptionOBJ.getString("prescriptionID")));
                           prescription.setStatus(JSONprescriptionOBJ.getString("status"));

                           mPrescriptions.add(prescription);
                       }

                       //Set recyclerview adapter to the adapter
                       mRecyclerView.setAdapter(mPrescriptionListAdapter);

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   System.out.println(error.toString());
               }
           });

           mRequestQueue.add(jsonArrayRequest);

       }

}

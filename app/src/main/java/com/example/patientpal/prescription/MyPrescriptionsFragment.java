package com.example.patientpal.prescription;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.patientpal.R;
import com.example.patientpal.adapters.PrescriptionListAdapter;
import com.example.patientpal.model.Prescription;
import com.example.patientpal.model.PrescriptionComparator;
import com.example.patientpal.services.VolleySingletonRequestQueue;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToLongFunction;

import maes.tech.intentanim.CustomIntent;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyPrescriptionsFragment extends Fragment implements PrescriptionListAdapter.OnPrescriptionListener {

    private ArrayList<Prescription> mPrescriptions;


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private PrescriptionListAdapter mPrescriptionListAdapter;

    //API call
    RequestQueue mRequestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        callPrescriptionsAPI();

        View v = inflater.inflate(R.layout.my_prescriptions_frag_layout, container, false);


        //Create RecyclerView view + Create Adapter. Set Adapter in the api method
        mRecyclerView = v.findViewById(R.id.recycler_view_prescriptions);


        mSwipeRefresh = v.findViewById(R.id.swipePrescriptionRefreshLayout);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callPrescriptionsAPI();

                mSwipeRefresh.setRefreshing(false);
            }
        });

        //Initial set adapter
        //mPrescriptionListAdapter = new PrescriptionListAdapter(mPrescriptions, getContext());

        //Set layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Set Decorator
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));



        return v;
    }



       public void callPrescriptionsAPI() {

        mRequestQueue = VolleySingletonRequestQueue.getInstance(getContext()).getRequestQueue();
        mPrescriptions = new ArrayList<>();
           JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getString(R.string.spring_boot_url) + "mobile/getMyPrescriptions", null, new Response.Listener<JSONArray>() {


               @Override
               public void onResponse(JSONArray response) {

                   try {
                       System.out.println("prescription response length " + response.length());
                       for (int i = 0; i < response.length(); i++) {

                           JSONObject JSONprescriptionOBJ = response.getJSONObject(i);

                           Prescription prescription = new Prescription();
                           prescription.setPrescriptionID(Integer.parseInt(JSONprescriptionOBJ.getString("prescriptionID")));
                           prescription.setStatus(JSONprescriptionOBJ.getString("prescriptionStatus"));
                           prescription.setPrescriptionCreationTime(JSONprescriptionOBJ.getLong("prescriptionCreationDate"));
                           if (JSONprescriptionOBJ.getLong("prescriptionFulfilmentDate")!=0){
                               prescription.setPrescriptionFulfillmentTime(JSONprescriptionOBJ.getLong("prescriptionFulfilmentDate"));
                           }
                           prescription.setDoctor(JSONprescriptionOBJ.getString("doctor"));
                           prescription.setPatientMessage(JSONprescriptionOBJ.getString("patientMessage"));
                           prescription.setPharmacyNameStr(JSONprescriptionOBJ.getString("pharmacyName"));
                            //More prescription fields
                           mPrescriptions.add(prescription);


                       }


                       PrescriptionComparator comparator = new PrescriptionComparator();
                       Collections.sort(mPrescriptions, comparator);


                       updateRecyclerView(mPrescriptions);


                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   System.out.println(error.toString());
                   mSwipeRefresh.setRefreshing(false);
               }
           });

           mRequestQueue.add(jsonArrayRequest);

       }


       public void updateRecyclerView(ArrayList<Prescription> rxList){
           //Set recyclerview adapter to the adapter
          mPrescriptionListAdapter = new PrescriptionListAdapter(rxList, getContext(), this);
           //mPrescriptionListAdapter.notifyDataSetChanged();
           mRecyclerView.setAdapter(mPrescriptionListAdapter);
           //Set Adapter

       }


       //MAy need prescription to implement parcelable

    @Override
    public void onPrescriptionClick(int position) {
       mPrescriptions.get(position);
        Log.d(TAG, "onPrescriptionClick: " + position);
        Intent intentToPrescription = new Intent(getContext(), SpecificPrescriptionActivity.class);
        intentToPrescription.putExtra("prescription_obj", mPrescriptions.get(position));
        startActivity(intentToPrescription);

    }
}

package com.example.patientpal.prescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.patientpal.R;
import com.example.patientpal.adapters.PrescriptionFragAdapter;
import com.example.patientpal.services.VolleySingletonRequestQueue;

import maes.tech.intentanim.CustomIntent;

public class PrescriptionHomeActivity extends AppCompatActivity  {

    private PrescriptionFragAdapter mPrescriptionFragAdapter;
    private ViewPager mViewPager;

    //private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_home);

        //Fragment stuff
        mPrescriptionFragAdapter = new PrescriptionFragAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.prescription_frag_view_pager);

        //Set up View pager
        setupViewPager(mViewPager);
    }

    public void setupViewPager(ViewPager viewPager){
        PrescriptionFragAdapter adapter = new PrescriptionFragAdapter(getSupportFragmentManager());
        adapter.addFragment(new PrescriptionMainFrag());
        adapter.addFragment(new NewPrescriptionFragment());
        adapter.addFragment(new MyPrescriptionsFragment());
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragNum){
        mViewPager.setCurrentItem(fragNum);
    }


    public void finish(){
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    public void finish(View view){
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }


}

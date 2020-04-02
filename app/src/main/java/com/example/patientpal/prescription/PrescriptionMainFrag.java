package com.example.patientpal.prescription;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.patientpal.R;

public class PrescriptionMainFrag extends Fragment {

    private ImageButton newPrescriptionBtn;
    private ImageButton myPrescriptionsBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v =inflater.inflate(R.layout.prescription_home_fragment, container, false);

        newPrescriptionBtn = v.findViewById(R.id.newPrescriptionBTN);
        myPrescriptionsBtn = v.findViewById(R.id.myPrescriptionsBtn);

        newPrescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PrescriptionHomeActivity)getActivity()).setViewPager(1);
            }
        });

        myPrescriptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PrescriptionHomeActivity)getActivity()).setViewPager(2);
            }
        });

        return v;
    }


}

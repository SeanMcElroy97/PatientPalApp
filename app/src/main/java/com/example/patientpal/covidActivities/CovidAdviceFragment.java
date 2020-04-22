package com.example.patientpal.covidActivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.patientpal.R;
import com.github.chrisbanes.photoview.PhotoView;


public class CovidAdviceFragment extends Fragment{
    private PhotoView photo;

    public CovidAdviceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_advice_covid, container, false);

         photo = (PhotoView) view.findViewById(R.id.covid_photo_view);
        photo.setImageResource(R.drawable.covid_advice_img);


        return view;
    }


}

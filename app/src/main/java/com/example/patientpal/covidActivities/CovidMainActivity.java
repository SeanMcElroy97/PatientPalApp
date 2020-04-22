package com.example.patientpal.covidActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.patientpal.R;
import com.example.patientpal.model.LocationCovidStats;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class CovidMainActivity extends AppCompatActivity {

    //Navegate through fragments
    private ViewPager viewPager;

    //FragmentAdapter
    private CovidFragmentCollectionAdapter adapter;



    ArrayList<LocationCovidStats> globalCovidStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_main);


        globalCovidStats = this.getIntent().getParcelableArrayListExtra("globalCovidArray");

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("globalCovidArray", globalCovidStats);



        //////////////////////////////////////////////
        viewPager = findViewById(R.id.view_pager);

        adapter = new CovidFragmentCollectionAdapter(getSupportFragmentManager(), bundle, getApplicationContext());

        viewPager.setAdapter(adapter);
        ////////////////////////////////////////////
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

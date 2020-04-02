package com.example.patientpal.services;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingletonRequestQueue {

    private static VolleySingletonRequestQueue mInstance;
    private RequestQueue mRequestQueue;

    private VolleySingletonRequestQueue(Context ctx) {
        this.mRequestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }


    public static synchronized VolleySingletonRequestQueue getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleySingletonRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}

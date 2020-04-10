package com.example.patientpal.appointments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.patientpal.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class CreateAppointmentDialog extends AppCompatDialogFragment {

    private DialogListerner listener;

    private TextInputLayout mAppointmentTitleEditText, mAppointmentAdditionalInfoEditText;
    private NumberPicker mHourPicker, mMinutePicker;
    private String[] hourArray, minuteArray;
    TextView redExText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_create_appointment, null);

        builder.setView(v);


        mAppointmentTitleEditText = v.findViewById(R.id.AppointmentTitle);
        mAppointmentAdditionalInfoEditText = v.findViewById(R.id.AppointmentAdditionalInfo);

        mHourPicker = v.findViewById(R.id.NumberPickerHour);
        mMinutePicker = v.findViewById(R.id.NumberPickerMinute);

        mHourPicker.setMinValue(00);
        mHourPicker.setMaxValue(23);

        mMinutePicker.setMinValue(00);
        mMinutePicker.setMaxValue(59);


        hourArray = new String[24];
        minuteArray = new String[60];

        for(int i=0; i<24; i++){
            hourArray[i] = ((i<10) ? "0" +String.valueOf(i): String.valueOf(i));
        }

        for(int i=0; i<60; i++){
            minuteArray[i] = ((i<10) ? "0" +String.valueOf(i): String.valueOf(i));
        }

        mHourPicker.setDisplayedValues(hourArray);
        mMinutePicker.setDisplayedValues(minuteArray);

        redExText = v.findViewById(R.id.dialog_close_red_x);

        redExText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return builder.create();
    }

    public interface DialogListerner{
        
    }

    public void onAttach(Context ctx){
        super.onAttach(ctx);

        try {
            listener = (DialogListerner) ctx;
        }catch (ClassCastException e){
            throw  new  ClassCastException(ctx.toString() + "Must implement dialogue listener");
        }
    }
}

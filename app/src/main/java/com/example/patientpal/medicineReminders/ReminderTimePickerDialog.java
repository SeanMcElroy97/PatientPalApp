package com.example.patientpal.medicineReminders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.patientpal.R;

public class ReminderTimePickerDialog extends AppCompatDialogFragment {

    private NumberPicker mHourPicker, mMinutePicker;
    private String[] hourArray, minuteArray;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_reminder_time_dialog, null);


        ///

        mHourPicker = v.findViewById(R.id.ReminderNumberPickerHour);
        mHourPicker.setMinValue(00);
        mHourPicker.setMaxValue(23);

        mMinutePicker = v.findViewById(R.id.ReminderNumberPickerMinute);
        mMinutePicker.setMinValue(00);
        mMinutePicker.setMaxValue(59);


        ////



        builder.setView(v)
                .setTitle("Choose Reminder Time");

        return builder.create();
    }
}



















//       mHourPicker.setMinValue(00);
//               mHourPicker.setMaxValue(23);
//
//               mMinutePicker.setMinValue(00);
//               mMinutePicker.setMaxValue(59);

//hourArray = new String[24];
//        minuteArray = new String[60];

//    String hour24 = String.valueOf(mHourPicker.getValue());
//    String minute24 = String.valueOf(mMinutePicker.getValue());
//
//                    if (hour24.length()<2){
//        hour24 = 0 + hour24;
//        }
//        if (minute24.length()<2){
//        minute24 = 0 + minute24;
//        }
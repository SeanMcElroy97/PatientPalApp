package com.example.patientpal.medicineReminders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.patientpal.R;

public class ReminderTimePickerDialog extends AppCompatDialogFragment {

    private NumberPicker mHourPicker, mMinutePicker;
    private String[] hourArray, minuteArray;
    private String mHour24, mMinute24;

    private ReminderTimePickerDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_reminder_time_dialog, null);


        ///

        mHourPicker = v.findViewById(R.id.ReminderNumberPickerHour);
        mHourPicker.setMinValue(0);
        mHourPicker.setMaxValue(23);

        mMinutePicker = v.findViewById(R.id.ReminderNumberPickerMinute);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(59);

        String[] hourdisplayedValues  = new String[24];
        String[] minutedisplayedValues  = new String[60];

        for(int i=0; i<24; i++){
            hourdisplayedValues[i] = ((i<10) ? "0" +String.valueOf(i): String.valueOf(i));
        }

        for(int i=0; i<60; i++){
            minutedisplayedValues[i] = ((i<10) ? "0" +String.valueOf(i): String.valueOf(i));
        }

        mMinutePicker.setDisplayedValues(minutedisplayedValues);
        mHourPicker.setDisplayedValues(hourdisplayedValues);



        ////



        builder.setView(v)
                .setTitle("Choose Reminder Time")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mHour24 = (mHourPicker.getValue()<10) ? "0" + String.valueOf(mHourPicker.getValue()) : String.valueOf(mHourPicker.getValue());
                        mMinute24 = (mMinutePicker.getValue()<10) ? "0" + String.valueOf(mMinutePicker.getValue()) : String.valueOf(mMinutePicker.getValue());


                        listener.getHourMinuteStr(mHour24, mMinute24, mHourPicker.getValue(), mMinutePicker.getValue());
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ReminderTimePickerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement the interface");
        }
    }


    //Need dialog interface to retrieve info in activity

    public interface ReminderTimePickerDialogListener{
        void getHourMinuteStr(String hour24Str, String minute60str, int hourVal, int minuteVal);
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
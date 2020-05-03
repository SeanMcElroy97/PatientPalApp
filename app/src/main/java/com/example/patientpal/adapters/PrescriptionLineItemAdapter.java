package com.example.patientpal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientpal.R;
import com.example.patientpal.model.LineItem;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionLineItemAdapter extends RecyclerView.Adapter<PrescriptionLineItemAdapter.ViewHolder> {

    private List<LineItem> mLineItems;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mMedicineNameTxtView;
        public TextView mLineItemQtyTxtView;
        public TextView mLineItemInstructions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMedicineNameTxtView = itemView.findViewById(R.id.scrollingTextMedicineName);
            mLineItemQtyTxtView = itemView.findViewById(R.id.lineItemQuantityView);
            mLineItemInstructions = itemView.findViewById(R.id.scrollingTextLineInstructions);

        }
    }

    public PrescriptionLineItemAdapter(List<LineItem> lineItemsArr){
        this.mLineItems = lineItemsArr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_line_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LineItem currentLineItem = mLineItems.get(position);
        holder.mMedicineNameTxtView.setText(currentLineItem.getMedicineName());
        holder.mMedicineNameTxtView.setSelected(true);
        holder.mLineItemQtyTxtView.setText("Quantity: " + String.valueOf(currentLineItem.getQuantity()));
        holder.mLineItemInstructions.setText(currentLineItem.getMedInstructions());
        holder.mLineItemInstructions.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return mLineItems.size();
    }


}

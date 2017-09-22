package com.utapass.expense;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vanessatsai on 2017/9/21.
 */

public class Expense_Adapter extends RecyclerView.Adapter<Expense_Adapter.ViewHolder> {

    private final Cursor cursor;

    public Expense_Adapter(Cursor cursor) {
        this.cursor =cursor;

    }

    //step 2
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //step 3
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        int id = cursor.getInt(cursor.getColumnIndex(ExpenseContacts.Expense_Table.ID));
        String cdata = cursor.getString(cursor.getColumnIndex(ExpenseContacts.Expense_Table.CDATE));
        String info = cursor.getString(cursor.getColumnIndex(ExpenseContacts.Expense_Table.INFO));
        String amount = cursor.getString(cursor.getColumnIndex(ExpenseContacts.Expense_Table.AMOUNT));
        holder.dateTextView.setText(cdata);
        holder.infoTextView.setText(info);

    }


    //step 0
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateTextView;
        private final TextView infoTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.row_date);
            infoTextView  =itemView.findViewById(R.id.row_info);
        }
    }
}

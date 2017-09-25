package com.utapass.expense;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vanessatsai on 2017/9/21.
 */

public class Expense_Adapter extends RecyclerView.Adapter<Expense_Adapter.ViewHolder>{

    private Cursor cursor;
    private OnItemClickInterface onItemClickListener; //listener and sender


    private Expense_Adapter(Cursor cursor) {
        this.cursor =cursor;

    }

    public Expense_Adapter() {

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        cursor.moveToPosition(position);
        Expense ex = new Expense(cursor);

        holder.dateTextView.setText(ex.getCdata());
        holder.infoTextView.setText(ex.getInfo());
        holder.itemView.setTag(ex); // all information into itemView
        holder.accountTextView.setAmount(ex.getAmount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("VA","0000000"+position);
                onItemClickListener.itemclicked(position, (Expense) view.getTag());
            }
        });

    }


    //step 0
    @Override
    public int getItemCount() {

        return (cursor!=null)? cursor.getCount(): 0;
    }

    public void setOnItemClickListener(OnItemClickInterface onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateTextView;
        private final TextView infoTextView;
        private final custom_textview accountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.row_date);
            infoTextView  =itemView.findViewById(R.id.row_info);
            accountTextView  =itemView.findViewById(R.id.row_amount);
        }
    }


    public void swapCursor(Cursor cursor) {
        //input cursor
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    //public interface onRecycleViewItemClickListener{
   //    void onItemClicked(int position, Expense ex);

   //}

   //public void setOnRecyclerViewItemClickListener(onRecycleViewItemClickListener onRecycleViewClickListener){
   //    this.onRecycleViewClickListener = onRecycleViewClickListener;

   //}
}

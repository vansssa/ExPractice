package com.utapass.expense;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vanessatsai on 2017/9/22.
 */

public class Expense implements Parcelable {


    private final int id;
    private final String cdata;
    private final String info;
    private final int amount;

    public Expense(Cursor cursor) {

        id = cursor.getInt(cursor.getColumnIndex(ExpenseContacts.Expense_Table.ID));
        cdata = cursor.getString(cursor.getColumnIndex(ExpenseContacts.Expense_Table.CDATE));
        info = cursor.getString(cursor.getColumnIndex(ExpenseContacts.Expense_Table.INFO));
        amount = cursor.getInt(cursor.getColumnIndex(ExpenseContacts.Expense_Table.AMOUNT));
    }

    protected Expense(Parcel in) {
        id = in.readInt();
        cdata = in.readString();
        info = in.readString();
        amount = in.readInt();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public int getID(){
        return id;

    }

    public String getCdata() {
        return cdata;
    }

    public String getInfo() {
        return info;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(cdata);
        parcel.writeString(info);
        parcel.writeInt(amount);
    }
}

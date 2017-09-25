package com.utapass.expense;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = AddActivity.class.getSimpleName();
    private EditText edDate;
    private EditText edInfo;
    private EditText edAmount;
    ExpenseDbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edDate = (EditText) findViewById(R.id.ed_date);
        edInfo = (EditText) findViewById(R.id.ed_info);
        edAmount = (EditText) findViewById(R.id.ed_amount);
        helper = new ExpenseDbHelper(this);


    }

    public void add(View v){
        String cdata = edDate.getText().toString();
        String info = edInfo.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());

        insertToDB(cdata,info,amount);
    }

    private void insertToDB(String cdate,String info, int amount) {
        ContentValues values = new ContentValues();
        values.put(ExpenseContacts.Expense_Table.CDATE, cdate);
        values.put(ExpenseContacts.Expense_Table.INFO, info);
        values.put(ExpenseContacts.Expense_Table.AMOUNT, amount);
        getContentResolver().insert(ExpenseContacts.CONTENT_URI,values);

    }

}

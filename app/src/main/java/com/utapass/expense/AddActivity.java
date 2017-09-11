package com.utapass.expense;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = AddActivity.class.getSimpleName();
    private EditText edDate;
    private EditText edInfo;
    private EditText edAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edDate = (EditText) findViewById(R.id.ed_date);
        edInfo = (EditText) findViewById(R.id.ed_info);
        edAmount = (EditText) findViewById(R.id.ed_amount);
    }

    public void add(View v){
        String date = edDate.getText().toString();
        String info = edInfo.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());
        ExpenseDbHelper helper = new ExpenseDbHelper(this);

        ContentValues values = new ContentValues();
        values.put("cdate",date);
        values.put("info",info);
        values.put("amount",amount);
        long id = helper.getWritableDatabase().insert("exp", null, values);
        Log.d(TAG, "add: " + id);
    }
}

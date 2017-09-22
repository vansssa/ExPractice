package com.utapass.expense;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Expense expense = getIntent()
                .getParcelableExtra(getString(R.string.json_expenses));
        TextView detail = (TextView) findViewById(R.id.detail);
        detail.setText(expense.getCdata()+"/"+expense.getInfo()+"/"+expense.getAmount());
    }
}

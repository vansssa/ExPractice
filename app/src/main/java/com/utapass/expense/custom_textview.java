package com.utapass.expense;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class custom_textview extends AppCompatTextView {


    private Drawable drawable;
    private TextView row_amount;

    public custom_textview(Context context) {
        super(context);
        initUI();
    }

    public custom_textview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public custom_textview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }


    private void initUI(){
        row_amount = findViewById(R.id.row_amount);
        drawable = ContextCompat.getDrawable(getContext(), R.drawable.custom_textview);
        setBackgroundResource(R.drawable.custom_textview);
        setTextSize(30);
        setGravity(TEXT_ALIGNMENT_CENTER);
    }

    public void setAmount(int amount){

        row_amount.setText(String.valueOf(amount));
    }


}

package com.utapass.expense;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by vanessatsai on 2017/9/22.
 */

public class ExpenseIntentService extends IntentService {
    public static final  String EXTRA_VALUE = "EXTRA_VALUE";
    public static final  String LAST = "LAST";

    public ExpenseIntentService() {
        super("ExpenseIntentService"); // identified Intentservice ID
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Parcelable value = intent.getParcelableExtra(EXTRA_VALUE);
        getContentResolver().insert(ExpenseContacts.CONTENT_URI, (ContentValues) value);

        boolean result = intent.getBooleanExtra(LAST,false);
        if(result) {
            //refresh UI
            Log.d("VA","last in service");
            Intent last=new Intent(LAST);
            sendBroadcast(last);
        }


    }
}

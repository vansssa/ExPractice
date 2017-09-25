package com.utapass.expense;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.utapass.expense.EventBus.ExpenseEventBus;

import de.greenrobot.event.EventBus;

/**
 * Created by vanessatsai on 2017/9/22.
 */

public class ExpenseIntentService extends IntentService {
    public static final  String EXTRA_VALUE = "EXTRA_VALUE";
    public static final  String EXTRA_LAST = "LAST";
    public static final  String EVENT_LAST = "LAST";
    private static final String TAG = ExpenseIntentService.class.getSimpleName() ;
    private static Context context;

    public ExpenseIntentService() {
        super("ExpenseIntentService"); // identified Intentservice ID
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Parcelable value = intent.getParcelableExtra(EXTRA_VALUE);
        getContentResolver().insert(ExpenseContacts.CONTENT_URI, (ContentValues) value);

        boolean result = intent.getBooleanExtra(EXTRA_LAST,false);
        if(result) {
            //refresh UI
            Log.d(TAG,"last in service");
            //Intent last=new Intent(LAST);
            //sendBroadcast(last);
            EventBus.getDefault().post(new ExpenseEventBus(EVENT_LAST));
        }


    }

    public static void insertExpense(Context context, ContentValues values, boolean last) {
        Intent intent =new Intent(context,ExpenseIntentService.class);
        intent.putExtra(ExpenseIntentService.EXTRA_VALUE,values);
        intent.putExtra(ExpenseIntentService.EXTRA_LAST,last);
        context.startService(intent);

    }
}

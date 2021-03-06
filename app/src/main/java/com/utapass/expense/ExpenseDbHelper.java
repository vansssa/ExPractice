package com.utapass.expense;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vanessatsai on 2017/9/11.
 */

public class ExpenseDbHelper extends SQLiteOpenHelper {


    Context context;

    //Constructor
    private ExpenseDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ExpenseDbHelper(Context context) {
        this(context,"expenses.db",null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase Db) {
        Log.i("VA","Create DB");
        Db.execSQL(ExpenseContacts.Expense_Table.CREATE_TABLE_SQL);
        readDataFromRaw(Db);


    }
    private void readDataFromRaw(SQLiteDatabase db) {
        InputStream inputStream = this.context.getResources().openRawResource(R.raw.expenses);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = reader.readLine();
            StringBuilder sb = new StringBuilder(); //collect string
            while (line!=null){
                sb.append(line);
                line = reader.readLine();
            }
            parseJsonObject(db,sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseJsonObject(SQLiteDatabase db, String sb) {
        JSONObject json = null;
        try {
            json = new JSONObject(sb.toString());
            JSONArray array =  json.getJSONArray(this.context.getString(R.string.json_expenses));
            for (int i = 0 ; i < array.length();i++) {
                String cdate = array.getJSONObject(i).getString(ExpenseContacts.Expense_Table.CDATE);
                String info = array.getJSONObject(i).getString(ExpenseContacts.Expense_Table.INFO);
                int amount = Integer.parseInt(array.getJSONObject(i).getString(ExpenseContacts.Expense_Table.AMOUNT));
                ContentValues values = new ContentValues();
                values.put(ExpenseContacts.Expense_Table.CDATE, cdate);
                values.put(ExpenseContacts.Expense_Table.INFO, info);
                values.put(ExpenseContacts.Expense_Table.AMOUNT, amount);
                //long id = db.insert(ExpenseContacts.TABLE_EXPENSE, null, values);

                //ExpenseIntentService service = new ExpenseIntentService();
                Intent intent =new Intent(context,ExpenseIntentService.class);
                intent.putExtra(ExpenseIntentService.EXTRA_VALUE,values);
                intent.putExtra(ExpenseIntentService.LAST, (i == array.length()-1)? true : false);
                context.startService(intent);







            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
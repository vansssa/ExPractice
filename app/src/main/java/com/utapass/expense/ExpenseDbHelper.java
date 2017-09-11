package com.utapass.expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vanessatsai on 2017/9/9.
 */

public class ExpenseDbHelper extends SQLiteOpenHelper {

    private Context context;
    private ExpenseDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ExpenseDbHelper(Context context) {
        this(context,"expense.db",null,1);
        this.context = context;
    }

    // If there is no database when install app. It's created "once".
    @Override
    public void onCreate(SQLiteDatabase Db) {
        Db.execSQL("CREATE TABLE exp (_id INTEGER PRIMARY KEY, cdate DATETIME NOT NULL, info VARCHAR, amount INTEGER) ");
        //input string
      readExpensesFromResource(Db);
    }

    private void readExpensesFromResource(SQLiteDatabase db) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.expenses);
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder(); //collect string
        try {
            String line = in.readLine();
            while (line != null){
                sb.append(line);
                line = in.readLine();
            }
            sb.toString();

            //parse JSON object
            JSONObject json = new JSONObject(sb.toString());
            JSONArray array =  json.getJSONArray(context.getString(R.string.json_expenses));

            for (int i = 0 ; i< array.length() ; i++){
                JSONObject obj = array.getJSONObject(i);
                String date = obj.getString("cdate") ;
                String info = obj.getString("info") ;
                int amount = obj.getInt("amount") ;
                ContentValues values = new ContentValues();
                values.put(ExpenseContract.CDATE,date);
                values.put(ExpenseContract.INFO,info);
                values.put(ExpenseContract.AMOUNT,amount);
                long id = db.insert(ExpenseContract.TABLE_EXPENSE,null,values);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase Db, int i, int i1) {

    }
}

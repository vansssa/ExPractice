package com.utapass.expense;

import android.net.Uri;

/**
 * Created by vanessatsai on 2017/9/11.
 */

public class ExpenseContacts {
    public static final String TABLE_EXPENSE = "exp";

    public class Expense_Table {

        public static final String ID = "_id";
        public static final String CDATE = "cdate";
        public static final String INFO = "info";
        public static final String AMOUNT = "amount";
        public static final String CREATE_TABLE_SQL = "CREATE TABLE exp (_id INTEGER PRIMARY KEY, cdate DATETIME NOT NULL, info VARCHAR, amount INTEGER) ";
    }

    public static String AUTHORITY = "com.utapass.expense";

    //define a new uri for content provider
    public static final Uri CONTENT_URI=new Uri.Builder().scheme("content").authority(AUTHORITY).appendPath(TABLE_EXPENSE).build();
}

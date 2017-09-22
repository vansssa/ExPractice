package com.utapass.expense;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.content.ContentProvider;

/**
 * Created by vanessatsai on 2017/9/21.
 */

public class ExContentProvider extends ContentProvider{

    private ExpenseDbHelper helper;
    private static final String TAG = ContentProvider.class.getSimpleName();
    private Cursor cursor;
    private static UriMatcher sUriMatcher = new UriMatcher(android.content.UriMatcher.NO_MATCH);

    private static final int uriWithoutID = 100;

    private static final int uriWithID = 200;

    //?
    static {
        sUriMatcher.addURI(ExpenseContacts.AUTHORITY,ExpenseContacts.TABLE_EXPENSE, uriWithoutID);
        sUriMatcher.addURI(ExpenseContacts.AUTHORITY,ExpenseContacts.TABLE_EXPENSE+"/#",uriWithID);

    }

    @Override
    public boolean onCreate() {
        Log.d("VA", "onCreate: ");
        helper = new ExpenseDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String order) {
        //四大元件都可以取得 context.
        Log.d("VA", "query: ");
        switch (sUriMatcher.match(uri)){
            case uriWithoutID:
                break;
            case  uriWithID:
                long id = ContentUris.parseId(uri);
                selection = ExpenseContacts.Expense_Table.ID+ "=" +id;
                break;

        }

        cursor = helper.getReadableDatabase().query(ExpenseContacts.TABLE_EXPENSE,projection,selection,selectionArgs,null,null,order);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        long id = helper.getWritableDatabase().insert(ExpenseContacts.TABLE_EXPENSE, null, contentValues);
        Uri record = ContentUris.withAppendedId(ExpenseContacts.CONTENT_URI,id);
        return record;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

package com.utapass.expense;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });
        ExpenseDbHelper helper = new ExpenseDbHelper(this);
        //   helper.getWritableDatabase().rawQuery("select 1",null);
        Cursor cursor = helper.getReadableDatabase().query(
                ExpenseContract.TABLE_EXPENSE,
                null, null, null, null, null, null
        );
        /*while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ExpenseContract.ID));
            String cdata = cursor.getString(cursor.getColumnIndex(ExpenseContract.CDATE));
            String info = cursor.getString(cursor.getColumnIndex(ExpenseContract.INFO));
            String amount = cursor.getString(cursor.getColumnIndex(ExpenseContract.AMOUNT));
            Log.d(TAG,"onCreate " + id + "/ " + cdata +"/"+info+" /"+ amount);
        }*/

        //dangerous permission checker
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            readContacts();
        }
        else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_CONTACTS);

        }
        //getContentResolver().query(ContactsContract.AUTHORITY_URI,null,null,null,null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_CONTACTS && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            readContacts();
        }

    }

    private void readContacts() {
       Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

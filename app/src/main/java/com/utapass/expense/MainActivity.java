package com.utapass.expense;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements onItemClickInterface{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CONTACTS = 1;
    ExpenseDbHelper helper;
    LinearLayout ll;
    RecyclerView recyclerView;
    public Expense_Adapter adater;

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
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });





        //Test for specific uri
        //Uri test = ContentUris.withAppendedId(ExpenseContacts.CONTENT_URI,3);
        //cursor = getContentResolver().query(test,null,null,null,null,null);


    }

    @Override
    protected void onStart() {
        super.onStart();
        // set up recycle view property
        refreshRecycleView();
        registerReceiver(receiver,new IntentFilter(ExpenseIntentService.LAST));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void refreshRecycleView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Cursor cursor = getContentResolver().query(ExpenseContacts.CONTENT_URI,null,null,null,null);
        adater = new Expense_Adapter(cursor);
        adater.setOnItemClickListener(this);
        recyclerView.setAdapter(adater);
    }
    private void selite(){

        //helper = new ExpenseDbHelper(this);
        //ll = (LinearLayout)findViewById(R.id.layout_list);
        //Cursor cursor = helper.getReadableDatabase().query(ExpenseContacts.TABLE_EXPENSE,null,null,null,null,null,null);
//
    }

    private void setlilearelayout() {
        //ll = (LinearLayout) findViewById(R.id.layout_list);
        //while (cursor.moveToNext()) {
        //    //int id = cursor.getInt(cursor.getColumnIndex(ExpenseContacts.Expense_Table.ID));
        //    //String cdata = cursor.getString(cursor.getColumnIndex(ExpenseContacts.Expense_Table.CDATE));
        //    //String info = cursor.getString(cursor.getColumnIndex(ExpenseContacts.Expense_Table.INFO));
        //    //String amount = cursor.getString(cursor.getColumnIndex(ExpenseContacts.Expense_Table.AMOUNT));
        //    //TextView tv = new TextView(this);
        //    //tv.setText("onCreate " + id + "/ " + cdata + "/" + info + " /" + amount);
        //    //ll.addView(tv);

        //}
    }
    private void readContact() {

        //dangerous permission checker
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            //readContact();
            Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                TextView tv = new TextView(this);
                tv.setText("Contact " + id + "/ " + id + "/" + name);
                ll.addView(tv);
            }

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_CONTACTS);
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



    @Override
    public void itemclicked(int position ,  Expense ex) {

        Intent detail = new Intent(this,Detail.class);
        detail.putExtra(getString(R.string.json_expenses), ex);
        startActivity(detail);

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ExpenseIntentService.LAST))
                Log.d("VA","receive posistion");
                refreshRecycleView();
        }
    };
}

package com.videxedge.ex1201;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.videxedge.ex1201.Grades.TABLE_GRADES;
import static com.videxedge.ex1201.Users.KEY_ID;
import static com.videxedge.ex1201.Users.TABLE_USERS;

public class Watchtables extends AppCompatActivity  implements AdapterView.OnItemClickListener {

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor c;

    ListView lvtables, lvrecords;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter adp;
    int tablechoise;
    AlertDialog.Builder adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchtables);

        lvtables=(ListView)findViewById(R.id.lvtables);
        lvrecords=(ListView)findViewById(R.id.lvrecords);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();
        db.close();

        lvtables.setOnItemClickListener(this);
        lvtables.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvrecords.setOnItemClickListener(this);
        lvrecords.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        tablechoise=0;

        String[] tables={TABLE_USERS,TABLE_GRADES};
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tables);
        lvtables.setAdapter(adp);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (parent == lvtables) {
            tbl = null;
            tablechoise = position + 1;
            if (tablechoise != 0) {
                db = hlp.getWritableDatabase();
                tbl = new ArrayList<>();
                if (tablechoise == 1) {
                    Cursor c = db.query(TABLE_USERS, null, null, null, null, null, null);
                    int col1 = c.getColumnIndex(Users.KEY_ID);
                    int col2 = c.getColumnIndex(Users.NAME);
                    int col3 = c.getColumnIndex(Users.PASSWORD);
                    int col4 = c.getColumnIndex(Users.AGE);

                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        int key = c.getInt(col1);
                        String name = c.getString(col2);
                        String pass = c.getString(col3);
                        int age = c.getInt(col4);
                        String tmp = "" + key + ", " + name + ", " + pass + ", " + age;
                        tbl.add(tmp);
                        c.moveToNext();
                    }
                } else {
                    Cursor c = db.query(TABLE_GRADES, null, null, null, null, null, null);
                    int col1 = c.getColumnIndex(Users.KEY_ID);
                    int col2 = c.getColumnIndex(Grades.SUBJECT);
                    int col3 = c.getColumnIndex(Grades.GRADE);

                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        int key = c.getInt(col1);
                        String sub = c.getString(col2);
                        int gra = c.getInt(col3);
                        String tmp = "" + key + ", " + sub + ", " + gra;
                        tbl.add(tmp);
                        c.moveToNext();
                    }
                }
                db.close();
                adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
                lvrecords.setAdapter(adp);
            } else {
                Toast.makeText(this, "Choose table", Toast.LENGTH_SHORT).show();
            }
        } else {
            String strtmp = tbl.get(position);
            adb = new AlertDialog.Builder(this);
            adb.setTitle("Are you sure ?");
            adb.setMessage("Are you sure you want to delete " + strtmp);
            adb.setPositiveButton("Yes !", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db = hlp.getWritableDatabase();
                    if (tablechoise == 1) {
                        db.delete(TABLE_USERS, KEY_ID+"=?", new String[]{Integer.toString(position + 1)});
                    } else {
                        db.delete(TABLE_GRADES, KEY_ID+"=?", new String[]{Integer.toString(position + 1)});
                    }
                    db.close();
                    tbl.remove(position);
                    adp.notifyDataSetChanged();
                }
            });
            adb.setNeutralButton("Cancel !", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog ad = adb.create();
            ad.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menuDataIn) {
            Intent t = new Intent(this, MainActivity.class);
            startActivity(t);
        }
        return true;
    }
}
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
    Cursor crsr;

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
            tbl = new ArrayList<>();
            tablechoise = position + 1;
            if (tablechoise != 0) {
                db = hlp.getWritableDatabase();
                if (tablechoise == 1) {
                    crsr = db.query(TABLE_USERS, null, null, null, null, null, null);
                    int col1 = crsr.getColumnIndex(Users.KEY_ID);
                    int col2 = crsr.getColumnIndex(Users.NAME);
                    int col3 = crsr.getColumnIndex(Users.PASSWORD);
                    int col4 = crsr.getColumnIndex(Users.AGE);

                    crsr.moveToFirst();
                    while (!crsr.isAfterLast()) {
                        int key = crsr.getInt(col1);
                        String name = crsr.getString(col2);
                        String pass = crsr.getString(col3);
                        int age = crsr.getInt(col4);
                        String tmp = "" + key + ", " + name + ", " + pass + ", " + age;
                        tbl.add(tmp);
                        crsr.moveToNext();
                    }
                } else {
                    crsr = db.query(TABLE_GRADES, null, null, null, null, null, null);
                    int col1 = crsr.getColumnIndex(Users.KEY_ID);
                    int col2 = crsr.getColumnIndex(Grades.SUBJECT);
                    int col3 = crsr.getColumnIndex(Grades.GRADE);

                    crsr.moveToFirst();
                    while (!crsr.isAfterLast()) {
                        int key = crsr.getInt(col1);
                        String sub = crsr.getString(col2);
                        int gra = crsr.getInt(col3);
                        String tmp = "" + key + ", " + sub + ", " + gra;
                        tbl.add(tmp);
                        crsr.moveToNext();
                    }
                }
                crsr.close();
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
        switch (id) {
            case (R.id.menuDataIn): {
                Intent t = new Intent(this, MainActivity.class);
                startActivity(t);
                break;
            }
            case (R.id.menuUpdate): {
                Intent t = new Intent(this, Update.class);
                startActivity(t);
                break;
            }
            case (R.id.menuSort): {
                Intent t = new Intent(this, Sort.class);
                startActivity(t);
                break;
            }
            default:
                break;
        }
        return true;
    }
}
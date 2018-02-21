package com.videxedge.ex1201;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.videxedge.ex1201.Grades.TABLE_GRADES;
import static com.videxedge.ex1201.Users.TABLE_USERS;

public class Watchtables extends AppCompatActivity  implements AdapterView.OnItemClickListener {

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor c;

    ListView lvtables, lvrecords;
    ArrayAdapter adp;
    int tablechoise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchtables);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();
        db.close();

        lvtables=(ListView)findViewById(R.id.lvtables);
        lvtables.setOnItemClickListener(this);
        lvtables.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvrecords=(ListView)findViewById(R.id.lvrecords);
        lvrecords.setOnItemClickListener(this);
        lvrecords.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        tablechoise=0;

        String[] tables={TABLE_USERS,TABLE_GRADES};
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tables);
        lvtables.setAdapter(adp);

    }

    public void back(View view) {
        Intent t = new Intent(this, MainActivity.class);
        startActivity(t);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent==lvtables) {
            tablechoise = position + 1;
            if (tablechoise != 0) {
                hlp = new HelperDB(this);
                db = hlp.getWritableDatabase();
                ArrayList<String> tbl = new ArrayList<>();
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
        }
    }
}
package com.videxedge.ex1201;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import static com.videxedge.ex1201.Users.TABLE_USERS;

public class Sort extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lVtbl, lVfld2sort, lVsorted;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    ArrayList<String> tblRec, tblFiled;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter<String> adpTables, adpFields, adpData;
    int table;
    String[] tables, fields;
    String tbl2sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        lVtbl=(ListView)findViewById(R.id.lVtbl);
        lVfld2sort=(ListView)findViewById(R.id.lVfld2sort);
        lVsorted=(ListView)findViewById(R.id.lVsorted);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();
        db.close();

        lVtbl.setOnItemClickListener(this);
        lVtbl.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lVfld2sort.setOnItemClickListener(this);
        lVfld2sort.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        table=-1;

        tables= new String[]{TABLE_USERS, TABLE_GRADES};
        ArrayAdapter<String> adpTables=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tables);
        lVtbl.setAdapter(adpTables);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (lVtbl.equals(parent)) {
            table = position;
            if (table==0) {
                tbl2sort= TABLE_USERS;
                fields= new String[]{Users.KEY_ID, Users.NAME, Users.PASSWORD, Users.AGE};
            } else {
                tbl2sort= TABLE_GRADES;
                fields= new String[]{Users.KEY_ID, Grades.SUBJECT, Grades.GRADE};
            }
            ArrayAdapter<String> adpFields=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,fields);
            lVfld2sort.setAdapter(adpFields);
        } else if (lVfld2sort.equals(parent)) {
            if (table != -1) {
                tbl = new ArrayList<>();
                db=hlp.getReadableDatabase();
                if (table == 0) {
                    crsr=db.query(TABLE_USERS,null,null,null,null,null,fields[position]);
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
                    crsr = db.query(TABLE_GRADES, null, null, null, null, null,fields[position]);
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
                adpData = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
                lVsorted.setAdapter(adpData);
            } else {
                Toast.makeText(this, "Choose table first !", Toast.LENGTH_LONG).show();
            }
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
            case (R.id.menuWatch): {
                Intent t = new Intent(this, Watchtables.class);
                startActivity(t);
                break;
            }
            case (R.id.menuUpdate): {
                Intent t = new Intent(this, Update.class);
                startActivity(t);
                break;
            }
            default:
                break;
        }
        return true;
    }
}

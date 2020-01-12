package com.videxedge.ex1201;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    HelperDB hlp;

    EditText eTname, eTpass, eTage, eTsub, eTgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTname = (EditText) findViewById(R.id.eTname);
        eTpass = (EditText) findViewById(R.id.eTpass);
        eTage = (EditText) findViewById(R.id.eTage);
        eTsub = (EditText) findViewById(R.id.eTsub);
        eTgrade = (EditText) findViewById(R.id.eTgrade);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
    }

    public void usersdatain(View view) {
        String name, pass, strage;
        int age;

        name = eTname.getText().toString();
        pass = eTpass.getText().toString();
        strage = eTage.getText().toString();
        age = Integer.parseInt(strage);

        ContentValues cv = new ContentValues();
        cv.put(Users.NAME, name);
        cv.put(Users.PASSWORD, pass);
        cv.put(Users.AGE, age);

        db = hlp.getWritableDatabase();
        db.insert(Users.TABLE_USERS, null, cv);
        db.close();
    }

    public void gradesdatain(View view) {
        String subject, strgrade;
        int grade;

        subject = eTsub.getText().toString();
        strgrade = eTgrade.getText().toString();
        grade = Integer.parseInt(strgrade);

        ContentValues cv = new ContentValues();
        cv.put(Grades.SUBJECT, subject);
        cv.put(Grades.GRADE, grade);

        db = hlp.getWritableDatabase();
        db.insert(Grades.TABLE_GRADES, null, cv);
        db.close();
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
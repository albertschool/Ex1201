package com.videxedge.ex1201;

import static com.videxedge.ex1201.Users.TABLE_USERS;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Demo app to use SQLite
 * Created by Albert on 16/01/2017.
 */
/**
 * The activity Main
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText eTname, eTpass, eTage, eTsub, eTgrade;
    private Spinner spinKey_id;

    private SQLiteDatabase db;
    private HelperDB hlp;
    private ArrayList<String> namesList;
    private ArrayList<User> users;
    private ArrayAdapter adp;
    private User tmpUser;
    private Grade tmpGrade;
    private int key_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAll();
    }

    /**
     * initAll
     * <p>
     * This method init the views& database
     */
    private void initAll() {
        eTname = (EditText) findViewById(R.id.eTname);
        eTpass = (EditText) findViewById(R.id.eTpass);
        eTage = (EditText) findViewById(R.id.eTage);
        spinKey_id = (Spinner) findViewById(R.id.spinKey_id);
        eTsub = (EditText) findViewById(R.id.eTsub);
        eTgrade = (EditText) findViewById(R.id.eTgrade);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        users = new ArrayList<>();
        namesList = new ArrayList<>();
        namesList.add("Choose a user:");
        key_id = 0;
        db=hlp.getReadableDatabase();
        Cursor crsr=db.query(TABLE_USERS, null, null, null, null, null, null);
        int colKEY_ID = crsr.getColumnIndex(Users.KEY_ID);
        int colNAME = crsr.getColumnIndex(Users.NAME);
        int colPASSWORD = crsr.getColumnIndex(Users.PASSWORD);
        int colAGE = crsr.getColumnIndex(Users.AGE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            tmpUser = new User();
            tmpUser.setKey_id(crsr.getInt(colKEY_ID));
            tmpUser.setName(crsr.getString(colNAME));
            tmpUser.setPassword(crsr.getString(colPASSWORD));
            tmpUser.setAge(crsr.getInt(colAGE));
            users.add(tmpUser);
            namesList.add(tmpUser.getName());
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, namesList);
        spinKey_id.setAdapter(adp);
        spinKey_id.setOnItemSelectedListener(this);
    }

    /**
     * Usersdatain
     * <p>
     * This method push data to Users table
     * @param view the view
     */
    public void usersdatain(View view) {
        String name, pass, strage;
        int age;

        name = eTname.getText().toString();
        pass = eTpass.getText().toString();
        strage = eTage.getText().toString();
        if (!name.isEmpty() && !pass.isEmpty() && !strage.isEmpty()) {
            tmpUser = new User(name, pass, Integer.parseInt(strage));

            ContentValues cv = new ContentValues();
            cv.put(Users.NAME, tmpUser.getName());
            cv.put(Users.PASSWORD, tmpUser.getPassword());
            cv.put(Users.AGE, tmpUser.getAge());

            db = hlp.getWritableDatabase();
            key_id = (int) db.insert(Users.TABLE_USERS, null, cv);
            db.close();

            eTname.setText("");
            eTpass.setText("");
            eTage.setText("");
            if (key_id != -1) {
                Toast.makeText(this, "Data pushed to Users table", Toast.LENGTH_LONG).show();
                tmpUser.setKey_id(key_id);
                users.add(tmpUser);
                namesList.add(tmpUser.getName());
                adp.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Gradesdatain
     * <p>
     * This method push data to Grades table
     * @param view the view
     */
    public void gradesdatain(View view) {
        String subject, strgrade;
        int grade;

        subject = eTsub.getText().toString();
        strgrade = eTgrade.getText().toString();
        if (!subject.isEmpty() && !strgrade.isEmpty() && key_id != 0) {
            tmpGrade = new Grade(key_id, subject, Integer.parseInt(strgrade));

            ContentValues cv = new ContentValues();
            cv.put(Grades.KEY_ID, tmpGrade.getKey_id());
            cv.put(Grades.SUBJECT, tmpGrade.getSubject());
            cv.put(Grades.GRADE, tmpGrade.getGrade());

            db = hlp.getWritableDatabase();
            db.insert(Grades.TABLE_GRADES, null, cv);
            db.close();

            key_id = 0;
            spinKey_id.setSelection(key_id);
            eTsub.setText("");
            eTgrade.setText("");
            Toast.makeText(this, "Data pushed to Grades table", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * onCreateOptionsMenu
     * <p>
     * This method create the menu options
     * @param menu the menu
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos != 0) {
            key_id = users.get(pos - 1).getKey_id();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    /**
     * onOptionsItemSelected
     * <p>
     * This method react to the menu option selected
     * @param item the menu item selected
     */
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
        return super.onOptionsItemSelected(item);
    }
}
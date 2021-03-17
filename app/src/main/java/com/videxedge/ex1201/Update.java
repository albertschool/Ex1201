package com.videxedge.ex1201;

import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.videxedge.ex1201.Grades.TABLE_GRADES;
import static com.videxedge.ex1201.Users.KEY_ID;
import static com.videxedge.ex1201.Users.TABLE_USERS;

/**
 * The activity Update
 * <p>
 * in this activity the user can update data of records in the tables
 */
public class Update extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lVtable, lVrecord, lVfield;
    EditText eTdata;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    int col1, col2, col3, col4;
    String strtmp, olddata, newdata;

    ArrayList<String> tblRec, tblFiled;
    ArrayAdapter<String> adpRecord, adpField;
    int table, record, field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        lVtable=(ListView)findViewById(R.id.lVtable);
        lVrecord=(ListView)findViewById(R.id.lVrecord);
        lVfield=(ListView)findViewById(R.id.lVfield);
        eTdata=(EditText)findViewById(R.id.eTdata);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();
        db.close();

        lVtable.setOnItemClickListener(this);
        lVtable.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lVrecord.setOnItemClickListener(this);
        lVrecord.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lVfield.setOnItemClickListener(this);
        lVfield.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        table=-1;
        record=-1;

        String[] tables={TABLE_USERS,TABLE_GRADES};
        ArrayAdapter<String> adpTable=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tables);
        lVtable.setAdapter(adpTable);

    }

    /**
     * onItemClick
     * <p>
     * This method react to the selected option in the listViews
     * @param parent the listView selected
     * @param view the view
     * @param position the position selected
     * @param id the id selected
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (lVtable.equals(parent)) {
            table = position;
            // table to update
            tblRec = new ArrayList<>();
            db = hlp.getReadableDatabase();
            // read the data
            switch (table) {
                case (0): {
                    crsr = db.query(TABLE_USERS, null, null, null, null, null, null);
                    col1 = crsr.getColumnIndex(Users.KEY_ID);
                    col2 = crsr.getColumnIndex(Users.NAME);
                    col3 = crsr.getColumnIndex(Users.PASSWORD);
                    col4 = crsr.getColumnIndex(Users.AGE);

                    crsr.moveToFirst();
                    while (!crsr.isAfterLast()) {
                        int key = crsr.getInt(col1);
                        String name = crsr.getString(col2);
                        String pass = crsr.getString(col3);
                        int age = crsr.getInt(col4);
                        String tmp = "" + key + ", " + name + ", " + pass + ", " + age;
                        tblRec.add(tmp);
                        crsr.moveToNext();
                    }
                    break;
                }
                case (1): {
                    crsr = db.query(TABLE_GRADES, null, null, null, null, null, null);
                    col1 = crsr.getColumnIndex(Users.KEY_ID);
                    col2 = crsr.getColumnIndex(Grades.SUBJECT);
                    col3 = crsr.getColumnIndex(Grades.GRADE);

                    crsr.moveToFirst();
                    while (!crsr.isAfterLast()) {
                        int key = crsr.getInt(col1);
                        String sub = crsr.getString(col2);
                        int gra = crsr.getInt(col3);
                        String tmp = "" + key + ", " + sub + ", " + gra;
                        tblRec.add(tmp);
                        crsr.moveToNext();
                    }
                    break;
                }
            }
            crsr.close();
            db.close();
            // display the records
            adpRecord= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tblRec);
            lVrecord.setAdapter(adpRecord);
        } else if (lVrecord.equals(parent)) {
            if (table != -1) {
                record = position;
                // record to update
                strtmp=tblRec.get(record);
                strtmp=strtmp.substring(0,strtmp.indexOf(","));
                tblFiled = new ArrayList<>();
                db = hlp.getReadableDatabase();
                // read the data
                switch (table) {
                    case (0): {
                        crsr = db.query(TABLE_USERS, null, KEY_ID+"=?", new String[]{strtmp}, null, null, null);
                        crsr.moveToFirst();
                        col1 = crsr.getColumnIndex(Users.KEY_ID);
                        col2 = crsr.getColumnIndex(Users.NAME);
                        col3 = crsr.getColumnIndex(Users.PASSWORD);
                        col4 = crsr.getColumnIndex(Users.AGE);

                        tblFiled.add(String.valueOf(crsr.getInt(col1)));
                        tblFiled.add(crsr.getString(col2));
                        tblFiled.add(crsr.getString(col3));
                        tblFiled.add(String.valueOf(crsr.getInt(col4)));
                        break;
                    }
                    case (1): {
                        crsr = db.query(TABLE_GRADES, null, KEY_ID+"=?", new String[]{strtmp}, null, null, null);
                        crsr.moveToFirst();
                        col1 = crsr.getColumnIndex(Users.KEY_ID);
                        col2 = crsr.getColumnIndex(Grades.SUBJECT);
                        col3 = crsr.getColumnIndex(Grades.GRADE);

                        tblFiled.add(String.valueOf(crsr.getInt(col1)));
                        tblFiled.add(crsr.getString(col2));
                        tblFiled.add(String.valueOf(crsr.getInt(col3)));
                        break;
                    }
                }
                crsr.close();
                db.close();
                // display the data
                adpField= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tblFiled);
                lVfield.setAdapter(adpField);
            } else {
                Toast.makeText(this, "You have to choose a table first", Toast.LENGTH_LONG).show();
            }
        } else if (lVfield.equals(parent)) {
            if (table != -1 && record != -1) {
                field = position;
                olddata=tblFiled.get(field);
                eTdata.setHint(olddata);
            } else {
                Toast.makeText(this, "You have to choose a table & record first", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * updatebtn
     * <p>
     * This method update the data in the selected field of table
     * @param view the view
     */
    public void updatebtn(View view) {
        newdata=eTdata.getText().toString();
        ContentValues cv = new ContentValues();
        db = hlp.getWritableDatabase();
        if (table==0){
            switch (field) {
                case (0):
                    cv.put(KEY_ID, Integer.parseInt(newdata));
                    db.update(TABLE_USERS,cv,KEY_ID+"=?", new String[]{olddata});
                    break;
                case (1):
                    cv.put(Users.NAME, newdata);
                    db.update(TABLE_USERS,cv,Users.NAME+"=?", new String[]{olddata});
                    break;
                case (2):
                    cv.put(Users.PASSWORD, newdata);
                    db.update(TABLE_USERS,cv,Users.PASSWORD+"=?", new String[]{olddata});
                    break;
                case (3):
                    cv.put(Users.AGE, Integer.parseInt(newdata));
                    db.update(TABLE_USERS,cv,Users.AGE+"=?", new String[]{olddata});
                    break;
            }
        } else {
            switch (field) {
                case (0):
                    cv.put(KEY_ID, Integer.parseInt(newdata));
                    db.update(TABLE_GRADES,cv,KEY_ID+"=?", new String[]{olddata});
                    break;
                case (1):
                    cv.put(Grades.SUBJECT, newdata);
                    db.update(TABLE_GRADES,cv,Grades.SUBJECT+"=?", new String[]{olddata});
                    break;
                case (2):
                    cv.put(Grades.GRADE, Integer.parseInt(newdata));
                    db.update(TABLE_GRADES,cv,Grades.GRADE+"=?", new String[]{olddata});
                    break;
            }
        }
        db.close();
        record=-1;
        tblRec.clear();
        adpRecord.notifyDataSetChanged();
        field=-1;
        tblFiled.clear();
        adpField.notifyDataSetChanged();
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
        return true;
    }

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

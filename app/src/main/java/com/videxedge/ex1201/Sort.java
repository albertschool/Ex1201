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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.videxedge.ex1201.Grades.TABLE_GRADES;
import static com.videxedge.ex1201.Users.TABLE_USERS;

/**
 * The activity Sort
 * <p>
 * in this activity the user can view the tables sorted by desired field
 */
public class Sort extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener {

    private Spinner spinTables;
    private ListView lVfld2sort, lVsorted;

    private SQLiteDatabase db;
    private HelperDB hlp;
    private Cursor crsr;

    private ArrayList<String> tbl = new ArrayList<>();
    private ArrayAdapter<String> adpData;
    private int table;
    private String[] tables, fields;
    private String tbl2sort;

    String[] columns = null;
    String selection = null;
    String[] selectionArgs = null;
    String groupBy = null;
    String having = null;
    String orderBy = null;
    String limit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        initAll();

        table=-1;

        tables= new String[]{TABLE_USERS, TABLE_GRADES};
        ArrayAdapter<String> adpTables=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tables);
        spinTables.setAdapter(adpTables);
    }

    /**
     * initAll
     * <p>
     * This method init the views& database
     */
    private void initAll() {
        spinTables=(Spinner)findViewById(R.id.spinTables);
        lVfld2sort=(ListView)findViewById(R.id.lVfld2sort);
        lVsorted=(ListView)findViewById(R.id.lVsorted);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();
        db.close();

        spinTables.setOnItemSelectedListener(this);
        lVfld2sort.setOnItemClickListener(this);
        lVfld2sort.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
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
        if (lVfld2sort.equals(parent)) {
            if (table != -1) {
                tbl = new ArrayList<>();
                db=hlp.getReadableDatabase();
                orderBy=fields[position];
                // read the data sorted
                if (table == 0) {
                    crsr=db.query(TABLE_USERS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                    int colKEY_ID = crsr.getColumnIndex(Users.KEY_ID);
                    int colNAME = crsr.getColumnIndex(Users.NAME);
                    int colPASSWORD = crsr.getColumnIndex(Users.PASSWORD);
                    int colAGE = crsr.getColumnIndex(Users.AGE);

                    crsr.moveToFirst();
                    while (!crsr.isAfterLast()) {
                        int key = crsr.getInt(colKEY_ID);
                        String name = crsr.getString(colNAME);
                        String pass = crsr.getString(colPASSWORD);
                        int age = crsr.getInt(colAGE);
                        String tmp = "" + key + ", " + name + ", " + pass + ", " + age;
                        tbl.add(tmp);
                        crsr.moveToNext();
                    }
                } else {
                    crsr=db.query(TABLE_GRADES, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                    int colKEY_ID = crsr.getColumnIndex(Users.KEY_ID);
                    int colSUBJECT = crsr.getColumnIndex(Grades.SUBJECT);
                    int colGRADE = crsr.getColumnIndex(Grades.GRADE);

                    crsr.moveToFirst();
                    while (!crsr.isAfterLast()) {
                        int key = crsr.getInt(colKEY_ID);
                        String sub = crsr.getString(colSUBJECT);
                        int gra = crsr.getInt(colGRADE);
                        String tmp = "" + key + ", " + sub + ", " + gra;
                        tbl.add(tmp);
                        crsr.moveToNext();
                    }
                }
                crsr.close();
                db.close();
                // display the data
                adpData = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
                lVsorted.setAdapter(adpData);
            } else {
                Toast.makeText(this, "Choose table first !", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        table = pos;
        // table to sort
        if (table==0) {
            tbl2sort= TABLE_USERS;
            fields= new String[]{Users.KEY_ID, Users.NAME, Users.PASSWORD, Users.AGE};
        } else {
            tbl2sort= TABLE_GRADES;
            fields= new String[]{Grades.KEY_ID, Grades.SUBJECT, Grades.GRADE};
        }
        ArrayAdapter<String> adpFields=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,fields);
        lVfld2sort.setAdapter(adpFields);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

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
            case (R.id.menuUpdate): {
                Intent t = new Intent(this, Update.class);
                startActivity(t);
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

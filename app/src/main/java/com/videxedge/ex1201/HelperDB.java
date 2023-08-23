package com.videxedge.ex1201;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.videxedge.ex1201.Grades.GRADE;
import static com.videxedge.ex1201.Grades.SUBJECT;
import static com.videxedge.ex1201.Grades.TABLE_GRADES;
import static com.videxedge.ex1201.Users.AGE;
import static com.videxedge.ex1201.Users.KEY_ID;
import static com.videxedge.ex1201.Users.NAME;
import static com.videxedge.ex1201.Users.PASSWORD;
import static com.videxedge.ex1201.Users.TABLE_USERS;

/**
 * The type HelperDB
 */
public class HelperDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbexam.db";
    private static final int DATABASE_VERSION = 1;
    private String strCreate, strDelete;

    /**
     * Instantiates a new HelperDB
     *
     * @param context the context
     */
    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate
     * <p>
     * This method create the tables in database
     * @param db the SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        strCreate="CREATE TABLE "+TABLE_USERS;
        strCreate+=" ("+KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+NAME+" TEXT,";
        strCreate+=" "+PASSWORD+" TEXT,";
        strCreate+=" "+AGE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_GRADES;
        strCreate+=" ("+KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+SUBJECT+" TEXT,";
        strCreate+=" "+GRADE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);
    }

    /**
     * onUpgrade
     * <p>
     * This method upgrade the database
     * @param db the SQLite database
     * @param oldVer the old version code
     * @param newVer the new version code
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        strDelete="DROP TABLE IF EXISTS "+TABLE_USERS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+TABLE_GRADES;
        db.execSQL(strDelete);

        onCreate(db);
    }
}

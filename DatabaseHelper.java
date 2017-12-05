package com.soc.matthewhaynes.sqliteapp; //this is different depending on the PATH to your project. remember to CLEAN -> REBUILD when downloading

/**********************************  CUT BELOW HERE TO PASTE *****************************************************/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matthew.haynes on 10/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SOC.db"; //declare name of database
    public static final String TABLE_NAME = "SOCtable";  //declare name of table in database
    public static final String COL_1 = "DEPT";           //declare column name
    public static final String COL_2 = "CLASS";          //declare column name
    public static final String COL_3 = "SECTION";        //declare column name
    public static final String COL_4 = "TIME";           //declare column name


    /* CONSTRUCTOR */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1); //database created when constructor is called
        //context.deleteDatabase(DATABASE_NAME); deletes the database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//creates table on creation of object, takes the db as a parameter
        db.execSQL("create table " + TABLE_NAME +" (DEPT TEXT,CLASS TEXT,SECTION TEXT,TIME TEXT)"); //create DB ROW names and data type
    }

    /* not sure, methinks it upgrades to new versions of database */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    /* Insert data into the DB */
    public boolean insertData(String dept, String classs, String section, String time) {
        SQLiteDatabase db = this.getWritableDatabase();    //declare db we want to alter
        ContentValues contentValues = new ContentValues(); //ContentValues creates and EMPTY SET of values ..below fills empty set
        contentValues.put(COL_1,dept);           //insert data into db column 1
        contentValues.put(COL_2,classs);         //insert data into db column 2
        contentValues.put(COL_3,section);        //insert data into db column 3
        contentValues.put(COL_4,time);           //insert data into db column 4
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /* A query to get all the data */
    public Cursor getAllData() {   //a Cursor object points to a SINGLE row of the result fetched by a db query ..so we return Cursor object 'res'
        SQLiteDatabase db = this.getWritableDatabase();             //declare db we want to alter
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null); //this is the actual db query
        return res;
    }

    /* UPDATE IS CURRENTLY BROKEN, ..needs a primary key declared (in place of TEXT) in onCreate() to work as written.   */
    public boolean updateData(String dept,String classs,String section,String time) {
        SQLiteDatabase db = this.getWritableDatabase();        //retrieve db to write to it
        ContentValues contentValues = new ContentValues();     //declare ContentValue object
        contentValues.put(COL_1,dept);                         //update data into column 1
        contentValues.put(COL_2,classs);                       //update data into column 2
        contentValues.put(COL_3,section);                      //update data into column 3
        contentValues.put(COL_4,time);                         //update data into column 4
        db.update(TABLE_NAME, contentValues, "DEPT = ?",new String[] { dept });  //update the database with primary key (not declared ..is why broke)
        return true;
    }

    /* returns 1 for successful delete from db, 0 for fail */
    public Integer deleteData (String dept) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "DEPT = ?",new String[] {dept}); //deletes according to string in DEPT filed (1st txt field)
    }
}

package com.soc.matthewhaynes.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matthew.haynes on 10/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    /* Declare String Vars */
    public static final String DATABASE_NAME = "SOC.db";
    public static final String TABLE_NAME = "SOCtable";
    public static final String COL_1 = "DEPT";
    public static final String COL_2 = "CLASS";
    public static final String COL_3 = "SECTION";
    public static final String COL_4 = "TIME";


    /* CONSTRUCTOR */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1); //database created when constructor is called
        //context.deleteDatabase(DATABASE_NAME); deletes the database
    }

    /* Creates table columns */
    @Override
    public void onCreate(SQLiteDatabase db) {//creates table when called
 /*       db.execSQL( "create table " + "SOCTable" + "(" +
                "_id integer primary key autoincrement, " +
                COL_1 + "," +
                COL_2 + "," +
                COL_3 + "," +
                COL_4 + ")"
        ); */ //executes query pased inside arguement
        db.execSQL("create table " + TABLE_NAME +" (DEPT TEXT,CLASS TEXT,SECTION TEXT,TIME TEXT)");
    }

    /* all upgrade functions are broken. they need a primary key to work */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    /* insert data into sql database */
    public boolean insertData(String dept, String classs, String section, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,dept);
        contentValues.put(COL_2,classs);
        contentValues.put(COL_3,section);
        contentValues.put(COL_4,time);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /* A query to get all the data */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    /* UPDATE IS CURRENTLY BROKEN, ..could be used with class ID */
    public boolean updateData(String dept,String classs,String section,String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,dept);
        contentValues.put(COL_2,classs);
        contentValues.put(COL_3,section);
        contentValues.put(COL_4,time);
        db.update(TABLE_NAME, contentValues, "DEPT = ?",new String[] { dept });
        return true;
    }

    /* returns 1 for successful delete, 0 for fail. deleteData() currently only looks at DEPT field */
    public Integer deleteData (String dept) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "DEPT = ?",new String[] {dept});
    }
}

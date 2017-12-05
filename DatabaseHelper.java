package com.soc.matthewhaynes.soc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by matthew.haynes on 12/2/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String dbName = "SOC.db";
    public static final String tblCatalogue = "tblCatalogue";
    public static final String tblClass = "tblClass";
    public static final String col_1 = "id";
    public static final String col_2 = "subject";
    public static final String col_3 = "class";
    public static final String col_4 = "section";
    public static final String col_5 = "description";
    public static final String col_6 = "date";
    public static final String col_7 = "day";
    public static final String col_8 = "time";
    public static final String col_9 = "roomNum";
    public static final String col_10 = "pop";
    public static final String col_11 = "waitList";
    public static final String col_12 = "profesor";
    public static final String col_13 = "credits";
    public static final String col_14 = "location";

    public static final String Create_tblCat = "CREATE TABLE IF NOT EXISTS " + tblCatalogue + " (" +
            col_1 + " TEXT, " +
            col_2 + " TEXT, " +
            col_3 + " TEXT, " +
            col_4 + " TEXT, " +
            col_5 + " TEXT, " +
            col_6 + " TEXT, " +
            col_7 + " TEXT, " +
            col_8 + " TEXT, " +
            col_9 + " TEXT, " +
            col_10 + " TEXT, " +
            col_11 + " TEXT, " +
            col_12 + " TEXT, " +
            col_13 + " TEXT, " +
            col_14 + " TEXT " +
            "); ";

    public static final String Create_tblClass = "CREATE TABLE IF NOT EXISTS " + tblClass + " (" +
            col_1 + " TEXT, " +
            col_2 + " TEXT, " +
            col_3 + " TEXT, " +
            col_4 + " TEXT, " +
            col_5 + " TEXT, " +
            col_6 + " TEXT, " +
            col_7 + " TEXT, " +
            col_8 + " TEXT, " +
            col_9 + " TEXT, " +
            col_10 + " TEXT, " +
            col_11 + " TEXT, " +
            col_12 + " TEXT, " +
            col_13 + " TEXT, " +
            col_14 + " TEXT " +
            "); ";
    ;

    /* CONSTRUCTOR */
    public DatabaseHelper(Context context) {
        super(context, dbName, null, 1); //database created when constructor is called
        //context.deleteDatabase(DATABASE_NAME); //deletes the database

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_tblCat); //create DB ROW names and data type
        db.execSQL(Create_tblClass);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    //---opens the database---
    public DatabaseHelper open() throws SQLException
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.close();
    }

    /* Insert data into the DB */
    public boolean insertData(String tableName, String c1, String c2, String c3, String c4, String c5, String c6, String c7, String c8, String c9, String c10, String c11, String c12, String c13, String c14) {
        SQLiteDatabase db = this.getWritableDatabase();    //declare db we want to alter
        ContentValues contentValues = new ContentValues(); //ContentValues creates and EMPTY SET of values ..below fills empty set
        contentValues.put(col_1, c1 );       //insert data into db column 1
        contentValues.put(col_2, c2 );       //insert data into db column 2
        contentValues.put(col_3, c3);        //insert data into db column 3
        contentValues.put(col_4, c4);        //insert data into db column 4
        contentValues.put(col_5, c5);        //insert data into db column 5
        contentValues.put(col_6, c6);        //insert data into db column 6
        contentValues.put(col_7, c7);        //insert data into db column 7
        contentValues.put(col_8, c8);        //insert data into db column 8
        contentValues.put(col_9, c9);        //insert data into db column 9
        contentValues.put(col_10, c10);      //insert data into db column 10
        contentValues.put(col_11, c11);      //insert data into db column 11
        contentValues.put(col_12, c12);      //insert data into db column 12
        contentValues.put(col_13, c13);      //insert data into db column 13
        contentValues.put(col_14, c14);      //insert data into db column 14


        long result = db.insert(tableName, null, contentValues);
        if (result == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    /* A query to get all the data */
    public Cursor getAllData(String tableName) {   //a Cursor object points to a SINGLE row of the result fetched by a db query ..so we return Cursor object 'res'
        SQLiteDatabase db = this.getReadableDatabase();             //declare db we want to alter
        Cursor res = db.rawQuery("select * from " + tableName, null); //this is the actual db query
        //db.close();
        return res;

    }

    public Cursor checkDbExists(String tableName){

        SQLiteDatabase db = this.getReadableDatabase();             //declare db we want to alter
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tblCatalogue + "'", null);

       return cursor;
    }

    /* get set info to set to cardview */
    public List<GetInfo> getSetInfo(){

        //array of columns to fedtch
        String[] columns={col_1, col_2, col_3, col_4};

        //sorting orders
        //String sortOrder= COL_2 + "ASC";
        List<GetInfo> infoList= new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tblCatalogue, columns, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                GetInfo getInfo = new GetInfo("stuff","stuff","stuff","stuff");

                getInfo.setId(cursor.getString(cursor.getColumnIndex(col_1)));
                getInfo.setSubject(cursor.getString(cursor.getColumnIndex(col_2)));
                getInfo.setClas(cursor.getString(cursor.getColumnIndex(col_3)));
                getInfo.setSection(cursor.getString(cursor.getColumnIndex(col_4)));

                //add record to list
                infoList.add(getInfo);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return infoList;
    }

}

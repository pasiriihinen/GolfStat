package com.pasiriihinen.golfstat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "courses.db";
    //Courses table
    public static final String TABLE_NAME = "course_table";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "NAME";
    public static final String COL_PAR = "PAR";
    //Rounds table
    public static final String ROUNDS_TABLE = "rounds_table";
    public static final String ROUND_ID = "id";
    public static final String ROUND_DATE = "DATE";
    public static final String ROUND_COURSE = "COURSE";
    public static final String ROUND_HOLENUM = "HOLENUM";
    public static final String ROUND_HOLEPAR = "HOLEPAR";
    public static final String ROUND_HOLESCORE   = "HOLESCORE";
    public static final String ROUND_HOLEPUTTS = "HOLEPUTTS";
    public static final String ROUND_HOLENFW = "HOLEFW";
    public static final String ROUND_HOLECHIP = "HOLECHIP";
    public static final String ROUND_HOLEPENALTY = "HOLEPENALTY";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PAR INTEGER)");
        //Create rounds table
        db.execSQL("create table " + ROUNDS_TABLE + "(id INTEGER PRIMARY KEY, DATE TEXT, COURSE TEXT, HOLENUM INTEGER, HOLEPAR INTEGER, HOLESCORE INTEGER, HOLEPUTTS INTEGER, HOLEFW TEXT, HOLECHIP TEXT, HOLEPENALTY TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_PAR, par);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Post score, putts, fw, chip and penalty info for one hole
    public boolean postHoleData(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ROUND_ID, id);
        long result = db.insert(ROUNDS_TABLE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getCourseNameData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME, null);
        return result;
    }




}

package com.example.stanislavlukanov.td.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SQL_Tab1_DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "tab_db";

    public SQL_Tab1_DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(SQL_Tab1.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQL_Tab1.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertDev (String name, String time, int year, int month, int day){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SQL_Tab1.COLUMN_NAME, name);
        values.put(SQL_Tab1.COLUMN_TIME, time);
        values.put(SQL_Tab1.COLUMN_YEAR, year);
        values.put(SQL_Tab1.COLUMN_MONTH, month);
        values.put(SQL_Tab1.COLUMN_DAY, day);

        long id = db.insert(SQL_Tab1.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public SQL_Tab1 getDev(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SQL_Tab1.TABLE_NAME,
                new String[]{SQL_Tab1.COLUMN_ID, SQL_Tab1.COLUMN_NAME, SQL_Tab1.COLUMN_TIME,
                SQL_Tab1.COLUMN_YEAR, SQL_Tab1.COLUMN_MONTH, SQL_Tab1.COLUMN_DAY},
                SQL_Tab1.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        SQL_Tab1 dev = new SQL_Tab1(
                cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SQL_Tab1.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(SQL_Tab1.COLUMN_TIME)),
                cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_YEAR)),
                cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_MONTH)),
                cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_DAY)));

        cursor.close();

        return dev;
    }

    public List<SQL_Tab1> getAll(){
        List<SQL_Tab1> deves = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + SQL_Tab1.TABLE_NAME + " ORDER BY " +
                SQL_Tab1.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                SQL_Tab1 dev = new SQL_Tab1();

                dev.setId(cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_ID)));
                dev.setName(cursor.getString(cursor.getColumnIndex(SQL_Tab1.COLUMN_NAME)));
                dev.setTime(cursor.getString(cursor.getColumnIndex(SQL_Tab1.COLUMN_TIME)));
                dev.setYear(cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_YEAR)));
                dev.setMonth(cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_MONTH)));
                dev.setDay(cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_DAY)));

                deves.add(dev);

            } while (cursor.moveToNext());
        }

        db.close();

        return deves;
    }

    public int getDevsCount(){
        String countQuery = "SELECT * FROM " + SQL_Tab1.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }


    public int updateDev(SQL_Tab1 dev){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SQL_Tab1.COLUMN_NAME, dev.getName());
        values.put(SQL_Tab1.COLUMN_TIME, dev.getTime());
        values.put(SQL_Tab1.COLUMN_YEAR, dev.getYear());
        values.put(SQL_Tab1.COLUMN_MONTH, dev.getMonth());
        values.put(SQL_Tab1.COLUMN_DAY, dev.getDay());


        return db.update(SQL_Tab1.TABLE_NAME, values, SQL_Tab1.COLUMN_ID+ " = ?",
                new String[]{String.valueOf(dev.getId())});
    }

    public void deleteDev (SQL_Tab1 dev) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SQL_Tab1.TABLE_NAME, SQL_Tab1.COLUMN_ID + " = ?",
                new String[]{String.valueOf(dev.getId())});

        db.close();
    }
}

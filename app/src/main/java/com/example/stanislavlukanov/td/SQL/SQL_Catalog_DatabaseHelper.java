package com.example.stanislavlukanov.td.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQL_Catalog_DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "catalog_db";

    public SQL_Catalog_DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_Catalog.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQL_Catalog.TABLE_NAME);

        onCreate(db);
    }

    public long insertCatalog(String contact,String description, String mail, String number) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SQL_Catalog.COLUMN_CONTACT, contact);
        values.put(SQL_Catalog.COLUMN_DESCRIPTION, description);
        values.put(SQL_Catalog.COLUMN_MAIL, mail);
        values.put(SQL_Catalog.COLUMN_NUMBER, number);


        long id = db.insert(SQL_Catalog.TABLE_NAME, null, values);

        db.close();

        return id;
    }



    public SQL_Catalog getContact(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SQL_Catalog.TABLE_NAME,
                new String[]{SQL_Catalog.COLUMN_ID, SQL_Catalog.COLUMN_CONTACT, SQL_Catalog.COLUMN_DESCRIPTION,SQL_Catalog.COLUMN_MAIL,
                        SQL_Catalog.COLUMN_NUMBER},
                SQL_Catalog.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        SQL_Catalog contact = new SQL_Catalog(
                cursor.getInt(cursor.getColumnIndex(SQL_Catalog.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SQL_Catalog.COLUMN_CONTACT)),
                cursor.getString(cursor.getColumnIndex(SQL_Catalog.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(SQL_Catalog.COLUMN_MAIL)),
                cursor.getString(cursor.getColumnIndex(SQL_Catalog.COLUMN_NUMBER)));

        cursor.close();

        return contact;
    }

    public List<SQL_Catalog> getAllContacts() {
        List<SQL_Catalog> сatalogs = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + SQL_Catalog.TABLE_NAME + " ORDER BY " +
                SQL_Catalog.COLUMN_CONTACT+ " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SQL_Catalog contact = new SQL_Catalog();

                contact.setId(cursor.getInt(cursor.getColumnIndex(SQL_Catalog.COLUMN_ID)));
                contact.setContact(cursor.getString(cursor.getColumnIndex(SQL_Catalog.COLUMN_CONTACT)));
                contact.setDescription(cursor.getString(cursor.getColumnIndex(SQL_Catalog.COLUMN_DESCRIPTION)));
                contact.setMail(cursor.getString(cursor.getColumnIndex(SQL_Catalog.COLUMN_MAIL)));
                contact.setNumber(cursor.getString(cursor.getColumnIndex(SQL_Catalog.COLUMN_NUMBER)));


                сatalogs.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();

        return сatalogs;
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + SQL_Catalog.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateContact(SQL_Catalog contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SQL_Catalog.COLUMN_CONTACT, contact.getContact());
        values.put(SQL_Catalog.COLUMN_DESCRIPTION, contact.getDescription());
        values.put(SQL_Catalog.COLUMN_MAIL, contact.getMail());
        values.put(SQL_Catalog.COLUMN_NUMBER, contact.getNumber());

        return db.update(SQL_Catalog.TABLE_NAME, values, SQL_Catalog.COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
    }


    public void deleteContact(SQL_Catalog contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SQL_Catalog.TABLE_NAME, SQL_Catalog.COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }
}

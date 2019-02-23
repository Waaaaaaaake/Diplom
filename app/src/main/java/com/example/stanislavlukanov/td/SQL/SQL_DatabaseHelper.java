package com.example.stanislavlukanov.td.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQL_DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "notes_db";


    public SQL_DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQL_Note.TABLE_NAME);

        onCreate(db);
    }

    public long insertNote(String note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQL_Note.COLUMN_NOTE, note);

        long id = db.insert(SQL_Note.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public SQL_Note getNote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SQL_Note.TABLE_NAME,
                new String[]{SQL_Note.COLUMN_ID, SQL_Note.COLUMN_NOTE, SQL_Note.COLUMN_TIMESTAMP},
                SQL_Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        SQL_Note note = new SQL_Note(
                cursor.getInt(cursor.getColumnIndex(SQL_Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SQL_Note.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(SQL_Note.COLUMN_TIMESTAMP)));

        cursor.close();

        return note;
    }

    // Получаем все заметки

    public List<SQL_Note> getAllNotes() {
        List<SQL_Note> notes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + SQL_Note.TABLE_NAME + " ORDER BY " +
                SQL_Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SQL_Note note = new SQL_Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(SQL_Note.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(SQL_Note.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(SQL_Note.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();

        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + SQL_Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateNote(SQL_Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQL_Note.COLUMN_NOTE, note.getNote());

        return db.update(SQL_Note.TABLE_NAME, values, SQL_Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(SQL_Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SQL_Note.TABLE_NAME, SQL_Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}

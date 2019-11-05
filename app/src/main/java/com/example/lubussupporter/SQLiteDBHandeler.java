package com.example.lubussupporter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHandeler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "colorManager";
    private static final String TABLE_NAME = "color";
    private static final String KEY_COLOR = "Color";
    private static final String KEY_NAME = "Name";

    Context context;

    public SQLiteDBHandeler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COLOR_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_NAME + " TEXT, " +KEY_COLOR+" TEXT ) ";
        db.execSQL(CREATE_COLOR_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);  ///upgrade version number***

    }

    // code to add the new contact
    void addData(String color) {
        //Toast.makeText(context, "Inserted ", Toast.LENGTH_LONG).show();
        deleteData("name");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, "name"); // Contact Name
        values.put(KEY_COLOR, color); // Contact Name


        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // Deleting single contact
    public void deleteData(String name) {
        //Toast.makeText(context, "Deleted ", Toast.LENGTH_LONG).show();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
        db.close();
    }

    public Cursor displayAllData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * From "+ TABLE_NAME, null);

        return cursor;
    }
}

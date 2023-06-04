package com.example.agendaapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ContactDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "contacts.sqlite";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "Contact";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PHONE = "phone";

    public ContactDataBase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table if not exists " + DB_TABLE + "( " +
                COL_ID + " integer primary key autoincrement, " +
                COL_NAME + " text, " +
                COL_PHONE + " text)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long createContactInDB(Contact c){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, c.getName());
        values.put(COL_PHONE, c.getPhone());
        SQLiteDatabase database = getWritableDatabase();
        long id = database.insert(DB_TABLE, null, values);
        database.close();
        return id;
    }

    public long insertContactInDB(Contact c){
        ContentValues values = new ContentValues();
        values.put(COL_ID, c.getId());
        values.put(COL_NAME, c.getName());
        values.put(COL_PHONE, c.getPhone());
        SQLiteDatabase database = getWritableDatabase();
        long id = database.insert(DB_TABLE, null, values);
        database.close();
        return id;
    }

    public ArrayList<Contact> getContactsFromDB(){

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(DB_TABLE, null, null, null, null,
                null, null);
        ArrayList<Contact> contacts = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));

                contacts.add(new Contact(id, name, phone));


            }while (cursor.moveToNext());
        }

        database.close();
        return contacts;
    }

    public int updateContactInDB(Contact c){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, c.getName());
        values.put(COL_PHONE, c.getPhone());
        String id = String.valueOf(c.getId());
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(DB_TABLE, values, COL_ID + "=?", new String[]{id});
        database.close();
        return count;
    }

    public int removeContactInDB(Contact c){
        String id = String.valueOf(c.getId());
        SQLiteDatabase database = getWritableDatabase();
        int count = database.delete(DB_TABLE, COL_ID + "=?", new String[]{id});
        database.close();
        return count;
    }


}

package com.example.chuck.sinav4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myKitaplik";    // Database Name
    private static final String TABLE_NAME = "myTable";   // Table Name
    private static final int DATABASE_Version = 1;    // Database Version
    private static final String UID="id";     // Column I (Primary Key)
    private static final String NAME = "Name";    //Column II
    private static final String YAZAR = "Yazar";    // Column III
    private static final String YIL = "Yıl";
    private static final String FIYAT = "Fiyat";
    private Context context;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                    " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ YAZAR+" VARCHAR(225) ,"+ YIL+ " INTEGER, "+FIYAT+ " INTEGER);";
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {

        }
    }
    public long insertData(String name, String yazar, int yil, int fiyat)
    {
        SQLiteDatabase dbb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(YAZAR, yazar);
        contentValues.put(FIYAT, fiyat);
        contentValues.put(YIL, yil);
        long id = dbb.insert(TABLE_NAME, null , contentValues);
        return id;
    }
    public String getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {UID,NAME, YAZAR, YIL, FIYAT};
        Cursor cursor =db.query(TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(UID));
            String name =cursor.getString(cursor.getColumnIndex(NAME));
            String yazar = cursor.getString(cursor.getColumnIndex(YAZAR));
            String yil = cursor.getString(cursor.getColumnIndex(YIL));
            String fiyat = cursor.getString(cursor.getColumnIndex(FIYAT));
            buffer.append(cid+ "   " + name + " " + yazar + " "+ yil + " " + fiyat + " \n");
        }
        return buffer.toString();
    }
    public boolean delete(String uname, String unFiyat, String unYil, String unYazar)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgsName ={uname};
        String[] whereArgsFiyat ={unFiyat};
        String[] whereArgsYil ={unYil};
        String[] whereArgsYazar ={unYazar};

        db.delete(TABLE_NAME ,NAME+" = ?",whereArgsName);
        db.delete(TABLE_NAME ,FIYAT+" = ?",whereArgsFiyat);
        db.delete(TABLE_NAME ,YAZAR+" = ?",whereArgsYazar);
        db.delete(TABLE_NAME ,YIL+" = ?",whereArgsYil);
        return  true;
    }

    public boolean update(String oldName , String newName, String oldFiyat, int newFiyat, String oldYil, int newYil, String oldYazar, String newYazar)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,newName);
        contentValues.put(FIYAT, newFiyat);
        contentValues.put(YAZAR, newYazar);
        contentValues.put(YIL, newYil);
        String[] whereArgsName = {oldName};
        String[] whereArgsYazar = {oldYazar};
        String[] whereArgsFiyat = {oldFiyat};
        String[] whereArgsYil = {oldYil};
        db.update(TABLE_NAME,contentValues, NAME+" = ?",whereArgsName );
        db.update(TABLE_NAME,contentValues, YAZAR+" = ?",whereArgsYazar );
        db.update(TABLE_NAME,contentValues, FIYAT+" = ?",whereArgsFiyat );
        db.update(TABLE_NAME,contentValues, YIL+" = ?",whereArgsYil );
        return true;
    }
}

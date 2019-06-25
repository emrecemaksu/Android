package com.example.chuck.basitsqliteornek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String db_name = "myDatabase";
    private static final String table_name = "myTable";
    private static final int db_version = 1;
    private static final String UID = "_id";
    private static final String myName = "Name";
    private static final String MyPassword = "Password";
    private Context context;
    public MyDbHelper(Context context) {
        super(context, db_name, null, db_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String Create_Table = "CREATE TABLE " + table_name + " (" + UID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    myName + " VARCHAR(100), " + MyPassword + " VARCHAR(20));";
            db.execSQL(Create_Table);
        }catch (Exception e){
            Log.w("Hata", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            String drop_table = "DROP TABLE IF EXISTS " + table_name;
            db.execSQL(drop_table);
        }catch (Exception e){
            Log.w("TAG", e.getMessage());
        }
    }
    public long insertData(String name, String password){
        SQLiteDatabase liteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myName, name);
        contentValues.put(MyPassword, password);
        long id = liteDatabase.insert(table_name, null, contentValues);
        return id;
    }
    public String getData(){
        StringBuffer buffer = new StringBuffer();
        SQLiteDatabase database = this.getReadableDatabase();
        String[] sutunlar = {UID, myName, MyPassword};
        String whereClause = "Name = ? or Name = ?";
        String[] whereargs = new String[] {"tugba", "zesayi"};
        Cursor cursor = database.query(table_name, sutunlar, whereClause, whereargs, null, null, myName);
        /*
        String sql = "select * from " + table_name;
        Cursor cursor = database.rawQuery(sql, null);
        */
        int degervar = cursor.getCount();
        if(degervar > 0 && cursor != null){
            while (cursor.moveToNext()){
                int cursorint = cursor.getInt(cursor.getColumnIndex(UID));
                String cursorName = cursor.getString(cursor.getColumnIndex(myName));
                String cursorPass = cursor.getString(cursor.getColumnIndex(MyPassword));
                buffer.append(cursorint + " " + cursorName + " " + cursorPass + "\n");
            }
        }
        return buffer.toString();
    }
    public int guncelleme(String eskiIsım, String guncelIsım){
        SQLiteDatabase liteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myName, guncelIsım);
        String[] whereArgs = {eskiIsım};
        int count = liteDatabase.update(table_name, contentValues, myName + " = ?", whereArgs);
        return count;
    }
}
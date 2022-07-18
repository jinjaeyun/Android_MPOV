package com.example.third;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "theater.db";
    public DBHelper(Context context, int version){
        super(context, DATABASE_NAME, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Theater(run TEXT, seat TEXT, value FLOAT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Theater");
        onCreate(db);
    }
    public void insert(String run, String seat, float value){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Theater VALUES('" + run + "'," + "'" + seat + "', " + value + ")");
        db.close();
    }
    public void Update(String run, String seat, float value){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Theater SET value = " + value + " WHERE seat = '" + seat + "'AND" + " run = '" + run +"'");
    }
    public void Delete(String run){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE Theater WHERE run = '" + run + "'");
        db.close();
    }
    public String getResult(String run, String seat){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT * FROM Theater WHERE run = '" + run + "'AND" + " seat = '" + seat + "'" , null);
        while (cursor.moveToNext()){
            result += "좌석 : " + cursor.getString(1)
                    + "                   별점 : " + cursor.getFloat(2)
                    + "\n";
        }
        return result;
    }
}

package com.example.szymon.sklejka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Szymon on 06.07.2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME="aplikacja.db";
    final static String TABLE_PLAYERS="PLAYERS";
    final static String TABLE_PLAYERS_COL1="ID";
    final static String TABLE_PLAYERS_COL2="NAME";
    final static String TABLE_PLAYERS_COL3="PASSWORD";
    final static String TABLE_PLAYERS_COL4="LEVEL";


    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_PLAYERS+" ( ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PASSWORD TEXT,LEVEL INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYERS);
        onCreate(db);
    }

    public boolean insertData(String name,String password,String level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TABLE_PLAYERS_COL2,name);
        contentValues.put(TABLE_PLAYERS_COL3,password);
        contentValues.put(TABLE_PLAYERS_COL4,level);
        long result = db.insert(TABLE_PLAYERS,null,contentValues);
        if(result==-1) return false;
        else return true;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_PLAYERS,(null));
        return res;
    }

    public boolean updateData(String id,String name,String password,String level)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TABLE_PLAYERS_COL1,id);
        contentValues.put(TABLE_PLAYERS_COL2,name);
        contentValues.put(TABLE_PLAYERS_COL3,password);
        contentValues.put(TABLE_PLAYERS_COL4,level);
        System.out.println(db.update(TABLE_PLAYERS, contentValues, TABLE_PLAYERS_COL1+"= ?",new String[] {id }));
        return true;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PLAYERS,"ID=?",new String[] {id});
    }
}

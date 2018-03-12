package com.example.venkat.newsapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by venkat on 13/3/18.
 */

public class DBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favouriteManager";
    private static final String TABLE_NEWS = "news";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    public DBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FAVOURITE_TABLE = "CREATE TABLE " + TABLE_NEWS + "(" + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " Text" + ")";
        db.execSQL(CREATE_FAVOURITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
            onCreate(db);
    }

    public void addFavourite(Favourite favourite){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,favourite.get);
    }

    public Favourite getFavourite(int id){


    }

    public List<Favourite> getAllFavourites(){

    }

    public int getFavouriteCount(){

    }

    public int updateFavourite(Favourite favourite){

    }

    public void deleteFavourite(Favourite favourite){

    }
}

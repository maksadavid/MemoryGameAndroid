package com.mks.memorygame.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mks.memorygame.model.Score;

/**
 * Created by maksadavid on 2017. 02. 13..
 */
public class DatabaseManager {

    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_SCORE = "score";

    public DatabaseManager(Context context) {
        db = context.openOrCreateDatabase("MemoryGame", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS scores (id INTEGER NOT NULL, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_SCORE + " INTEGER NOT NULL, PRIMARY KEY('id'));");
    }

    private SQLiteDatabase db;

    public void addEntry(Score score) {
        db.execSQL("INSERT INTO scores (name, score) VALUES ('" + score.getName() +"', " + score.getScore() + ");");
    }

    public Cursor getAllEntries() {
        return db.rawQuery("SELECT id _id, * FROM scores ORDER BY score DESC, id ASC", null);
    }

}

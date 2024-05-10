package com.example.noteappv4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteDB extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "todo";
    private static final String TABLE_NAME = "todo";

    // Column names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    public NoteDB(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" " +
                "("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE + " TEXT,"
                +DESCRIPTION + " TEXT,"
                +STARTED+ " TEXT,"
                +FINISHED+" TEXT" +
                ");";

        /*
            CREATE TABLE todo (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT,
            started TEXT,finished TEXT); */

        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        // Drop older table if existed
        db.execSQL(DROP_TABLE_QUERY);
        // Create tables again
        onCreate(db);
    }

    /*
            +-------+-------+-------+-------+
            | Col 1 | col 2 | Col 3 | Col 4 |
            +-------+-------+-------+-------+
            |   1   |   2   |  red  |  dog  |
            +-------+-------+-------+-------+
            |   2   |   4   |  blue |  cat  |
            +-------+-------+-------+-------+
            |   3   |   9   |  red  | bird  |
            +-------+-------+-------+-------+
     */

    // Add a single todo
    public void addToDo(NoteObj noteObj){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, noteObj.getTitle());
        contentValues.put(DESCRIPTION, noteObj.getDescription());
        contentValues.put(STARTED, noteObj.getStarted());
        contentValues.put(FINISHED, noteObj.getFinished());

        //save to table
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        // close database
        sqLiteDatabase.close();
    }

    // Count todo table records
    public int countToDo(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    // Get all todos into a list
    public List<NoteObj> getAllToDos(){

        List<NoteObj> noteObjs = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {
                // Create new ToDo object
                NoteObj noteObj = new NoteObj();
                // mmgby6hh
                noteObj.setId(cursor.getInt(0));
                noteObj.setTitle(cursor.getString(1));
                noteObj.setDescription(cursor.getString(2));
                noteObj.setStarted(cursor.getLong(3));
                noteObj.setFinished(cursor.getLong(4));

                //toDos [obj,objs,asas,asa]
                noteObjs.add(noteObj);
            }while (cursor.moveToNext());
        }
        return noteObjs;
    }

    // Delete item
    public void deleteToDo(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,"id =?",new String[]{String.valueOf(id)});
        db.close();
    }

    // Get a single todo
    public NoteObj getSingleToDo(int id){
        SQLiteDatabase db = getWritableDatabase();

       Cursor cursor = db.query(TABLE_NAME,new String[]{ID,TITLE,DESCRIPTION,STARTED, FINISHED},
                ID + "= ?",new String[]{String.valueOf(id)}
                ,null,null,null);

       NoteObj noteObj;
       if(cursor != null){
            cursor.moveToFirst();
         noteObj = new NoteObj(
                 cursor.getInt(0),
                 cursor.getString(1),
                 cursor.getString(2),
                 cursor.getLong(3),
                 cursor.getLong(4)
         );
         return noteObj;
       }
       return null;
    }

    // Update a single todo
    public int updateSingleToDo(NoteObj noteObj){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, noteObj.getTitle());
        contentValues.put(DESCRIPTION, noteObj.getDescription());
        contentValues.put(STARTED, noteObj.getStarted());
        contentValues.put(FINISHED, noteObj.getFinished());

        int status = db.update(TABLE_NAME,contentValues,ID +" =?",
                new String[]{String.valueOf(noteObj.getId())});

        db.close();
        return status;
    }
}

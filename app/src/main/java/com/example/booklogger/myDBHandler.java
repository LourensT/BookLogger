package com.example.sqltut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.booklogger.Book;

import java.text.SimpleDateFormat;
import java.util.Date;

public class myDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "books.db";

    public static final String TABLE_BOOKS = "BOOKS";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_title = "_title";
    public static final String COLUMN_author = "_author";
    public static final String COLUMN_pages = "_pages";
    public static final String COLUMN_days = "_days";
    public static final String COLUMN_currentPage = "_currentPage";

    public static final String TABLE_PROGRESS = "PROGRESS";
    public static final String COLUMN_bookid = "_bookid";
    public static final String COLUMN_date = "_date";
    public static final String COLUMN_page = "_page";

    public myDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS +";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS +";");

        String query_books = "CREATE TABLE " + TABLE_BOOKS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_title + " TEXT, " +
                COLUMN_author + " TEXT, " +
                COLUMN_pages + " REAL NOT NULL, " +
                COLUMN_days + " REAL NOT NULL, " +
                COLUMN_currentPage + " REAL NOT NULL " +
                " );";

        db.execSQL(query_books);

        String query_progress = "CREATE TABLE " + TABLE_PROGRESS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_bookid + " INT, " +
                COLUMN_date + " STRING, " +
                COLUMN_page + " INTEGER NOT NULL);";

        db.execSQL(query_progress);

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        Log.i("DB", "yo");
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                Log.i("DB","Table Name=> "+c.getString(0));
                c.moveToNext();
            }
        }

    }

    //drop previous database and initialize new on
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    public void appendBook(Book book){
        ContentValues bookValues = new ContentValues();
        bookValues.put(COLUMN_title, book.getTitle());
        bookValues.put(COLUMN_author, book.getAuthor());
        bookValues.put(COLUMN_pages, book.getPages());
        bookValues.put(COLUMN_days, book.getDays());
        bookValues.put(COLUMN_currentPage, book.getCurrentPage());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_BOOKS, null, bookValues);
        db.close();
    }

    public Book getBook(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKS + " WHERE (" + COLUMN_ID + "=" + String.valueOf(id) + ");";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        Book book = new Book();
        book.setTitle(c.getString(c.getColumnIndex(COLUMN_title)));
        book.setAuthor(c.getString(c.getColumnIndex(COLUMN_author)));
        book.setPages(c.getInt(c.getColumnIndex(COLUMN_pages)));
        book.setDays(c.getInt(c.getColumnIndex(COLUMN_days)));
        book.setCurrentPage(c.getInt(c.getColumnIndex(COLUMN_currentPage)));

        c.close();
        db.close();
        return book;
    }

    public Cursor getFullCursor(){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_BOOKS;
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public Cursor getProgressCursor(int id){
        SQLiteDatabase db = getWritableDatabase();
        Log.i("DB", "yo");

        String query = "SELECT * FROM " + TABLE_PROGRESS + " WHERE " +  COLUMN_bookid + "=" + String.valueOf(id) + ";";
        //String query = "SELECT * FROM " + TABLE_PROGRESS + ";";
        Cursor c = db.rawQuery(query, null);
        Log.i("DB", "getProgressCursor has been called");
        return c;
    }

    public void addProgress(int id, Date date, int page){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = sdf.format(date);

        ContentValues progress = new ContentValues();
        progress.put(COLUMN_bookid, id);
        progress.put(COLUMN_page, stringDate);

        db.close();
    }

    public void addProgressString(int id, String date, int page){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues progress = new ContentValues();
        progress.put(COLUMN_bookid, id);
        progress.put(COLUMN_date, date);
        progress.put(COLUMN_page, page);

        Log.i("DB", "addProgressString has been called");
        db.insert(TABLE_PROGRESS, null, progress);
        db.close();

        setCurrentPage(id, page);
    }

    public void setCurrentPage(int bookId, int page){
        ContentValues newEntry = new ContentValues();
        newEntry.put(COLUMN_currentPage, page);

        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_BOOKS, newEntry, COLUMN_ID + "=" + String.valueOf(bookId), null);
        db.close();

    }


}


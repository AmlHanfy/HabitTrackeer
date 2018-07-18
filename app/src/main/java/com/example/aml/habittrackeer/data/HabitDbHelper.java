package com.example.aml.habittrackeer.data;
import android.content.Context;
import com.example.aml.habittrackeer.data.HabitContract.HabitEntry;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class HabitDbHelper extends SQLiteOpenHelper
{
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "habit_tracker.db";
    private final static String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;
    private final static String SQL_CREATE_ENTRIES = "CREATE TABLE " + HabitEntry.TABLE_NAME + "("
            + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL,"
            + HabitEntry.COLUMN_HABIT_TIME + " INTEGER NOT NULL DEFAULT 0 );";
    public HabitDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
}

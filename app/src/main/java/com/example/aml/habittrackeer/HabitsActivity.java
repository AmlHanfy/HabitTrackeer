package com.example.aml.habittrackeer;
import android.widget.TextView;
import com.example.aml.habittrackeer.data.HabitContract.HabitEntry;
import com.example.aml.habittrackeer.data.HabitDbHelper;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.view.MenuItem;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
public class HabitsActivity extends AppCompatActivity
{
    HabitDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.f);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(HabitsActivity.this, AddHabitActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new HabitDbHelper(this);
        displayDatabaseInfo();
    }
    private void insertHabit()
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitEntry.COLUMN_HABIT_NAME, "Football");
        contentValues.put(HabitEntry.COLUMN_HABIT_TIME, "800");
        db.insert(HabitEntry.TABLE_NAME, null, contentValues);
    }
    private Cursor displayDatabaseInfo()
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_TIME,
        };
        Cursor cursor = db.query(HabitEntry.TABLE_NAME,projection,null,null,null,null,null);
        return cursor;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_habits, menu);
        return true;
    }
    private void readFromCursor(Cursor cursor)
    {
        TextView displayView = (TextView) findViewById(R.id.text_view_habit);
        try
        {
            displayView.setText("The Habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitEntry.COLUMN_HABIT_TIME + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TIME);
            while (cursor.moveToNext())
            {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentTime = cursor.getString(timeColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentTime));
            }
        }
        finally
        {
            cursor.close();
        }
    }
    @Override
    protected void onStart()
    {
        Cursor cursor = displayDatabaseInfo();
        readFromCursor(cursor);
        super.onStart();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_insert_dummy_data)
        {
            insertHabit();
            Cursor cursor = displayDatabaseInfo();
            readFromCursor(cursor);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.aml.habittrackeer;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.example.aml.habittrackeer.data.HabitContract.HabitEntry;
import com.example.aml.habittrackeer.data.HabitDbHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
public class AddHabitActivity extends AppCompatActivity
{
    HabitDbHelper dbHelper;
    private EditText mNameEditText;
    private EditText mMinutesEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        mNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mMinutesEditText = (EditText) findViewById(R.id.edit_habit_time);
        dbHelper = new HabitDbHelper(this);
    }
    private void insertHabit()
    {
        if (!TextUtils.isEmpty(mMinutesEditText.getText()) && !TextUtils.isEmpty(mNameEditText.getText()))
        {
            String name = mNameEditText.getText().toString().trim();
            int time = Integer.parseInt(mMinutesEditText.getText().toString().trim());
            ContentValues values = new ContentValues();
            values.put(HabitEntry.COLUMN_HABIT_NAME, name);
            values.put(HabitEntry.COLUMN_HABIT_TIME, time);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long rowId = db.insert(HabitEntry.TABLE_NAME, null, values);
            if (rowId == -1)
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "New Row Id " + rowId, Toast.LENGTH_LONG).show();
            finish();
        }
        else
            Toast.makeText(this,"Complete all fields",Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_save)
        {
            insertHabit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_habit_add, menu);
        return true;
    }
}

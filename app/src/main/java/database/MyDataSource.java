package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by carolina on 07/07/17.
 */

public class MyDataSource {
    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String PEOPLE_TABLE = "people"; // name of table
    public final static String PERSON_ID = "personId"; // id value for employee
    public final static String PERSON_NAME = "personName";  // name of employee

    public MyDataSource(Context context) {
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long createRecord(int id, String name) {
        ContentValues values = new ContentValues();
        values.put(PERSON_ID, id);
        values.put(PERSON_NAME, name);
        return database.insert(PEOPLE_TABLE, null, values);
    }

    public Cursor selectRecord(int personId) {
        String[] cols = new String[]{PERSON_ID, PERSON_NAME};
        return database.query(true, PEOPLE_TABLE, cols,
                MyDataSource.PERSON_ID + "=" + personId,
                null, null, null, null, null);
    }

    public void initData() {
        long count = DatabaseUtils.queryNumEntries(database, PEOPLE_TABLE);
        if (count == 0) {
            Log.i("MyDataSource", "Initializing data");
            for (int i = 1; i <= 100; i++) {
                createRecord(i, "Person " + i);
            }
        } else {
            Log.i("MyDataSource", "Data already initialized with " + count + " rows");
        }
    }

}
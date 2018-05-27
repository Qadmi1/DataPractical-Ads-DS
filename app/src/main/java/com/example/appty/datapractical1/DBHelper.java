package com.example.appty.datapractical1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by appty on 21/04/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    static final String TABLE_NAME = "WorkoutTable";
    static final String KEY_ROWID = "_id";
    static final String KEY_TITLE = "Title";
    static final String KEY_REPS = "Reps";
    static final String KEY_SETS = "Sets";
    static final String KEY_DESC = "Description";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "WorkoutDatabase";

    private static SQLiteDatabase DB;

    private String TAG = "DBHelper";

    private static String CREATE_TABLE = "CREATE TABLE  " + TABLE_NAME + " (" + KEY_ROWID +
            " integer PRIMARY KEY, " + KEY_TITLE + " ," + KEY_REPS + " ," + KEY_SETS +
            " ," + KEY_DESC + " );";


    DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d(TAG, "Table Created Successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void insertValues(String title, String reps, String sets, String desc){
        //try creating an instant of this class instead of using the "this"
        DB = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, title);
        values.put(KEY_REPS, reps);
        values.put(KEY_SETS, sets);
        values.put(KEY_DESC, desc);

        DB.insert(TABLE_NAME, null, values);

        Log.d(TAG, "Values inserted");

    }

    public  void deleteValues(int passedID){

        DB = this.getWritableDatabase();
        String rowID = Integer.toString(passedID);

        DB.delete(TABLE_NAME, KEY_ROWID+ " = "+ rowID, null);


    }
}

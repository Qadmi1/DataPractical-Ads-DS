package com.example.appty.datapractical1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.appty.datapractical1.DBHelper.KEY_ROWID;
import static com.example.appty.datapractical1.DBHelper.TABLE_NAME;


/**
 * Created by appty on 22/04/18.
 */

public class Workouts extends AppCompatActivity {
    private static final String TAG = "Workouts";
    public static final String EXTRA_ID = "id";

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts);

        final int workoutID = (Integer) getIntent().getExtras().get(EXTRA_ID);
        final DBHelper helper = new DBHelper(this);

        try {
            db = helper.getReadableDatabase();
            String[] projection = {DBHelper.KEY_ROWID, DBHelper.KEY_TITLE, DBHelper.KEY_REPS, DBHelper.KEY_SETS,
            DBHelper.KEY_DESC};
            String whereClause = DBHelper.KEY_ROWID + " =?";
            String[] whereArgs = {Integer.toString(workoutID)};

            cursor = db.query( DBHelper.TABLE_NAME, projection, whereClause, whereArgs,null, null, null);

            cursor.moveToFirst();

            String t = cursor.getString(1);
            String r = cursor.getString(2);
            String s = cursor.getString(3);
            String d = cursor.getString(4);

            TextView txt1 = findViewById(R.id.title);
            TextView txt2 = findViewById(R.id.reps);
            TextView txt3 = findViewById(R.id.sets);
            TextView txt4 = findViewById(R.id.desc);

            txt1.setText(t);
            txt2.setText(r);
            txt3.setText(s);
            txt4.setText(d);

            cursor.close();
        }
        catch (SQLiteException e){
            e.printStackTrace();
        }

        Button b1 = findViewById(R.id.delete);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rowID = Integer.toString(workoutID);
                String whereClause = KEY_ROWID + "=?";
                String[] whereArgs = {rowID};

                db.delete(TABLE_NAME, whereClause, whereArgs);
                Toast.makeText(getApplicationContext(), "Workout Deleted", Toast.LENGTH_LONG).show();
                Log.d(TAG, "delete successful");
                finish();

            }
        });
    }
}

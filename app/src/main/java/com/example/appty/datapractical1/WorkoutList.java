package com.example.appty.datapractical1;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by appty on 22/04/18.
 */

public class WorkoutList extends ListActivity {

    private static final String TAG = "WorkoutList";
    private SimpleCursorAdapter cursorAdapter;
    private ListView listView;
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = getListView();
//        int layout = android.R.layout.simple_expandable_list_item_1;

        String[] displayCol = {DBHelper.KEY_TITLE};
        int[] to = {android.R.id.text1};

        cursor = null;
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_1, cursor, displayCol, to, 0);
        listView.setAdapter(cursorAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        cursor = updateCursor();
        cursorAdapter.changeCursor(cursor);
    }

    private Cursor updateCursor() {
        cursor = null;

        try {
            DBHelper helper = new DBHelper(this);
            String[] projection = {DBHelper.KEY_ROWID, DBHelper.KEY_TITLE};
            db = helper.getWritableDatabase();
            cursor = db.query(DBHelper.TABLE_NAME, projection, null, null,
                    null, null, null);
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        return cursor;
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, Workouts.class);
        intent.putExtra(Workouts.EXTRA_ID, (int) id);

        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cursor != null) {
            cursor.close();
        }
        if (cursor != null) {
            db.close();
        }
    }
}

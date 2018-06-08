package com.example.appty.datapractical1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    Cursor cursor;
    SQLiteDatabase db;
    ListView listView;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this);
        adView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

        listView= findViewById(R.id.mainList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (position == 0) {
                    if (cursor.moveToFirst()) {
                        Intent intent = new Intent(getApplicationContext(), WorkoutList.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Add a Workout", Toast.LENGTH_SHORT).show();
                    }
                } else if (position == 1) {
                    Intent intent = new Intent(getApplicationContext(), AddWorkouts.class);
                    startActivity(intent);
                }
                else if (position == 2){

                    if (checkNetworkConnection(getApplicationContext()))
                    {
                        Intent intent = new Intent(getApplicationContext(), NetworkList.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please connect to a wifi or mobile network", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean checkNetworkConnection(Context context){

        int[] networkTypes = {ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE};

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();


            for (int i : networkTypes){
                if (activeNetworkInfo != null && activeNetworkInfo.getType() == i)
                {
                    Log.d("NetworkInfo", activeNetworkInfo.toString());
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            return false;
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper helper = new DBHelper(this);

        db = helper.getReadableDatabase();

        String[] prpjection = {DBHelper.KEY_ROWID};

        cursor = db.query(DBHelper.TABLE_NAME, prpjection, null, null, null, null, null);


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

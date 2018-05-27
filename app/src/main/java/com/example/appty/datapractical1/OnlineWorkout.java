package com.example.appty.datapractical1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by appty on 05/05/18.
 */

public class OnlineWorkout extends AppCompatActivity {

    public final static String EXTRA_ID = "onlineid";
    private final static String TAG = "WorkoutList.java";

    String title, sets, reps, desc;

    InterstitialAd inter;
    AlertDialog.Builder dialog;
    AlertDialog display;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.online_workouts);
        Workout workout = getIntent().getExtras().getParcelable(EXTRA_ID);

        inter = new InterstitialAd(this);
        inter.setAdUnitId("ca-app-pub-3396045645520018/1983071915");

        AdRequest adRequest = new AdRequest.Builder().build();

        inter.loadAd(adRequest);

        inter.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                AdRequest adRequest1 = new AdRequest.Builder().build();

                inter.loadAd(adRequest1);

                finish();

                Toast.makeText(OnlineWorkout.this, "Workout Saved", Toast.LENGTH_SHORT).show();
            }
        });


        Button add = findViewById(R.id.add);

        TextView txt1 = findViewById(R.id.otitle);
        TextView txt2 = findViewById(R.id.oreps);
        TextView txt3 = findViewById(R.id.osets);
        TextView txt4 = findViewById(R.id.odesc);

        title = workout.getTitle();
        sets = workout.getSets();
        reps = workout.getReps();
        desc = workout.getDesc();

        txt1.setText(title);
        txt2.setText(sets);
        txt3.setText(reps);
        txt4.setText(desc);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper helper = new DBHelper(getApplicationContext());
                helper.insertValues(title, reps, sets, desc);

                dialog = new AlertDialog.Builder(OnlineWorkout.this).setCancelable(true)
                        .setMessage("View Add to help fund us and get Ads you are downloading for free");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inter.show();
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                        finish();
                        Toast.makeText(OnlineWorkout.this, "Workout Saved", Toast.LENGTH_SHORT).show();

                    }
                });

                display = dialog.create();
                display.show();

            }
        });

    }
}

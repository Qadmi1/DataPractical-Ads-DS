package com.example.appty.datapractical1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by appty on 22/04/18.
 */

public class AddWorkouts extends AppCompatActivity {

    String title, reps, sets, desc;
    EditText t, r, s, d;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workouts);

        t = findViewById(R.id.inputTitle);
        r = findViewById(R.id.inputReps);
        s = findViewById(R.id.inputSets);
        d = findViewById(R.id.inputDesc);


        Button b = findViewById(R.id.add_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertToDB();
            }
        });
    }

    private void insertToDB() {

        title = t.getText().toString();
        reps = r.getText().toString();
        sets = s.getText().toString();
        desc = d.getText().toString();


        DBHelper helper = new DBHelper(this);

        helper.insertValues(title, reps, sets, desc);

        this.finish();
    }

}

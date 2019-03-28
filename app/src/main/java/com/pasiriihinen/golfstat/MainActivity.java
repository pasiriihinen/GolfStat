package com.pasiriihinen.golfstat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    //Create variables for the buttons
    private Button addNewRoundButton;
    private Button viewStatsButton;
    private Button addNewCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        //Initialize the buttons
        addNewRoundButton = (Button) findViewById(R.id.btnAddNewRound);
        viewStatsButton = (Button) findViewById(R.id.btnViewStats);
        addNewCourseButton = (Button) findViewById(R.id.btnAddNewCourse);

        //Set onClickListener for the buttons
        addNewRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openSelectCourseActivity();
            }
        });

        viewStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewStatsActivity();
            }
        });

        addNewCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNewRoundActivity();
            }
        });
    }

    //Methods for opening other activities
    public void openSelectCourseActivity() {
        Intent intent = new Intent(this, SelectCourseActivity.class);
        startActivity(intent);
    }
    public void openViewStatsActivity() {
        Intent intent = new Intent(this, ViewStatsActivity.class);
        startActivity(intent);
    }
    public void openAddNewRoundActivity() {
        Intent intent = new Intent(this, AddNewCourseActivity.class);
        startActivity(intent);
    }
}

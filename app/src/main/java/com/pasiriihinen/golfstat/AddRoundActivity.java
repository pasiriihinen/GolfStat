package com.pasiriihinen.golfstat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddRoundActivity extends AppCompatActivity {
    //Add variables
    DatabaseHelper myDb;
    TextView TextViewSelectedCourse, TextViewSelectedCourseIdDigit, TextViewCurrentHole,
            TextViewCurrentHoleDigit, TextViewCurrentHolePar, TextViewCurrentHoleParDigit;
    Button button_Next;
    Integer courseIdDigit, holeNumberDigit, holeParDigit;
    char[] holeParArray;
    String tempString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_round);

        //DatabaseHelper
        myDb = new DatabaseHelper(this);

        //
        TextViewSelectedCourse = findViewById(R.id.TextView_selectedCourse);
        TextViewSelectedCourseIdDigit = findViewById(R.id.textView_selectedCourseIdDigit);

        //Receive and assign intent bundle content from selectCourseActivity for initial setup
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            TextViewSelectedCourse.setText(bundle.getString("CourseName"));
            TextViewSelectedCourseIdDigit.setText(bundle.getString("CourseId"));
            tempString = (bundle.getString("Par"));
        }

        //Setup textViews
        holeParArray = tempString.toCharArray();

        courseIdDigit = Integer.valueOf(TextViewSelectedCourseIdDigit.getText().toString());
        TextViewCurrentHole = findViewById(R.id.textView_AddRoundHoleNumber);
        TextViewCurrentHoleDigit = findViewById(R.id.textView_AddRoundHoleNumberDigit);
        holeNumberDigit = 1;
        TextViewCurrentHoleDigit.setText(holeNumberDigit.toString());
        TextViewCurrentHolePar = findViewById(R.id.textView_AddRoundHolePar);
        TextViewCurrentHoleParDigit = findViewById(R.id.textView_AddRoundHoleParDigit);
        holeParDigit = Integer.valueOf(holeParArray[0]);
        TextViewCurrentHoleParDigit.setText(holeParDigit);

        //Button setup
        button_Next = findViewById(R.id.button_NextHole);


        PostHoleData();

    }


    //Update GUI after clicking next button


    //Method to post hole data to database
    public void PostHoleData() {
        button_Next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                   /*     boolean postedHoleData = myDb.postHoleData(3);
                    if (postedHoleData == true) {
                        Toast.makeText(AddRoundActivity.this, "Data posted To DB", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AddRoundActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    } */
                    }
                }
        );
    }
}

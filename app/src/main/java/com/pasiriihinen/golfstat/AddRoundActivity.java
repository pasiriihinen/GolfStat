package com.pasiriihinen.golfstat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddRoundActivity extends AppCompatActivity {
    //Add variables
    DatabaseHelper myDb;
    TextView TextViewSelectCourse;
    TextView TextViewHoleNumber;
    TextView TextViewHolePar;
    Button button_NextHole;
    String str_CourseId, str_Par;
    Character char_CurrentHolePar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_round);

        //DatabaseHelper
        myDb = new DatabaseHelper(this);

        //Assign the variables via findViewById
        button_NextHole = (Button)findViewById(R.id.button_NextHole);
        //Set HoleNumber
        TextViewHoleNumber = (TextView) findViewById(R.id.textView_holeNumber);


        TextViewSelectCourse = (TextView) findViewById(R.id.selectedCourseTextView);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            TextViewSelectCourse.setText(bundle.getString("CourseName") + "(" + bundle.getString("CourseId") + ")");
        }

        str_CourseId = (bundle.getString("CourseId"));
        str_Par = (bundle.getString("PAR"));
        char_CurrentHolePar = str_Par.charAt(0);

        TextViewHolePar.setText(char_CurrentHolePar);



        PostHoleData();

    }

    //Method to post hole data to database
    public void PostHoleData() {
        button_NextHole.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean postedHoleData = myDb.postHoleData(3);
                    if (postedHoleData == true) {
                        Toast.makeText(AddRoundActivity.this, "Data posted To DB", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AddRoundActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    }
                }
        );
    }
}

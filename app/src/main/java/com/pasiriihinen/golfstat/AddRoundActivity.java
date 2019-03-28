package com.pasiriihinen.golfstat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AddRoundActivity extends AppCompatActivity {
    //Add variables
    TextView TextViewSelectCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_round);


        TextViewSelectCourse = (TextView) findViewById(R.id.selectedCourseTextView);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            TextViewSelectCourse.setText(bundle.getString("CourseName") + "(" + bundle.getString("CourseId") + ")");
        }

    }
}

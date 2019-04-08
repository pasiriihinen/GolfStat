package com.pasiriihinen.golfstat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import static com.pasiriihinen.golfstat.DatabaseHelper.ROUNDS_TABLE;
import static com.pasiriihinen.golfstat.DatabaseHelper.ROUND_ID;

public class AddRoundActivity extends AppCompatActivity {
    //Add variables
    DatabaseHelper myDb;
    TextView TextViewSelectedCourse, TextViewSelectedCourseIdDigit, TextViewCurrentHole,
            TextViewCurrentHoleDigit, TextViewCurrentHolePar, TextViewCurrentHoleParDigit;
    Button button_Next;
    Integer roundId, holeNumberDigit, holeParDigit, holePuttsDigit, currentHoleParHelper;
    //char[] holeParArray;
    String roundIdString, tempString, dateString, courseIdDigit, holeScore;
    RadioGroup radioGroupHoleScore, radioGroupHolePutts;
    RadioButton radioButtonHoleScore, radioButtonHolePutts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_round);

        //DatabaseHelper
        myDb = new DatabaseHelper(this);

        //Get the current date
        Calendar calendar = Calendar.getInstance();
        dateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());

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
        courseIdDigit = TextViewSelectedCourseIdDigit.getText().toString();
        TextViewCurrentHole = findViewById(R.id.textView_AddRoundHoleNumber);
        TextViewCurrentHoleDigit = findViewById(R.id.textView_AddRoundHoleNumberDigit);
        holeNumberDigit = 1;
        //TextViewCurrentHoleDigit.setText(holeNumberDigit.toString());
        TextViewCurrentHoleDigit.setText(String.valueOf(holeNumberDigit));
        TextViewCurrentHolePar = findViewById(R.id.textView_AddRoundHolePar);
        TextViewCurrentHoleParDigit = findViewById(R.id.textView_AddRoundHoleParDigit);
    //    holeParDigit = Integer.valueOf(holeParArray[0]);
    //    TextViewCurrentHoleParDigit.setText(holeParDigit);
        currentHoleParHelper = 0;
        TextViewCurrentHoleParDigit.setText(String.valueOf(tempString.charAt(currentHoleParHelper)));


        //Setup radiogroups
        radioGroupHoleScore = findViewById(R.id.radioGroupScore);
        radioGroupHolePutts = findViewById(R.id.radioGroupPutts);

        //Button setup
        button_Next = findViewById(R.id.button_NextHole);

        //Get last round id
        roundId = 0;
        getLastRoundId();

        PostHoleData();

    }

    private void getLastRoundId() {
        //roundId = Integer.valueOf("SELECT " + ROUND_ID + " FROM " + ROUNDS_TABLE + " ORDER BY _id DESC LIMIT 1");
       // roundId = Integer.valueOf("SELECT MAX(id) FROM ROUNDS_TABLE");
        myDb.execSQL("SELECT MAX(id) FROM ROUNDS_TABLE");
        //roundId += 1;
        roundIdString = String.valueOf(roundId);
    }


    //Update GUI after clicking next button

    //Method to post hole data to database
    public void PostHoleData() {
        button_Next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //boolean postedHoleData = myDb.postHoleData(3);
                        boolean postedHoleData = myDb.postHoleData(roundIdString, dateString, courseIdDigit, String.valueOf(holeNumberDigit), String.valueOf(tempString.charAt(currentHoleParHelper)), String.valueOf(holeParDigit));

                    if (postedHoleData == true) {
                        Toast.makeText(AddRoundActivity.this, "Data posted To DB", Toast.LENGTH_SHORT).show();
                    }
                        else {
                            Toast.makeText(AddRoundActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

    //Method to store hole score from radio group to variable
    public void checkHoleScore(View v) {
        int radioScoreId = radioGroupHoleScore.getCheckedRadioButtonId();

        radioButtonHoleScore = findViewById(radioScoreId);
        String tempScore = String.valueOf(radioButtonHoleScore.getText());
        holeParDigit = Integer.valueOf(tempScore);
        Toast.makeText(this, "Hole score is: " + radioButtonHoleScore.getText(),
                Toast.LENGTH_SHORT).show();
    }

    //Method to store hole putts from radio group to variable
    public void checkHolePutts(View v) {
        int radioPuttsId = radioGroupHolePutts.getCheckedRadioButtonId();

        radioButtonHolePutts = findViewById(radioPuttsId);
        String tempPutts = String.valueOf(radioButtonHolePutts.getText());
        holePuttsDigit = Integer.valueOf(tempPutts);
        Toast.makeText(this, "Number of putts is: " + radioButtonHolePutts.getText(),
                Toast.LENGTH_SHORT).show();
    }
}

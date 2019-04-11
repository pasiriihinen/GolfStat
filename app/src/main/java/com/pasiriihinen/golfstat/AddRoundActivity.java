package com.pasiriihinen.golfstat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    Integer roundId, holeNumberInteger, holePuttsDigit, currentHoleParHelper;
    //char[] holeParArray;
    String roundIdString, tempString, dateString, holeNumberString, courseIdString, holeScoreString, holeParString, currentHolePar, holePuttsString, holeFWStatusString, holeChipString, holePenaltyString;
    RadioGroup radioGroupHoleScore, radioGroupHolePutts, radioGroupFW;
    RadioButton radioButtonHoleScore, radioButtonHolePutts, radioButtonFW;
    ToggleButton toggleButtonChip, toggleButtonPenalty;


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
        courseIdString = TextViewSelectedCourseIdDigit.getText().toString();
        TextViewCurrentHole = findViewById(R.id.textView_AddRoundHoleNumber);
        TextViewCurrentHoleDigit = findViewById(R.id.textView_AddRoundHoleNumberDigit);
        holeNumberInteger = 1;
        holeNumberString = String.valueOf(holeNumberInteger);
        //TextViewCurrentHoleDigit.setText(holeNumberDigit.toString());
        TextViewCurrentHoleDigit.setText(String.valueOf(holeNumberInteger));
        TextViewCurrentHolePar = findViewById(R.id.textView_AddRoundHolePar);
        TextViewCurrentHoleParDigit = findViewById(R.id.textView_AddRoundHoleParDigit);
    //    holeParDigit = Integer.valueOf(holeParArray[0]);
    //  The line below needs br to an INTEGER eventually
        currentHoleParHelper = 0;
        TextViewCurrentHoleParDigit.setText(String.valueOf(tempString.charAt(currentHoleParHelper)));
        currentHolePar = TextViewCurrentHoleParDigit.getText().toString();

        //Setup radiogroups
        radioGroupHoleScore = findViewById(R.id.radioGroupScore);
        radioGroupHolePutts = findViewById(R.id.radioGroupPutts);
        radioGroupFW = findViewById(R.id.radioGroupFW);

        //Setup toggle buttons
        toggleButtonChip = findViewById(R.id.toggleButton_Chip);
        holeChipString = "No";
        this.toggleButtonChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    holeChipString = "Yes";
                    Toast.makeText(AddRoundActivity.this, "Chip = Yes", Toast.LENGTH_SHORT).show();
                } else {
                    holeChipString = "No";
                    Toast.makeText(AddRoundActivity.this, "Chip = No", Toast.LENGTH_SHORT).show();
                }
            }
        });
        toggleButtonPenalty = findViewById(R.id.toggleButton_Penalty);
        holePenaltyString = "No";
        this.toggleButtonPenalty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    holePenaltyString = "Yes";
                    Toast.makeText(AddRoundActivity.this, "Penalty = Yes", Toast.LENGTH_SHORT).show();
                } else {
                    holePenaltyString = "No";
                    Toast.makeText(AddRoundActivity.this, "Penalty = No", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button setup
        button_Next = findViewById(R.id.button_NextHole);


        //Get last round id
        roundId = 2;
        roundIdString = String.valueOf(roundId);
        holeParString = "olle";

        PostHoleData();

    }


    //Update GUI after clicking next button

    //Method to post hole data to database
    public void PostHoleData() {
        button_Next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean postedHoleData = myDb.postHoleData(dateString, courseIdString, holeNumberString, currentHolePar, holeScoreString, holePuttsString, holeFWStatusString, holeChipString, holePenaltyString);
                        //boolean postedHoleData = myDb.postHoleData(roundIdString, dateString, courseIdString, holeNumberString, String.valueOf(tempString.charAt(currentHoleParHelper)), holeScoreString);
                        //boolean postedHoleData = myDb.postHoleData(roundIdString, dateString, courseIdString, holeNumberString, holeParString, holeScoreString);
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
        //String tempScore = String.valueOf(radioButtonHoleScore.getText());
        //holeScoreString = tempScore;
        holeScoreString = String.valueOf(radioButtonHoleScore.getText());
        Toast.makeText(this, "Hole score is: " + radioButtonHoleScore.getText(),
                Toast.LENGTH_SHORT).show();
    }

    //Method to store hole putts from radio group to variable
    public void checkHolePutts(View v) {
        int radioPuttsId = radioGroupHolePutts.getCheckedRadioButtonId();

        radioButtonHolePutts = findViewById(radioPuttsId);
        holePuttsString = String.valueOf(radioButtonHolePutts.getText());
        Toast.makeText(this, "Number of putts is: " + radioButtonHolePutts.getText(),
                Toast.LENGTH_SHORT).show();
    }

    //Method to store hole putts from radio group to variable
    public void checkFW(View v) {
        int radioFWId = radioGroupFW.getCheckedRadioButtonId();

        radioButtonFW = findViewById(radioFWId);
        holeFWStatusString = String.valueOf(radioButtonFW.getText());
        Toast.makeText(this, "Fairway status: " + radioButtonFW.getText(),
                Toast.LENGTH_SHORT).show();
    }
}

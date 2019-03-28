package com.pasiriihinen.golfstat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewCourseActivity extends AppCompatActivity {
    //Variables
    DatabaseHelper myDb;
    EditText editName;
    TextView holeNR, parCheck;
    Button btnAddData, btnNextHole;
    NumberPicker noPicker;
    TextView insertedHoles;
    String currentHolePar;
    Integer currentHoleNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_course);

        //DatabaseHelper
        myDb = new DatabaseHelper(this);

        //Assign the variables via findViewById
        editName = (EditText)findViewById(R.id.editText_courseName);
        parCheck = (TextView)findViewById(R.id.textView_parCheck);
        btnAddData = (Button)findViewById(R.id.btnAddNewCourse);
        holeNR = (TextView) findViewById(R.id.textView_holeNumber);
        btnNextHole = (Button)findViewById(R.id.btnNextHole);
        insertedHoles = (TextView)findViewById(R.id.textView_holePar);

        //NumberPicker
        noPicker = (NumberPicker) findViewById(R.id.numberPicker_score);
        noPicker.setMinValue(3);
        noPicker.setMaxValue(6);
        noPicker.setValue(4);
        noPicker.setWrapSelectorWheel(false);

        //Set initial variable values
        currentHoleNumber = 1;
        currentHolePar = "4";
        holeNR.setText("Hole 1");

        btnNextHole.setEnabled(false);
        //Disable btnAddData until BuildHoles enables it
        btnAddData.setEnabled(false);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnNextHole.setEnabled(true);
            }
        });

        //Listener on NumberPicker
        noPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentHolePar = (Integer.toString(newVal ));
                }
        });


        BuildHoles();
        AddData();
    }



    //Method to build the string with the hole par values
    private void BuildHoles() {
        btnNextHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentHoleNumber  < 18) {
                    insertedHoles.append(currentHolePar);
                    currentHoleNumber = currentHoleNumber + 1;
                    holeNR.setText("Hole " + currentHoleNumber);
                    sumCoursePar();
                } else if (currentHoleNumber == 18) {
                    insertedHoles.append(currentHolePar);
                    btnAddData.setEnabled(true);
                    btnNextHole.setEnabled(false);
                    sumCoursePar();
                  }
            }
        });
    }

    private void sumCoursePar() {

        //Toast.makeText(AddNewCourseActivity.this,"TestMessage " + s, Toast.LENGTH_LONG) .show();
        CharSequence parChars = insertedHoles.getText();
        int totPar = 0;
        for (int i = 0; i < parChars.length(); i++)
        {
            totPar += Character.getNumericValue(parChars.charAt(i));
        }
        parCheck.setText(Integer.toString(totPar));
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Method to post user input data to the database
    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                insertedHoles.getText().toString());
                        if(isInserted == true) {
                            Toast.makeText(AddNewCourseActivity.this, "New Course Inserted To DB", Toast.LENGTH_SHORT).show();
                            openMainActivity();
                        }
                        else
                            Toast.makeText(AddNewCourseActivity.this,"Failed To Add Data", Toast.LENGTH_LONG) .show();
                    }
                }
        );
    }



}

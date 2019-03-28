package com.pasiriihinen.golfstat;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SelectCourseActivity extends AppCompatActivity {
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        //Assign variable values
        myDb = new DatabaseHelper(this);

        final ListView myList = (ListView) findViewById(R.id.courseListView);
        populateListView();

        //Try OnClickItem
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(SelectCourseActivity.this, AddRoundActivity.class);
                Cursor cursor = (Cursor)parent.getAdapter().getItem(i);
                intent.putExtra("CourseName", cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)));
                intent.putExtra("CourseId", cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                startActivity(intent);
            }
        });

        }



    //Populate ListView from the database
        private void populateListView() {
        Cursor cursor = myDb.getCourseNameData();
        cursor.moveToFirst();
        String[] fromFieldNames = new String[] {DatabaseHelper.COL_ID, DatabaseHelper.COL_NAME};
        int[] toViewIDs = new int[] {R.id.courseID_textView, R.id.courseName_textView};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(this, R.layout.course_layout, cursor, fromFieldNames,toViewIDs,0);
        ListView myList = (ListView) findViewById(R.id.courseListView);
        myList.setAdapter(myCursorAdapter);
    }




}

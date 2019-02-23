package com.example.wenyuyang.gwebapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectCoursesActivity extends AppCompatActivity {

    Spinner mSpinner;
    Button mButtonAddCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_courses);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mButtonAddCourse = (Button) findViewById(R.id.button_add_course);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Subjects, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        //Default CSCI
        mSpinner.setSelection(29);

        mButtonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readCoursesOfSubject();
            }
        });
    }

    private void readCoursesOfSubject() {
        Context context = SelectCoursesActivity.this;
        Class destinationActivity = MainSelectActivity.class;
        Intent startLogin = new Intent(context, destinationActivity);
        startLogin.putExtra("Subject", mSpinner.getSelectedItem().toString());
        startActivity(startLogin);
    }
}

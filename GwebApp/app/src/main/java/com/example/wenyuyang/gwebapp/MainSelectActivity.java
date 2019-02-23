package com.example.wenyuyang.gwebapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainSelectActivity extends AppCompatActivity
        implements SubjectCoursesAdapter.ListItemListener {


    private String mSubject = "CSCI";
    private RecyclerView mSelectSubjectCourses;
    private SubjectCoursesAdapter mSubjectCourseAdapter;
    public static List<SelectCourseInfo> mListSelectCourses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("Subject")) {
            mSubject = intentThatStartedThisActivity.getStringExtra("Subject");
            System.out.println(mSubject);
            //Get Subject
            String[] ss = mSubject.split(":");
            mSubject = ss[0];
            mListSelectCourses.clear();
            for (int index = 0; index < MainActivity.mSelectCoursesInfo.size(); index++) {
                if (MainActivity.mSelectCoursesInfo.get(index).Subject.equalsIgnoreCase(mSubject))
                    mListSelectCourses.add(MainActivity.mSelectCoursesInfo.get(index));
            }
        }


        loadCourseInformationToRecycleview();
    }

    private void loadCourseInformationToRecycleview() {
        mSelectSubjectCourses = (RecyclerView) findViewById(R.id.rv_Select_Courses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mSelectSubjectCourses.setLayoutManager(layoutManager);
        mSubjectCourseAdapter = new SubjectCoursesAdapter(mListSelectCourses.size(), this);
        mSelectSubjectCourses.setAdapter(mSubjectCourseAdapter);
    }


    @Override
    public void onListItemClicked(int clickedItemIndex) {

    }
}

package com.example.wenyuyang.gwebapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailCourseActivity extends AppCompatActivity {

    TextView mTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_course);

        mTest=(TextView)findViewById(R.id.tv_CourseDetailTest);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("item")) {
            int i=intentThatStartedThisActivity.getIntExtra("item",0);
            String detail="";
            for (int index=0;index<MainActivity.mSelectCoursesInfo.size();index++)
            {
                if (MainActivity.mClassCourses.get(i).name.contains(MainActivity
                .mSelectCoursesInfo.get(index).Subject)&&
                        MainActivity.mClassCourses.get(i).name.contains(MainActivity
                        .mSelectCoursesInfo.get(index).Course)){
                    detail=MainActivity.mSelectCoursesInfo.get(index).Detail;
                }
            }
            mTest.setText("Name: "+MainActivity.mClassCourses.get(i).name
                    +"\n"+"Time: "+MainActivity.mClassCourses.get(i).time
                    +"\n"+"Instructors: "+MainActivity.mClassCourses.get(i).instructors
                    +"\n"+"Scheduletypr: "+MainActivity.mClassCourses.get(i).scheduleType
                    +"\n"+"DateRange: "+MainActivity.mClassCourses.get(i).dateRange
                    +"\n"+"Location: "+MainActivity.mClassCourses.get(i).where
                    +"\n"+"Campus: "+MainActivity.mClassCourses.get(i).campus
                    +"\n"+"Status: "+MainActivity.mClassCourses.get(i).status
                    +"\n"+"CRN: "+MainActivity.mClassCourses.get(i).CRN
                    +"\n"+"Day: "+MainActivity.mClassCourses.get(i).day
                    +"\n"+"GradeMode: "+MainActivity.mClassCourses.get(i).gradeMode
                    +"\n"+"Level: "+MainActivity.mClassCourses.get(i).level
                    +"\n"+"Detail: "+detail
            );
        }

    }
}

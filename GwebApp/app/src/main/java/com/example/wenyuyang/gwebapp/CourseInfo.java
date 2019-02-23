package com.example.wenyuyang.gwebapp;

//A class to store course information
public class CourseInfo {
    public String name = "";
    public String term = "";
    public String CRN = "";
    public String status = "";
    public String assignedInstructor = "";
    public String gradeMode = "";
    public String credits = "";
    public String level = "";
    public String campus = "";

    public String type = "";
    public String time = "";
    public String day = "";
    public String where = "";
    public String dateRange = "";
    public String scheduleType = "";
    public String instructors = "";


    public CourseInfo(String name, String term, String crn, String status, String assignedInstructor,
                      String gradeMode, String credits, String level, String campus, String type,
                      String time, String day, String where, String dateRange, String scheduleType,
                      String instructors) {
        this.name = name;
        this.term = term;
        this.CRN = crn;
        this.status = status;
        this.assignedInstructor = assignedInstructor;
        this.gradeMode = gradeMode;
        this.credits = credits;
        this.level = level;
        this.campus = campus;
        this.type = type;
        this.time = time;
        this.day = day;
        this.where = where;
        this.dateRange = dateRange;
        this.scheduleType = scheduleType;
        this.instructors = instructors;
    }

    public CourseInfo() {

    }

    //record base on the position
    public void changeCourseInfo(int typeTag, int tag, String str) {
        if (typeTag % 2 == 1) {
            switch (tag) {
                case 2:
                    term = str;
                case 4:
                    CRN = str;
                case 6:
                    status = str;
                case 8:
                    assignedInstructor = str;
                case 10:
                    gradeMode = str;
                case 12:
                    credits = str;
                case 14:
                    level = str;
                case 16:
                    campus = str;
            }
        } else {
            switch (tag) {
                case 8:
                    type = str;
                case 9:
                    time = str;
                case 10:
                    day = str;
                case 11:
                    where = str;
                case 12:
                    dateRange = str;
                case 13:
                    scheduleType = str;
                case 14:
                    instructors = str;
            }
        }
    }
}

package com.example.stanislavlukanov.td.SQL;


public class SQL_Tab1 {

    public static final String TABLE_NAME = "tab";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";





    private int id;
    private String name;
    private String time;
    private int year;
    private int month;
    private int day;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_TIME + " TEXT,"
                    + COLUMN_YEAR + " INT,"
                    + COLUMN_MONTH + " INT,"
                    + COLUMN_DAY + " INT"
                    + ")";

    public SQL_Tab1() {

    }
    public SQL_Tab1(int id, String name, String time, int year, int month , int day){
        this.id = id;
        this.name = name;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) { this.time = time; }

    public int getYear() { return year; }

    public void setYear(int year) { this.year = year; }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) { this.month = month; }

    public int getDay() {
        return day;
    }

    public void setDay(int day) { this.day = day; }

}

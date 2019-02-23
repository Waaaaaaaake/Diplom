package com.example.stanislavlukanov.td.Navigation_Menu_Fragments.DevRes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.stanislavlukanov.td.MyEventDay;
import com.example.stanislavlukanov.td.R;
import com.example.stanislavlukanov.td.SQL.SQL_Tab1;
import com.example.stanislavlukanov.td.SQL.SQL_Tab1_DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Tab2 extends Fragment   {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<EventDay> mEventDays = new ArrayList<>();
    private CalendarView mCalendarView;
    List<SQL_Tab1> devsList = new ArrayList<>();
    SQL_Tab1_DatabaseHelper db;
    private Calendar mCalendar;

    private boolean logic = false;
    public Tab2() {
        // Required empty public constructor
    }

    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


//        widget.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
//        widget.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium);
//        widget.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
    }



    @Override
    public void onStop() {
        super.onStop();
        db = new SQL_Tab1_DatabaseHelper(getContext());
        devsList.addAll(db.getAll());
    }

    @Override
    public void onResume() {
        super.onResume();
        int a = 1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int a = 1;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int a = 1;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int a = 1;
    }

    @Override
    public void onStart() {
        super.onStart();
        int a = 1;
    }

    // далее лишний класс
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            mCalendarView = (com.applandeo.materialcalendarview.CalendarView) view.findViewById(R.id.calendarView);
            db = new SQL_Tab1_DatabaseHelper(getContext());
            devsList.addAll(db.getAll());

            for (int counter = 0; counter < devsList.size(); counter++) {

                mCalendar = Calendar.getInstance();
                mCalendar.set(devsList.get(counter).getYear(), devsList.get(counter).getMonth(), devsList.get(counter).getDay());

                MyEventDay myEventDay = new MyEventDay(mCalendar,
                        R.drawable.ic_today_24dp, devsList.get(counter).getName());

                mCalendarView.setDate(myEventDay.getCalendar());

                mEventDays.add(myEventDay);
                mCalendarView.setEvents(mEventDays);

            }

            mCalendarView.setOnDayClickListener(new OnDayClickListener() {
                @Override
                public void onDayClick(EventDay eventDay) {
                    // start miniactive
                }
            });
        int a = 1;
        super.onViewCreated(view, savedInstanceState);

}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fr_dev_tab2, container, false);

        Tab2.newInstance(mParam1,mParam2);
        mCalendarView = (com.applandeo.materialcalendarview.CalendarView) v.findViewById(R.id.calendarView);

        db = new SQL_Tab1_DatabaseHelper(getContext());
        devsList.addAll(db.getAll());

        for (int counter = 0; counter <  devsList.size(); counter++) {

            mCalendar = Calendar.getInstance();
            mCalendar.set(devsList.get(counter).getYear(),devsList.get(counter).getMonth(),devsList.get(counter).getDay());

            MyEventDay myEventDay = new MyEventDay(mCalendar,
                    R.drawable.ic_today_24dp, devsList.get(counter).getName());

            mCalendarView.setDate(myEventDay.getCalendar());

            mEventDays.add(myEventDay);
            mCalendarView.setEvents(mEventDays);

        }

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                // start miniactive
            }
        });
        logic = true;
        int a = 1;
        return v;
    }

// ATTACH -- CREATE --- CREATEVIEW --- ACTIVITYCREATE--- ONSTART  --- RESUME --- PAUSE  ----- ON STOP


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

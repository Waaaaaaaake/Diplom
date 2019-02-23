package com.example.stanislavlukanov.td.Navigation_Menu_Fragments.DevRes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.stanislavlukanov.td.R;
import com.example.stanislavlukanov.td.SQL.SQL_MyDividerItemDecoration;
import com.example.stanislavlukanov.td.SQL.SQL_RecyclerTouchListener;
import com.example.stanislavlukanov.td.SQL.SQL_Tab1;
import com.example.stanislavlukanov.td.SQL.SQL_Tab1Adapter;
import com.example.stanislavlukanov.td.SQL.SQL_Tab1_DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class Tab1 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Calendar mCurrentDate;

    SQL_Tab1Adapter mAdapter;
    List<SQL_Tab1> devsList = new ArrayList<>();
    CoordinatorLayout coordinatorLayout;
    RecyclerView recyclerView;
    TextView noDevsView;
    SQL_Tab1_DatabaseHelper db;
    private OnFragmentInteractionListener mListener;

    public Tab1() {
    }


    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View v = inflater.inflate(R.layout.fr_dev_tab1, container, false);
        coordinatorLayout = v.findViewById(R.id.coordinator_tab1);
        recyclerView = v.findViewById(R.id.tab1_recycler);
        noDevsView = v.findViewById(R.id.empty_devs_view);


        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity.getSupportActionBar() != null;

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_tab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDevsDialog(false, null, -1);
            }
        });
        return v;
    }


        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = new SQL_Tab1_DatabaseHelper(getContext());

        devsList.addAll(db.getAll());

        mAdapter = new SQL_Tab1Adapter(getContext(), devsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SQL_MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyDevs();

        recyclerView.addOnItemTouchListener(new SQL_RecyclerTouchListener(getContext(),
                recyclerView, new SQL_RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }


    private void createDev(String name, String time, Calendar mCurrentDate){

      int year = mCurrentDate.get(Calendar.YEAR);
      int month = mCurrentDate.get(Calendar.MONTH);
      int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

        long id = db.insertDev(name,time,year,month,day);
        SQL_Tab1 n = db.getDev(id);
        if(n != null){
            devsList.add(0,n);
            mAdapter.notifyDataSetChanged();

            toggleEmptyDevs();
        }
    }
    private void updateDev(String name, String time, Calendar mCurrentDate, int position) {
        SQL_Tab1 n = devsList.get(position);

        int year = mCurrentDate.get(Calendar.YEAR);
        int month = mCurrentDate.get(Calendar.MONTH);
        int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

        n.setName(name);
        n.setTime(time);
        n.setDay(day);
        n.setMonth(month);
        n.setYear(year);

        db.updateDev(n);

        devsList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyDevs();
    }

    private void deleteDevs(int position) {
        db.deleteDev(devsList.get(position));

        devsList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyDevs();
    }


    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Редактировать событие", "Удалить"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Выберите действие");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showDevsDialog(true, devsList.get(position), position);
                } else {
                    deleteDevs(position);
                }
            }
        });
        builder.show();
    }


    private void showDevsDialog(final boolean shouldUpdate, final SQL_Tab1 dev, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View view = layoutInflaterAndroid.inflate(R.layout.sql_tab1_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        final EditText inputDev = view.findViewById(R.id.etName_tab1);
        EditText inputTime = (EditText) view.findViewById(R.id.etTime_tab1);



        inputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDate = Calendar.getInstance();
                int hour =  mCurrentDate.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentDate.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.app.AlertDialog.THEME_HOLO_LIGHT,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String str = hourOfDay + ":" + minute;
                                if(hourOfDay < 10){
                                    if (minute < 10){
                                        str = "0" + hourOfDay + ":" + "0" + minute;
                                    } else {
                                        str = "0" + hourOfDay + ":" + minute;
                                    }
                                }
                                inputTime.setText(str);

                                mCurrentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                mCurrentDate.set(Calendar.MINUTE, minute);
                            }
                        }, hour ,minute, true);
                timePickerDialog.show();
            }
        });

        EditText inputCalendar = (EditText) view.findViewById(R.id.txtDateEntered);

        inputCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // получили текущую дату
                mCurrentDate = Calendar.getInstance();

                int year = mCurrentDate.get(Calendar.YEAR);
                int month = mCurrentDate.get(Calendar.MONTH);
                int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        inputCalendar.setText(dayOfMonth+ "-" + (month+1) + "-" + year);
                        mCurrentDate.set(year, month, dayOfMonth);

                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });

        TextView dialogTitle = view.findViewById(R.id.tab1_dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.msg_new_dev) : getString(R.string.lbl_edit_dev_title));

        if (shouldUpdate && dev != null) {
            // место для восстановления данных при редактировании
            inputDev.setText(dev.getName());

            inputTime.setText(dev.getTime());



            int year = dev.getYear();
            int month = dev.getMonth();
            int day = dev.getDay();
            inputCalendar.setText(day+ "-" + (month+1) + "-" + year);

        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "Изменить" : "Сохранить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputDev.getText().toString()) || TextUtils.isEmpty(inputCalendar.getText())) {
                    Toast.makeText(getActivity(), "Введите данные!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (shouldUpdate && dev != null) {

                    updateDev(inputDev.getText().toString(), inputTime.getText().toString(), mCurrentDate,  position);
                } else {
                    createDev(inputDev.getText().toString(),inputTime.getText().toString(), mCurrentDate);
                }
            }
        });
    }

    private void toggleEmptyDevs() {

        if (db.getDevsCount() > 0) {
            noDevsView.setVisibility(View.GONE);
        } else {
            noDevsView.setVisibility(View.VISIBLE);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

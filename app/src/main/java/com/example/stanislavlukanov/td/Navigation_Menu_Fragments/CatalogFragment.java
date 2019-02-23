package com.example.stanislavlukanov.td.Navigation_Menu_Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stanislavlukanov.td.ContactActivity;
import com.example.stanislavlukanov.td.R;
import com.example.stanislavlukanov.td.SQL.SQL_Catalog_DatabaseHelper;
import com.example.stanislavlukanov.td.SQL.SQL_MyDividerItemDecoration;
import com.example.stanislavlukanov.td.SQL.SQL_Catalog;
import com.example.stanislavlukanov.td.SQL.SQL_CatalogAdapter;
import com.example.stanislavlukanov.td.SQL.SQL_RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment {

    public static CatalogFragment newInstance(){
        Bundle args = new Bundle();
        CatalogFragment fragment = new CatalogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    SQL_CatalogAdapter mAdapter;
    List<SQL_Catalog> CatalogList = new ArrayList<>();
    CoordinatorLayout coordinatorLayout;
    RecyclerView recyclerView;
    TextView noContactsView;
    SQL_Catalog_DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_catalog,container, false);

        coordinatorLayout = v.findViewById(R.id.coordinator_layout_catalog);
        recyclerView = v.findViewById(R.id.recycler_view_catalog);
        noContactsView = v.findViewById(R.id.empty_contacts_view);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity.getSupportActionBar() != null;

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_contact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCatalogDialog(false, null, -1);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = new SQL_Catalog_DatabaseHelper(getContext());
        CatalogList.addAll(db.getAllContacts());
        mAdapter = new SQL_CatalogAdapter(getContext(), CatalogList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SQL_MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);


        toggleEmptyCatalogs();

        recyclerView.addOnItemTouchListener(new SQL_RecyclerTouchListener(getContext(),
                recyclerView, new SQL_RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                // Click on recyclerView
                SQL_Catalog n = CatalogList.get(position);

                Intent intent = new Intent(getActivity(), ContactActivity.class);
                intent.putExtra("CONTACT", n.getContact());
                intent.putExtra("DESCRIPTION",n.getDescription());
                intent.putExtra("MAIL", n.getMail());
                intent.putExtra("NUMBER", n.getNumber());


                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

                showActionsDialog(position);
            }
        }));
    }

    private void createContact(String contact, String description, String mail, String number) {

        long id = db.insertCatalog(contact, description, mail, number);

        SQL_Catalog n = db.getContact(id);

        if (n != null) {
            CatalogList.add(0, n);

            // refresh
            mAdapter.notifyDataSetChanged();

            toggleEmptyCatalogs();
        }
    }


    //  here
    private void updateContact(String contact, String description, String mail, String number, int position) {
        SQL_Catalog n = CatalogList.get(position);
        n.setContact(contact);
        n.setDescription(description);
        n.setMail(mail);
        n.setNumber(number);

        db.updateContact(n);

        CatalogList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyCatalogs();
    }


    private void deleteContact(int position) {
        db.deleteContact(CatalogList.get(position));

        CatalogList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyCatalogs();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Редактировать запись", "Удалить"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Выберите действие");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showCatalogDialog(true, CatalogList.get(position), position);
                } else {
                    deleteContact(position);
                }
            }
        });
        builder.show();
    }

    private void showCatalogDialog(final boolean shouldUpdate, final SQL_Catalog contact, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View view = layoutInflaterAndroid.inflate(R.layout.sql_catalog_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        final EditText inputContact = view.findViewById(R.id.layout_contact);
        final EditText inputDescription = view.findViewById(R.id.layout_description);
        final EditText inputMail = view.findViewById(R.id.layout_mail);
        final EditText inputNumber = view.findViewById(R.id.layout_number);

        TextView dialogTitle = view.findViewById(R.id.catalog_dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.msg_new_contact) : getString(R.string.lbl_edit_contact_title));

        if (shouldUpdate && contact != null) {
            inputContact.setText(contact.getContact());
            inputDescription.setText(contact.getDescription());
            inputMail.setText(contact.getMail());
            inputNumber.setText(contact.getNumber());

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
                if (TextUtils.isEmpty(inputContact.getText().toString())) {
                    Toast.makeText(getActivity(), "Введите имя контакта!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (shouldUpdate && contact != null) {

                    updateContact(inputContact.getText().toString(),inputDescription.getText().toString(),inputMail.getText().toString(),inputNumber.getText().toString(), position);
                } else {
                    createContact(inputContact.getText().toString(),inputDescription.getText().toString(),inputMail.getText().toString(),inputNumber.getText().toString());
                }
            }
        });
    }
    private void toggleEmptyCatalogs() {

        if (db.getContactsCount() > 0) {
            noContactsView.setVisibility(View.GONE);
        } else {
            noContactsView.setVisibility(View.VISIBLE);
        }
    }
}

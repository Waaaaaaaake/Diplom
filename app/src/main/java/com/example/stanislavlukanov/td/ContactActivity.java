package com.example.stanislavlukanov.td;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import android.widget.Toolbar;

public class ContactActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_inside_contact);
        String contact =  (String) getIntent().getSerializableExtra("CONTACT");
        String description = (String) getIntent().getSerializableExtra("DESCRIPTION");
        String mail = (String) getIntent().getSerializableExtra("MAIL");
        String number = (String) getIntent().getSerializableExtra("NUMBER");

        CollapsingToolbarLayout сollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_contact);
        сollapsingToolbarLayout.setTitle(contact.toString());

        EditText editText_Phone = findViewById(R.id.et_phone_final);
        editText_Phone.setText(number.toString());


        EditText editText_Mail = findViewById(R.id.et_email_final);
        editText_Mail.setText(mail.toString());

        // setTitle(contact.toString());


        FloatingActionButton fab_phone = (FloatingActionButton) findViewById(R.id.fab_phone);
        fab_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dial = "tel:" + number.toString();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });

        FloatingActionButton fab_mail = (FloatingActionButton) findViewById(R.id.fab_mail);
        fab_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Выберите email клиент :"));
            }
        });
    }
}

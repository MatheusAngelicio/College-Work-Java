package com.example.agendaapplication.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.agendaapplication.R;
import com.example.agendaapplication.model.Contact;
import com.example.agendaapplication.model.DataModel;

public class DetailsActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText phoneEditText;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);

        Bundle extra = getIntent().getExtras();
        index = extra.getInt("index");
        if (index != -1) {
            Contact c = DataModel.getInstance().contacts.get(index);
            nameEditText.setText(c.getName());
            phoneEditText.setText(c.getPhone());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if (name.length() > 1 && phone.length() > 1) {
            if (index == -1) {
                // adicionando no singleton datamodel
                DataModel.getInstance().contacts.add(new Contact(name, phone));
            } else {
                Contact c = DataModel.getInstance().contacts.get(index);
                c.setName(name);
                c.setPhone(phone);
            }

            DataModel.getInstance().saveToFile(DetailsActivity.this);
            finish();


        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
            builder.setTitle("Atenção");
            builder.setMessage("Voçe deseja voltar?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("Nao", null);
            builder.create().show();
        }


    }
}
package com.example.agendaapplication.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.agendaapplication.R;
import com.example.agendaapplication.model.Contact;
import com.example.agendaapplication.model.DataModel;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContactAdapter adapter = new ContactAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        DataModel.getInstance().createDatabase(getApplicationContext());


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setOnItemClicklistener((view, position) -> {

            goToDetailActivity(position);

        });

        adapter.setLongClickListener((view, position) -> {

            Contact c = DataModel.getInstance().getContact(position);
                    DataModel.getInstance().removeContact(position);
            adapter.notifyItemRemoved(position);
            View contextView = findViewById(android.R.id.content);
            Snackbar.make(contextView, "Contato Removido", Snackbar.LENGTH_LONG)
                    .setAction("Undo", v -> {
                        DataModel.getInstance().insertContact(c, position);
                        adapter.notifyItemInserted(position);
                    })
                    .show();
            return true;


        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // o certo é:
        // quando eu salvar um novo usuario, quando voltar pra essa tela eu aviso que salvei um novo usuario
        // pra eu pegar o resultado aqui na volta, e chamar o notifyDataSetChanged
        // usar como se fosse o onActivityResult (tem uma aula de activitys)
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    void goToDetailActivity(int index){
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add_contact) {
            goToDetailActivity(-1);

        }
        return super.onOptionsItemSelected(item);
    }
}
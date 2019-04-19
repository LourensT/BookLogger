package com.example.booklogger;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.sqltut.myDBHandler;

public class MainActivity extends AppCompatActivity {

    private Button addBookButton;
    com.example.sqltut.myDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new myDBHandler(this, null, null, 1);
        Cursor cursor = dbHandler.getFullCursor();

        ListView mainListView = findViewById(R.id.fullList);
        cursorAdapter mainAdapter = new cursorAdapter(this, cursor, false);
        mainListView.setAdapter(mainAdapter);

        mainListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int dbid = position + 1;

                        Intent editBook = new Intent(view.getContext(), EditBook.class);
                        editBook.putExtra("book", dbid);
                        startActivity(editBook);

                    }
                }
        );
        mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int dbid = position + 1;
                return false;
            }
        });

        addBookButton = findViewById(R.id.addBook);
        addBookButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent addTitleIntent = new Intent(v.getContext() , AddTitle.class);
                        startActivity(addTitleIntent);
                    }
                }
        );
    }


}

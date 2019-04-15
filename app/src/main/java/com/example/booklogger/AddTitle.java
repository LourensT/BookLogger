package com.example.booklogger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sqltut.myDBHandler;

public class AddTitle extends AppCompatActivity {

    private EditText inputTitle;
    private EditText inputAuthor;
    private EditText inputPages;
    private EditText inputDays;
    private TextView pagesPerPage;
    private Button submitButton;
    private com.example.sqltut.myDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_title);

        dbHandler = new myDBHandler(this, null, null, 1);

        inputTitle = findViewById(R.id.inputTitle);
        inputAuthor = findViewById(R.id.inputAuthor);
        inputPages = findViewById(R.id.inputPages);
        inputDays = findViewById(R.id.inputDays);
        pagesPerPage = findViewById(R.id.pagesPerDay);
        submitButton = findViewById(R.id.addBookButton);

        inputDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float pagesPerDay = Float.valueOf(inputPages.getText().toString()) / Float.valueOf(inputDays.getText().toString());
                String dailyReading =  String.valueOf(pagesPerDay) + " Pages a Day";
                pagesPerPage.setText(dailyReading);

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book newBook = new Book();
                newBook.setTitle(inputTitle.getText().toString());
                newBook.setAuthor(inputAuthor.getText().toString());
                newBook.setPages(Integer.parseInt(inputPages.getText().toString()));
                newBook.setDays(Integer.parseInt(inputDays.getText().toString()));

                dbHandler.appendBook(newBook);

                Intent toMainIntent = new Intent(v.getContext(), MainActivity.class);
                startActivity(toMainIntent);
            }
        });
    }
}

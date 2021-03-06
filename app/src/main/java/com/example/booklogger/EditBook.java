package com.example.booklogger;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqltut.myDBHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class EditBook extends AppCompatActivity implements DatePickerFragment.dateInterface {

    private com.example.sqltut.myDBHandler dbHandler;
    private TextView titleText;
    private TextView authorText;
    private TextView pagesText;
    private TextView currentPageText;
    private ListView progressList;

    private Button selectDateButton;
    private EditText inputPage;

    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        dbHandler = new myDBHandler(this, null, null ,1);

        titleText = findViewById(R.id.titleText);
        authorText = findViewById(R.id.authorText);
        pagesText = findViewById(R.id.pagesText);
        currentPageText = findViewById(R.id.textCurrentPage);

        selectDateButton = findViewById(R.id.selectDate);
        inputPage = findViewById(R.id.inputPage);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        bookId = b.getInt( "book");
        Book book = dbHandler.getBook(bookId);
        titleText.setText(book.getTitle());
        authorText.setText(book.getAuthor());
        pagesText.setText("Pages " + String.valueOf(book.getPages()));
        currentPageText.setText("At Page " + String.valueOf(book.getCurrentPage()));


        progressList = findViewById(R.id.progress);
        Cursor cursor = dbHandler.getProgressCursor(bookId);
        progressCursorAdapter mainAdapter = new progressCursorAdapter(this, cursor, false);
        progressList.setAdapter(mainAdapter);



        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPage = inputPage.getText().toString();
                if(enteredPage.isEmpty()){
                    Toast.makeText(EditBook.this, "Enter the current page you're on", Toast.LENGTH_LONG).show();
                }
                else{
                    DialogFragment dateDialog = new DatePickerFragment();
                    int page = Integer.valueOf(enteredPage);
                    ((DatePickerFragment) dateDialog).setValues(bookId, page);
                    dateDialog.show(getSupportFragmentManager(), "datePicker");

                }
            }
        });
    }


    public void onDateSelected(String date, int id, int page){
        myDBHandler dbHandler = new myDBHandler(this, null, null, 1);
        dbHandler.addProgressString(id, date, page);
        finish();
        startActivity(getIntent());
        //inputPage.setText("");
    }

}


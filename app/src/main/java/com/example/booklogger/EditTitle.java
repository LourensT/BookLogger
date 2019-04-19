package com.example.booklogger;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sqltut.myDBHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditTitle extends AppCompatActivity {

    private EditText inputTitle;
    private EditText inputAuthor;
    private EditText inputPages;
    private EditText inputDays;
    private Button submitButton;
    private Button setLogoButton;
    private com.example.sqltut.myDBHandler dbHandler;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String logoPath = "Not Set";
    private int currentBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_title);

        dbHandler = new myDBHandler(this, null, null, 1);
        inputTitle = findViewById(R.id.inputTitle);
        inputAuthor = findViewById(R.id.inputAuthor);
        inputPages = findViewById(R.id.inputPages);
        inputDays = findViewById(R.id.inputDays);
        submitButton = findViewById(R.id.addBookButton);
        setLogoButton = findViewById(R.id.addLogoButton);


        Intent receivedIntent = getIntent();
        Bundle b = receivedIntent.getExtras();
        currentBookId = b.getInt("ID");

        Book oldBook = dbHandler.getBook(currentBookId);
        inputTitle.setText(oldBook.getTitle());
        inputAuthor.setText(oldBook.getAuthor());
        inputPages.setText(String.valueOf(oldBook.getPages()));
        inputDays.setText(String.valueOf(oldBook.getDays()));
        logoPath = oldBook.getCoverPath();


        setLogoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
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
                newBook.setCoverPath(logoPath);

                dbHandler.updateBook(currentBookId, newBook);

                Intent toMainIntent = new Intent(v.getContext(), MainActivity.class);
                startActivity(toMainIntent);
            }
        });

    }

    public void launchCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extra = data.getExtras();
            Bitmap photo = (Bitmap) extra.get("data");

            logoPath = saveToInternalStorage(photo);
            Log.i("DB", logoPath);
        }

    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String fp = "logo_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File myPath=new File(directory, fp);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath() + "/" + fp;
    }

}

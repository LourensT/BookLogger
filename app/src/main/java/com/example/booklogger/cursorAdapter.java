package com.example.booklogger;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class cursorAdapter extends CursorAdapter {
    public cursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView itemTitle = view.findViewById(R.id.itemTitle);
        TextView itemAuthor = view.findViewById(R.id.itemAuthor);
        TextView itemPages = view.findViewById(R.id.itemPages);
        TextView itemCurrentPage = view.findViewById(R.id.itemCurrentPage);
        ImageView bookLogo = view.findViewById(R.id.bookLogo);

        String Title = cursor.getString(cursor.getColumnIndex("_title"));
        String Author = cursor.getString(cursor.getColumnIndex("_author"));
        int Pages = cursor.getInt(cursor.getColumnIndex("_pages"));
        int currentPage = cursor.getInt(cursor.getColumnIndex("_currentPage"));
        String logoPath = cursor.getString(cursor.getColumnIndex("_coverPath"));

        itemTitle.setText(Title);
        itemAuthor.setText(Author);
        itemPages.setText("Pages: "+  String.valueOf(Pages));
        itemCurrentPage.setText("At Page "+  String.valueOf(currentPage));

        bindLogoToView(logoPath, bookLogo);

    }

    private void bindLogoToView(String path, ImageView imageview)
    {
        if(path != "Not Set"){
            try {
                File f = new File(path);

                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                imageview.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }
}


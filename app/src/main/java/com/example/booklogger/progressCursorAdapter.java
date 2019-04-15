package com.example.booklogger;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class progressCursorAdapter extends CursorAdapter {
    public progressCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_row_progress, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView itemDate = view.findViewById(R.id.dateString);
        TextView itemPages = view.findViewById(R.id.pageNumber);

        String dateString = cursor.getString(cursor.getColumnIndex("_date"));
        int Pages = cursor.getInt(cursor.getColumnIndex("_page"));

        if(dateString == null){
            Log.i("DB", "null");
        }
        //Log.i("DB", dateString);
        Log.i("DB", String.valueOf(Pages));

        itemDate.setText(dateString);
        itemPages.setText(String.valueOf(Pages));

    }
}

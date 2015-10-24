package com.apprevelations.testify;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class VideoAdapter extends CursorAdapter{


    public VideoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }



    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

}

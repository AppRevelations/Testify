package com.apprevelations.testify;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.video_recycler);

        Toolbar toolBar=(Toolbar) findViewById(R.id.toolbar_select);
        toolBar.setTitle("Select Video ");
        setSupportActionBar(toolBar);

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Video.VideoColumns.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        String paths[] = new String[cursor.getCount()];

        if (cursor != null) {
            int index = 0;
            while (cursor.moveToNext()) {
                paths[index++] = cursor.getString(0);
            }
            cursor.close();
        }

        StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        VideoAdapter mVideoAdapter = new VideoAdapter(paths);
        mRecyclerView.setAdapter(mVideoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_gridview,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.i_check)
        {

        }
        return true;
    }
}

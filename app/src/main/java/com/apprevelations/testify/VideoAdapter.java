package com.apprevelations.testify;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.HashMap;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String[] mVideos;
    HashMap<RecyclerView.ViewHolder, LoadThumbnail> mHashMap;

    public VideoAdapter(String[] videos) {
        mVideos = videos;
        mHashMap = new HashMap<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_singleview, parent, false);

        DataViewHolder dataViewHolder = new DataViewHolder(view);
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((DataViewHolder) viewHolder).thumbnail.setImageBitmap(null);

        LoadThumbnail mLoadThumbnail = new LoadThumbnail(mVideos, position, viewHolder);

        if (mHashMap.containsKey(viewHolder)) {
            (mHashMap.get(viewHolder)).cancel(true);
        }

        mHashMap.put(viewHolder, mLoadThumbnail);
        mLoadThumbnail.execute();
    }

    @Override
    public int getItemCount() {
        return mVideos.length;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        (mHashMap.get(holder)).cancel(true);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        protected ImageView thumbnail;
        protected LinearLayout selected;
        protected ProgressBar progressBar;

        public DataViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            selected = (LinearLayout) itemView.findViewById(R.id.selected);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }

    public static class LoadThumbnail extends AsyncTask<Object, Void, Bitmap> {

        String[] mVideos;
        int position;
        DataViewHolder dataViewHolder;

        public LoadThumbnail(String[] mVideos, int position, RecyclerView.ViewHolder viewHolder) {
            this.mVideos = mVideos;
            this.position = position;
            this.dataViewHolder = (DataViewHolder) viewHolder;
        }

        @Override
        protected void onPreExecute() {
            dataViewHolder.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(mVideos[position],
                    MediaStore.Images.Thumbnails.MINI_KIND);

            return thumbnail;
        }

        @Override
        protected void onPostExecute(Bitmap thumbnail) {
            dataViewHolder.progressBar.setVisibility(View.GONE);
            dataViewHolder.thumbnail.setImageBitmap(thumbnail);
        }
    }
}

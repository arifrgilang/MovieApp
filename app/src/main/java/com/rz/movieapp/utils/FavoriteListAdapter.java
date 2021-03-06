package com.rz.movieapp.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rz.movieapp.R;
import com.rz.movieapp.data.model.MovieObject;
import com.rz.movieapp.ui.activities.detail.DetailMovieActivity;

import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_ID;

public class FavoriteListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private Cursor mList;
    private Context context;

    public FavoriteListAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_viewholder, viewGroup, false);
        return new ListViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        MovieObject movieObject = getItem(i);
        String title = movieObject.getOriginal_title();
        String url = movieObject.getPoster_path();
        final String movieId = movieObject.getId();

        listViewHolder.rvText.setText(title);

        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().override(72, 72))
                .into(listViewHolder.rvImg);

        listViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(MOVIE_ID, movieId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        return mList.getCount();
    }

    public void setData(Cursor data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    private MovieObject getItem(int position){
        if (!mList.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieObject(mList);
    }
}

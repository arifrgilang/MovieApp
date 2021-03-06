package com.rz.movieapp.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rz.movieapp.BuildConfig;
import com.rz.movieapp.R;
import com.rz.movieapp.data.model.MovieObject;
import com.rz.movieapp.ui.activities.detail.DetailMovieActivity;

import java.util.ArrayList;

import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_ID;

public class MovieListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private ArrayList<MovieObject> mList;
    private Context context;

    public MovieListAdapter(Context context){
        this.mList = new ArrayList<>();
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
        String title = mList.get(i).getOriginal_title();
        String url = BuildConfig.IMG_BASE_URL + mList.get(i).getPoster_path();
        final String movieId = mList.get(i).getId();

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
        return mList.size();
    }

    public void setData(ArrayList<MovieObject> data) {
        this.mList.clear();
        this.mList.addAll(data);
        notifyDataSetChanged();
    }
}

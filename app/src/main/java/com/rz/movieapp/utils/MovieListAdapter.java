package com.rz.movieapp.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rz.movieapp.R;
import com.rz.movieapp.model.MovieObject;
import com.rz.movieapp.view.detail.DetailMovieActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private ArrayList<MovieObject> mList;
    private Context context;

    public MovieListAdapter(ArrayList<MovieObject> list, Context ctx){
        this.mList = list;
        this.context = ctx;
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
        String url = "https://image.tmdb.org/t/p/w154" + mList.get(i).getPoster_path();
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
                intent.putExtra(DetailMovieActivity.MOVIE_ID, movieId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

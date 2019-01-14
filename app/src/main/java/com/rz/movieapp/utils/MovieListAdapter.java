package com.rz.movieapp.utils;

import android.content.Context;
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

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ListViewHolder> {
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
        listViewHolder.rvText.setText(title);

        String url = "https://image.tmdb.org/t/p/w154" + mList.get(i).getPoster_path();
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().override(72, 72))
                .into(listViewHolder.rvImg);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView rvImg;
        TextView rvText;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            rvImg = itemView.findViewById(R.id.card_img);
            rvText = itemView.findViewById(R.id.card_title);
        }
    }

//    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }
//
//    public ArrayList<MovieObject> getmList() {
//        return mList;
//    }
//
//    public void setmList(ArrayList<MovieObject> mList) {
//        this.mList = mList;
//    }

}
package com.rz.movieapp.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rz.movieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class ListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.card_img) ImageView rvImg;
    @BindView(R.id.card_title) TextView rvText;

    ListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

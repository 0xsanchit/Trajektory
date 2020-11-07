package com.sanchit.trajektory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView photoid , txtProductDescription ,txtProductPrice;
    public ImageView imageView;
    public ItemClassListener listener;



    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.photo);
        //photoid = (TextView) itemView.findViewById(R.id.test);


    }

     public void setItemClickListener(ItemClassListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        listener.onClick(view , getAdapterPosition(), false);
    }
}


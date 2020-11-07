package com.sanchit.trajektory;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.MyViewHolder> {

    private Context mContext ;
    private List<Subtopics> mData1 ;
    private InterstitialAd mInterstitialAd1;


    public RecyclerViewAdapter1(Context mContext, List<Subtopics> mData1) {
        this.mContext = mContext;
        this.mData1 = mData1;
    }

    @Override
    public RecyclerViewAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_topic,parent,false);
        mInterstitialAd1 = new InterstitialAd(parent.getContext());
        mInterstitialAd1.setAdUnitId("ca-app-pub-4682278563427277/6265530105");
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());
        return new RecyclerViewAdapter1.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_book_title.setText(mData1.get(position).getName());
        // holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                } else {
                    mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                Intent intent = new Intent(mContext,display_pdf.class);

                // passing data to the book activity
                //  intent.putExtra("Title",mData.get(position).getTitle());
                // intent.putExtra("Description",mData.get(position).getDescription());
                // intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                intent.putExtra("Url",mData1.get(position).getUrl());
                // start the activity
                mContext.startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return mData1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        //ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.subtopics_text) ;
            //img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}

package com.sanchit.trajektory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class BookSolutionMathsFragment extends Fragment {

    List<Book> Book2;
    private AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_solution_maths, container, false);


        mAdView = view.findViewById(R.id.adViewbooksolutionm);
        AdRequest adRequest = new AdRequest.Builder().build();

        Book2 = new ArrayList<>();
        Book2.add(new Book("Thomas Calculus","","",R.drawable.thomas_calculus,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/Thomas%20calculus-solution%20manual.pdf?alt=media&token=ac9e5390-d298-42d3-bd35-628095b99aa4"));
        //Book2.add(new Book("","","",R.drawable.hcv1,""));
        //Book2.add(new Book("","","",R.drawable.hcv1,""));
        //Book2.add(new Book("","","",R.drawable.hcv1,""));



        mAdView.loadAd(adRequest);


        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview10);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(),Book2);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),2));
        myrv.setAdapter(myAdapter);

        return view;
    }
}
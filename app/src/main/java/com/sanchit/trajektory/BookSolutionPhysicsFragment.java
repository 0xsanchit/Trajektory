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

public class BookSolutionPhysicsFragment extends Fragment {

    List<Book> lstBook;
    private AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_solution_physics, container, false);


        lstBook = new ArrayList<>();
        lstBook.add(new Book("HC Verma part 1","Categorie Book","Description book",R.drawable.hcv1,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/HC%20Verma%20Solution's%20vol1.pdf?alt=media&token=c57d2445-b848-4620-8682-91c0b0d93543"));
        lstBook.add(new Book("HC Verma part 2","Categorie Book","Description book",R.drawable.hcv2,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/HC%20Verma%20Solution's%20vol2.pdf?alt=media&token=2c603259-ce2d-49af-a018-b7502e393d9a"));
        lstBook.add(new Book("I.E Irodov part 1","Categorie Book","Description book",R.drawable.irodov,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/Irodov%20solutions%20part%201.pdf?alt=media&token=7d7497a1-bf7f-44a1-883a-2dcd5547bef3"));
        lstBook.add(new Book("I.E Irodov part 2","Categorie Book","Description book",R.drawable.irodov,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/Irodov%20solutions%20part%202.pdf?alt=media&token=9f190db9-b4d8-4b76-accc-205e1253cf49"));
        lstBook.add(new Book("DC Pandey Mechanics - Volume 1","Categorie Book","Description book",R.drawable.dcp_vol1,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/dcp_mech_vol1%20sol.pdf?alt=media&token=351a92a3-ef8f-40c5-a5b0-643f0068cb3a"));
        lstBook.add(new Book("DC Pandey Mechanics - Volume 2","Categorie Book","Description book",R.drawable.dcp_vol2,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/dcp_mech_vol2%20sol.pdf?alt=media&token=7403342d-93fb-44ee-bff1-77e31991261a"));
        lstBook.add(new Book("DC Pandey Electricity and Magnetism","Categorie Book","Description book",R.drawable.dcp_em,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/KEY-2019-Paper1.pdf?alt=media&token=ca73b9da-c353-46eb-875f-6cc879dc6c29"));
        lstBook.add(new Book("DC Pandey Optics and Modern Physics","Categorie Book","Description book",R.drawable.dcp_optics,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/KEY-2019-Paper1.pdf?alt=media&token=ca73b9da-c353-46eb-875f-6cc879dc6c29"));






        mAdView = view.findViewById(R.id.adViewbooksolutionp);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview1);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(),lstBook);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),2));
        myrv.setAdapter(myAdapter);

        return view;
    }
}
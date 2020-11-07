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

public class BookSolutionChemistryFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_book_solution_chemistry, container, false);

        Book2 = new ArrayList<>();
        Book2.add(new Book("Himanshu Pandey","Categorie Book","Description book",R.drawable.himanshu_pandey,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/Solutions%20of%20Himanshu%20Pandey%20Advanced%20Problems%20In%20Organic%20Chemistry(Original%20Solutions)%20(1).pdf?alt=media&token=06cc4ac3-ed57-4133-9efe-d9f3d9ff9c9a"));
        Book2.add(new Book("MS Chauhan","Categorie Book","Description book",R.drawable.ms_chauhan,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/MS%20Chauhan%20solutions.pdf?alt=media&token=4eba5fe6-2bf4-4749-874f-00344abc6c2f"));
        Book2.add(new Book("Atkins Physical Chemistry","","",R.drawable.atkins,"https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/Atkins_Physical_Chemistry_8th_instructor.pdf?alt=media&token=d691694b-688c-44d6-97b2-1935bb55ebe2"));
        //Book2.add(new Book("","","",R.drawable.,""));


        mAdView = view.findViewById(R.id.adViewbooksolutionc);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview5);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(),Book2);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),2));
        myrv.setAdapter(myAdapter);

        return view;
    }
}
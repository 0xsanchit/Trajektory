package com.sanchit.trajektory;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.sanchit.trajektory.R;

public class ChemistryFragment extends Fragment {


    private AdView mAdView3;
    private InterstitialAd mInterstitialAd1;

    ImageButton imagebutton1;
    ImageButton imagebutton2;
    ImageButton imagebutton3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chemistry, container, false);

        imagebutton1 = (ImageButton) view.findViewById(R.id.formula_list1);
        imagebutton2 = (ImageButton) view.findViewById(R.id.summary1);
        imagebutton3 =  (ImageButton) view.findViewById(R.id.book_solution1);

        mInterstitialAd1 = new InterstitialAd(getContext());
        mInterstitialAd1.setAdUnitId("ca-app-pub-4682278563427277/4405653525");
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());

        imagebutton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                } else {
                    mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                Fragment selectedfragment=new FormulaListChemistryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, selectedfragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
        });

        imagebutton2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                } else {
                    mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                Fragment selectedfragment=new SummaryChemistryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, selectedfragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
        });

        imagebutton3.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                } else {
                    mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                Fragment selectedfragment=new BookSolutionChemistryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, selectedfragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
        });


        mAdView3 = view.findViewById(R.id.adViewchemistry);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView3.loadAd(adRequest);

        return view;
    }
}
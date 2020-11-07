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


public class PhysicsFragment extends Fragment {

    private InterstitialAd mInterstitialAd,mInterstitialAd1,mInterstitialAd2;
    private static final String TAG = "MainActivity";
    private AdView mAdView2;

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

        View view = inflater.inflate(R.layout.fragment_physics, container, false);

        mInterstitialAd1 = new InterstitialAd(getContext());
        mInterstitialAd1.setAdUnitId("ca-app-pub-4682278563427277/7115577349");
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());


        imagebutton1 = (ImageButton) view.findViewById(R.id.formula_list);
        imagebutton2 = (ImageButton) view.findViewById(R.id.summary);
        imagebutton3 =  (ImageButton) view.findViewById(R.id.book_solution);

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
                Fragment selectedfragment=new FormulaListPhysicsFragment();
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
                Fragment selectedfragment=new SummaryPhysicsFragment();
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
                Fragment selectedfragment=new BookSolutionPhysicsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, selectedfragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

            }
        });


        mAdView2 = view.findViewById(R.id.adViewphysics);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest);

        return view;
    }
}
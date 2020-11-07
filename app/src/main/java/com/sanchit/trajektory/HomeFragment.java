package com.sanchit.trajektory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class HomeFragment extends Fragment {


    private InterstitialAd mInterstitialAd;
    ImageButton imagebutton1;
    ImageButton imagebutton2;
    ImageButton imagebutton3;
    ImageButton imagebutton4;
    ImageButton imagebutton5;

    private static final String TAG = "MainActivity";
    private AdView mAdView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        imagebutton1 = (ImageButton) view.findViewById(R.id.physics);
        imagebutton2 = (ImageButton) view.findViewById(R.id.chemistry);
        imagebutton3 =  (ImageButton) view.findViewById(R.id.maths);

        imagebutton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                Fragment selectedfragment=new PhysicsFragment();
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
                Fragment selectedfragment=new ChemistryFragment();
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
                Fragment selectedfragment=new MathsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, selectedfragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
        });

        return view;
    }
}
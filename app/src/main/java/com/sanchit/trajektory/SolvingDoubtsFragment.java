package com.sanchit.trajektory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class SolvingDoubtsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private final List<Doubts> doubtsList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private DoubtAdapter1 doubtAdapter;
    private AdView mAdView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solving_doubts, container, false);

        mAdView = view.findViewById(R.id.adViewSolvingDoubt);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        recyclerView = view.findViewById(R.id.doubts_recycler_view5);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("SolvingDoubts").child(FirebaseAuth.getInstance().getCurrentUser().getUid());



        linearLayoutManager = new LinearLayoutManager(getContext());
        doubtAdapter = new DoubtAdapter1(doubtsList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(doubtAdapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Doubts doubt = dataSnapshot.getValue(Doubts.class);
                doubtsList.add(doubt);
                doubtAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Doubts doubt = dataSnapshot.getValue(Doubts.class);
                doubtsList.add(doubt);
                doubtAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        doubtsList.clear();
        doubtAdapter.notifyDataSetChanged();
    }

}
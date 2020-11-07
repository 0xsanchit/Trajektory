package com.sanchit.trajektory;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SolveDoubtActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private AdView mAdView1,mAdView;

    private RecyclerView chatsList;
    private DatabaseReference ChatsRef,UsersRef,NotifsRef;
    private FirebaseAuth mAuth;
    private String currentUserId;
    private InterstitialAd mInterstitialAd,mInterstitialAd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_doubt);

        BottomNavigationView navView = findViewById(R.id.bot_nav_view1);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mInterstitialAd1 = new InterstitialAd(this);
        mInterstitialAd1.setAdUnitId("ca-app-pub-4682278563427277/6742196349");
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment_container2, new PendingDoubtFragment());
        //transaction.addToBackStack(null);
// Commit the transaction
        transaction.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedfragment=new PendingDoubtFragment();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedfragment=new PendingDoubtFragment();
                    break;
                case R.id.navigation_dashboard:
                    selectedfragment=new SolvingDoubtsFragment();
                    break;
                default:
                    selectedfragment=new PendingDoubtFragment();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment_container2, selectedfragment);
            transaction.addToBackStack(null);
// Commit the transaction
            transaction.commit();
            return true;
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_tools) {
            if (mInterstitialAd1.isLoaded()) {
                mInterstitialAd1.show();
            } else {
                mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

            Intent intent = new Intent(SolveDoubtActivity.this,ProfileActivity.class);
            startActivity(intent);
        } else if(id==R.id.nav_notes) {
            if (mInterstitialAd1.isLoaded()) {
                mInterstitialAd1.show();
            } else {
                mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            Intent intent = new Intent(SolveDoubtActivity.this,NotesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Greetings from Trajektory,\n" +
                    "Most of us face troubles during our preparation for JEE. Besides academic doubts, we all have been faced with uncertainties, such as which branches are good? How much do I study? What to do if I am stressed or bored? Is my decision of taking a particular branch justified? For my rank, which college and branch should I opt for? \n" +
                    "This is where we come in. We, at Trajektory, provide free mentorship to students preparing for JEE. With our free chat based application, students can talk to a mentor and get their doubts resolved. Head over to our website, to know more about us, and download our free app to get started. We are glad to have you onboard.\n" +
                    "\n" +
                    "https://play.google.com/store/apps/details?id=com.sanchit.trajektory\n" +
                    "\n" +
                    "https://trajektory-ffb2c.web.app/";
            String shareSub = "Trajektory - The best FREE App for JEE Mentorship";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } else if(id==R.id.nav_about)
        {
            Intent intent = new Intent(SolveDoubtActivity.this,WebViewActivity.class);
            intent.putExtra("Url","https://trajektory-ffb2c.firebaseapp.com/about.html");
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page_mentor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (mInterstitialAd1.isLoaded()) {
                mInterstitialAd1.show();
            } else {
                mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            Intent intent = new Intent(SolveDoubtActivity.this,ProfileActivity.class);
            startActivity(intent);
        }
        if(id==R.id.log_out1)
        {
            FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
            if(currentUser!=null)
            {
                updateUserStatus("offline");
            }
            if (mInterstitialAd1.isLoaded()) {
                mInterstitialAd1.show();
            } else {
                mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FirebaseAuth firebaseAuth;
            FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null){
                        //Do anything here which needs to be done after signout is complete
                        updateUserStatus("offline");
                        Intent intent = new Intent(SolveDoubtActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                    }
                }
            };

//Init and attach
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.addAuthStateListener(authStateListener);

//Call signOut()
            firebaseAuth.signOut();

        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUserStatus(String state)
    {
        String saveCurrentTime,saveCurrentDate,currentUserId;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null) {
            currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            HashMap<String, Object> onlineStateMap = new HashMap<>();
            onlineStateMap.put("time", saveCurrentTime);
            onlineStateMap.put("date", saveCurrentDate);
            onlineStateMap.put("state", state);


            RootRef.child("Users").child(currentUserId).child("userState").updateChildren(onlineStateMap);
        }

    }




}
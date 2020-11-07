package com.sanchit.trajektory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.inmobi.ads.InMobiBanner;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainPageMentee extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    DatabaseReference userRef;
    FirebaseUser currentUser;
    CircleImageView mentorImage;
    TextView mentorName;
    String mentorId;
    private AdView mAdView,mAdView1;
    private InterstitialAd mInterstitialAd,mInterstitialAd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_mentee);



        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent intent = new Intent(MainPageMentee.this,MainActivity.class);
            startActivity(intent);
        }

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



        mAdView = findViewById(R.id.adViewMentee);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd1 = new InterstitialAd(this);
        mInterstitialAd1.setAdUnitId("ca-app-pub-4682278563427277/1615838654");
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("@string/interstitial_ad_sign_out");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }
        });

        mAuth =FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 5 seconds
                if(FirebaseAuth.getInstance().getCurrentUser()==null)
                {
                    Intent intent = new Intent(MainPageMentee.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }, 600);

        //FirebaseMessaging.getInstance().subscribeToTopic(mAuth.getCurrentUser().getUid());
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mentorImage = findViewById(R.id.mentor_image);
        mentorName = findViewById(R.id.mentor_name);

        if(currentUser!=null) {
            userRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mentorId = dataSnapshot.child("mentorId").getValue().toString();
                    userRef.child(mentorId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mentorName.setText(dataSnapshot.child("name").getValue().toString());
                            if (!dataSnapshot.child("image").equals("default")) {
                               // Glide.with(getApplicationContext()).load(dataSnapshot.child("image").getValue().toString()).placeholder(R.drawable.profile_image).into(mentorImage);
                                Picasso.get().load(dataSnapshot.child("image").getValue().toString()).placeholder(R.drawable.profile_image).into(mentorImage);
                            } else {
                                //Glide.with(getApplicationContext()).load(R.drawable.profile_image).into(mentorImage);
                                Picasso.get().load(R.drawable.profile_image).into(mentorImage);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

                final String[] userId = new String[1];
                final String[] userName = new String[1];
                final String[] userImage = new String[1];

                if(currentUser!=null) {
                    userRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            userId[0] = dataSnapshot.child("mentorId").getValue().toString();
                            userRef.child(userId[0]).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    userName[0] = dataSnapshot.child("name").getValue().toString();
                                    userImage[0] = dataSnapshot.child("image").getValue().toString();
                                    final Intent intent = new Intent(MainPageMentee.this, ChatActivity.class);
                                    intent.putExtra("visit_user_id", userId[0]);
                                    intent.putExtra("visit_user_name", userName[0]);
                                    intent.putExtra("visit_image", userImage[0]);
                                    mentorName.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivity(intent);
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }




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
        getMenuInflater().inflate(R.menu.main_page_mentee, menu);
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
            Intent intent = new Intent(MainPageMentee.this,ProfileActivity.class);
            startActivity(intent);
        }
        if(id==R.id.log_out)
        {
            FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
            if(currentUser!=null)
            {
                updateUserStatus("offline");
            }
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
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
                        Intent intent = new Intent(MainPageMentee.this,MainActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tools) {

            if (mInterstitialAd1.isLoaded()) {
                mInterstitialAd1.show();
            } else {
                mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            Intent intent = new Intent(MainPageMentee.this,ProfileActivity.class);
            startActivity(intent);

        } else if(id==R.id.nav_gallery)
        {
            Intent intent = new Intent(MainPageMentee.this,AskDoubtActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_notes) {
            try {
                Intent intent = new Intent(MainPageMentee.this,NotesActivity.class);
                startActivity(intent);

                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                } else {
                    mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainPageMentee.this,WebViewActivity.class);
            intent.putExtra("Url","https://trajektory-ffb2c.firebaseapp.com/about.html");
            startActivity(intent);
        }else if (id == R.id.nav_share) {

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
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            //Do anything here which needs to be done after signout is complete
            updateUserStatus("offline");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            //Do anything here which needs to be done after signout is complete
            updateUserStatus("offline");
        }
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

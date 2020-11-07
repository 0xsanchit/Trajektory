package com.sanchit.trajektory;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainPageMentor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AdView mAdView1,mAdView;

    private RecyclerView chatsList;
    private DatabaseReference ChatsRef,UsersRef,NotifsRef;
    private FirebaseAuth mAuth;
    private String currentUserId;
    private InterstitialAd mInterstitialAd,mInterstitialAd1;

    FirebaseRecyclerAdapter<Contacts,ChatsViewHolder> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_mentor);

        mAuth = FirebaseAuth.getInstance();

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent intent = new Intent(MainPageMentor.this,MainActivity.class);
            startActivity(intent);
        }

        //FirebaseMessaging.getInstance().subscribeToTopic(mAuth.getCurrentUser().getUid());

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        NotifsRef = FirebaseDatabase.getInstance().getReference().child("Notifications");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 5 seconds
                if(FirebaseAuth.getInstance().getCurrentUser()==null)
                {
                    Intent intent = new Intent(MainPageMentor.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }, 600);

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent intent = new Intent(MainPageMentor.this,MainActivity.class);
            startActivity(intent);
        }
        else {
            currentUserId = mAuth.getCurrentUser().getUid();
        }

        mInterstitialAd1 = new InterstitialAd(this);
        mInterstitialAd1.setAdUnitId("ca-app-pub-4682278563427277/6742196349");
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adViewMentor);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("@string/interstitial_ad_sign_out");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        /*mAdView1 = findViewById(R.id.adViewMentorNav);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);*/


if(currentUserId!=null) {
    ChatsRef = FirebaseDatabase.getInstance().getReference().child("Pairing").child(currentUserId);
}

        chatsList = (RecyclerView) findViewById(R.id.chats_list);
        chatsList.setLayoutManager(new LinearLayoutManager(this));


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

        FirebaseRecyclerOptions<Contacts> options ;
        options = new FirebaseRecyclerOptions.Builder<Contacts>().setQuery(ChatsRef,Contacts.class).build();


        adapter = new FirebaseRecyclerAdapter<Contacts, ChatsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsViewHolder holder, int position, @NonNull Contacts model) {

                final String userId = getRef(position).getKey();
                final String[] retImage = {"default_image"};
                NotifsRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            if(dataSnapshot.hasChild(userId))
                            {
                                Toast.makeText(MainPageMentor.this,"Hello HI",Toast.LENGTH_SHORT).show();
                                holder.status.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                UsersRef.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            if (dataSnapshot.hasChild("image")) {
                                retImage[0] = dataSnapshot.child("image").getValue().toString();
                                //Glide.with(holder.itemView.getContext()).load(retImage[0]).placeholder(R.drawable.profile_image).into(holder.profileImage);
                                Picasso.get().load(retImage[0]).placeholder(R.drawable.profile_image).into(holder.profileImage);
                            }
                            if(dataSnapshot.child("userState").hasChild("state"))
                            {
                                String state = dataSnapshot.child("userState").child("state").getValue().toString();
                                String date = dataSnapshot.child("userState").child("date").getValue().toString();
                                String time = dataSnapshot.child("userState").child("time").getValue().toString();

                                if(state.equals("online"))
                                {
                                    holder.userStatus.setText("online");
                                }
                                else if(state.equals("offline"))
                                {
                                    holder.userStatus.setText("Last Seen: "+ date +" "+time);
                                }
                            }

                            final String retName = dataSnapshot.child("name").getValue().toString();

                            holder.userName.setText(retName);
                            //holder.userStatus.setText("Last Seen: ");

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    NotifsRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(userId).removeValue();
                                    Intent chatIntent = new Intent(MainPageMentor.this,ChatActivity.class);
                                    chatIntent.putExtra("visit_user_id",userId);
                                    chatIntent.putExtra("visit_user_name",retName);
                                    chatIntent.putExtra("visit_image", retImage[0]);
                                    startActivity(chatIntent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @NonNull
            @Override
            public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout,parent,false);
                return  new ChatsViewHolder(view);
            }
        };

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
    protected void onStart() {
        super.onStart();

        try {
            chatsList.setAdapter(adapter);
            adapter.startListening();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static class ChatsViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView profileImage;
        ImageView status;
        TextView userStatus,userName;

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.users_profile_image);
            userStatus=itemView.findViewById(R.id.user_profile_status);
            userName=itemView.findViewById(R.id.user_profile_name);
            status =itemView.findViewById(R.id.user_online_status);
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
            Intent intent = new Intent(MainPageMentor.this,ProfileActivity.class);
            startActivity(intent);
        }
        if(id==R.id.log_out1)
        {
            FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
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
                        Intent intent = new Intent(MainPageMentor.this,MainActivity.class);
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

            Intent intent = new Intent(MainPageMentor.this,ProfileActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_gallery)
         {
             Intent intent =new Intent(MainPageMentor.this,SolveDoubtActivity.class);
             startActivity(intent);
         }
         else if(id==R.id.nav_notes) {
            if (mInterstitialAd1.isLoaded()) {
                mInterstitialAd1.show();
            } else {
                mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            Intent intent = new Intent(MainPageMentor.this,NotesActivity.class);
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
            Intent intent = new Intent(MainPageMentor.this,WebViewActivity.class);
            intent.putExtra("Url","https://trajektory-ffb2c.firebaseapp.com/about.html");
            startActivity(intent);
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

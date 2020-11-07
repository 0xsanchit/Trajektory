package com.sanchit.trajektory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

import com.google.ads.mediation.inmobi.InMobiConsent;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.inmobi.sdk.InMobiSdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class StartScreen extends AppCompatActivity {

    VideoView videoView;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    DatabaseReference UsersRef;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        MobileAds.initialize(this,"ca-app-pub-4682278563427277~4034076877");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        JSONObject consentObject = new JSONObject();
        try {
            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
            consentObject.put("gdpr", "1");
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        InMobiConsent.updateGDPRConsent(consentObject);

        // AerServSdk.init(activity<Activity>, siteId<String>, gdprConsentObject<JSONObject>, sdkInitializatonListener<SdkInitializationListener>)

        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_screen);
        //getSupportActionBar().hide();

        videoView = (VideoView)findViewById(R.id.videoView);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.output_free);

        videoView.setVideoURI(video);

        firebaseAuth = FirebaseAuth.getInstance();
        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users");
        currentUser=firebaseAuth.getCurrentUser();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            UsersRef.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        if(dataSnapshot.child("isMentor").getValue().toString().equals("false"))
                        {
                            updateUserStatus("online");
                            intent = new Intent(StartScreen.this,MainPageMentee.class);
                        }
                        else
                        {
                            updateUserStatus("online");
                            intent = new Intent(StartScreen.this,MainPageMentor.class);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else
        {
            intent = new Intent(StartScreen.this,MainActivity.class);
        }

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(isFinishing())
                    return;

                if(intent==null)
                {
                    intent = new Intent(StartScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                startActivity(intent);
                finish();

            }
        });

        videoView.start();
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

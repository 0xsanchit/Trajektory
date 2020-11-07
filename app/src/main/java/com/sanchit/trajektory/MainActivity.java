package com.sanchit.trajektory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.ads.mediationtestsuite.MediationTestSuite;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public EditText loginEmailId, logInpasswd;
    public static final String PREFS_NAME = "MyPrefsFile";
    private static String PREF_USERNAME = "username";
    private static String PREF_PASSWORD = "password";
    Button btnLogIn;
    CheckBox checkbox;
    TextView signup;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    DatabaseReference UsersRef;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AdView mAdView,mAdView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView1 = findViewById(R.id.adViewMain1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        MediationTestSuite.launch(MainActivity.this);



        firebaseAuth = FirebaseAuth.getInstance();
        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users");
        currentUser=firebaseAuth.getCurrentUser();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            UsersRef.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("isMentor").getValue().toString().equals("false"))
                    {
                        Intent intent = new Intent(MainActivity.this,MainPageMentee.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(MainActivity.this,MainPageMentor.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        loginEmailId = findViewById(R.id.loginEmail);
        logInpasswd = findViewById(R.id.loginpaswd);
        btnLogIn = findViewById(R.id.btnLogIn);
        signup = findViewById(R.id.TVSignIn);
        checkbox = findViewById(R.id.checkBox);

        loginEmailId.setText("");
        logInpasswd.setText("");

        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String username = pref.getString("user", null);
        String password = pref.getString("pass", null);

        if(username!=null && password!=null) {
            loginEmailId.setText(username);
            logInpasswd.setText(password);
        }


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //Toast.makeText(MainActivity.this, "User logged in ", Toast.LENGTH_SHORT).show() ;

                } else {
                    Toast.makeText(MainActivity.this, "Login to continue", Toast.LENGTH_SHORT).show() ;
                }
            }
        };
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, SignUp.class);
                startActivity(I);
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userEmail = loginEmailId.getText().toString();
                //PREF_USERNAME=userEmail;
                final String userPaswd = logInpasswd.getText().toString();
                //PREF_PASSWORD=userPaswd;
                if (userEmail.isEmpty()) {
                    loginEmailId.setError("Provide your Email first!");
                    loginEmailId.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    logInpasswd.setError("Enter Password!");
                    logInpasswd.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                }

                // else if (!(userEmail.isEmpty() && userPaswd.isEmpty()) && !(firebaseAuth.getCurrentUser().isEmailVerified()))
                // {Toast.makeText(ActivityLogin.this,"Please verify your email first",Toast.LENGTH_LONG);}

                else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {

                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(MainActivity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Not succesful", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(firebaseAuth.getCurrentUser().isEmailVerified())
                                {   if(checkbox.isChecked())
                                {
                                    getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                                            .edit()
                                            .putString("user", userEmail)
                                            .putString("pass", userPaswd)
                                            .commit();

                                }

                                else
                                {
                                    getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                                            .edit()
                                            .putString("user", "")
                                            .putString("pass", "")
                                            .commit();
                                }


                                if(firebaseAuth.getCurrentUser()==null)
                                {

                                }
                                else {
                                   UsersRef.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.child("isMentor").getValue().toString().equals("false")) {
                                                updateUserStatus("online");
                                                Intent intent = new Intent(MainActivity.this, MainPageMentee.class);
                                                startActivity(intent);
                                            } else {
                                                updateUserStatus("online");
                                                Intent intent = new Intent(MainActivity.this, MainPageMentor.class);
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }


                                }
                                else
                                {  Toast.makeText(MainActivity.this,"Please verify your email first",Toast.LENGTH_LONG).show();}

                            }
                        }
                    });
                }

                // else if (!(userEmail.isEmpty() && userPaswd.isEmpty()) && !firebaseAuth.getCurrentUser().isEmailVerified()) {
                // Toast.makeText(ActivityLogin.this,"Please verify your email first",Toast.LENGTH_LONG).show();}

                else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            updateUserStatus("offline");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
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

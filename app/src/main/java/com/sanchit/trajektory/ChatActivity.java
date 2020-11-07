package com.sanchit.trajektory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAA1Bv8WiM:APA91bGUX2m2_bNhQYIrJVDgi2tXAxGjLJxiYm7zko1m1XrnjsZycLmE4IXCH19YxCM51Y86Vzmm5dsGaVaXAwx9PyXjnB6yLstJd4YLkT5nAsZPCi-xoRgbcga8g589wQka5yFW2JKh";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    private String messageRecieverId , messageReceiverName,messageReceiverImage,messageSenderId;
    private TextView userName ,userLastSeen;
    private CircleImageView userImage;

    private Toolbar ChatToolBar;
    private ImageButton SendMessageButton,SendFilesButton;
    private EditText MessageInputText;

    private FirebaseAuth mAuth;

    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";

    private DatabaseReference RootRef,UsersRef,NotificationRef;

    private final List<Messages> messageList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessageList;
    private AdView mAdView;
    private Intent intent;

    private String saveCurrentTime,saveCurrentDate;
    private String checker="",myUrl="";
    private StorageTask uploadTask;
    private Uri fileUri;
    private ProgressDialog loadingBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mRequestQue = Volley.newRequestQueue(this);

        mAdView = findViewById(R.id.adViewChat);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        NotificationRef = FirebaseDatabase.getInstance().getReference().child("Notifications");
        messageSenderId = mAuth.getCurrentUser().getUid();


        messageRecieverId= getIntent().getExtras().get("visit_user_id").toString();
        messageReceiverName= getIntent().getExtras().get("visit_user_name").toString();
        messageReceiverImage = getIntent().getExtras().get("visit_image").toString();

        InitializeControllers();

        userName.setText(messageReceiverName);
        Glide.with(this).load(messageReceiverImage).placeholder(R.drawable.profile_image).into(userImage);

        DisplayLastSeen();

        RootRef.child("Messages").child(messageSenderId).child(messageRecieverId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages messages = dataSnapshot.getValue(Messages.class);
                messageList.add(messages);
                messageAdapter.notifyDataSetChanged();
                userMessageList.smoothScrollToPosition(userMessageList.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages messages = dataSnapshot.getValue(Messages.class);
                messageList.add(messages);
                messageAdapter.notifyDataSetChanged();
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

        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendMessage();

            }
        });

        SendFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[]=new CharSequence[]
                        {
                                "Images",
                                "PDF Files",
                                "MS Word Files"
                        };

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setTitle("Select the file");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0)
                        {
                            checker="image";

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent,"Select Image"),438);
                        }
                        if(i==1)
                        {
                            checker="pdf";


                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/pdf");
                            startActivityForResult(intent.createChooser(intent,"Select PDF file"),438);
                        }
                        if(i==2)
                        {
                            checker="docx";


                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/msword");
                            startActivityForResult(intent.createChooser(intent,"Select MS Word file"),438);
                        }
                    }
                });
                builder.show();
            }
        });

        ChatToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("isMentor").getValue().toString().equals("false"))
                        {
                            intent = new Intent(ChatActivity.this,MainPageMentee.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            intent = new Intent(ChatActivity.this,MainPageMentor.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==438 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            loadingBar.setTitle("Sending File");
            loadingBar.setMessage("Please wait,we are sending that file...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            fileUri = data.getData();
            if(!checker.equals("image"))
            {

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Document Files");
                final String[] fileUrl = new String[1];
                final String messageSenderRef = "Messages/"+messageSenderId+"/"+messageRecieverId;
                final String messageReceiverRef = "Messages/"+messageRecieverId+"/"+messageSenderId;

                final DatabaseReference userMessageKeyRef = RootRef.child("Messages").child(messageSenderId).child(messageRecieverId).push();

                final String messagePushId = userMessageKeyRef.getKey();

                final StorageReference filePath = storageReference.child(messagePushId +"."+checker);

                //final UploadTask uploadTask = filePath.putFile(fileUri);

                UploadTask uploadTask1 = (UploadTask) filePath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Uri downloadUrl=uri;
                                fileUrl[0] =downloadUrl.toString();

                                Map messageTextBody = new HashMap();
                                messageTextBody.put("message",fileUrl[0] );
                                messageTextBody.put("name",fileUri.getLastPathSegment());
                                messageTextBody.put("type",checker);
                                messageTextBody.put("from",messageSenderId);
                                messageTextBody.put("to",messageRecieverId);
                                messageTextBody.put("messageId",messagePushId);
                                messageTextBody.put("time",saveCurrentTime);
                                messageTextBody.put("date",saveCurrentDate);


                                Map messageBodyDetails = new HashMap();
                                messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageTextBody);
                                messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageTextBody);

                                RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful())
                                        {

                                            Map map = new HashMap();
                                            map.put("new","yes");
                                            NotificationRef.child(messageRecieverId).child(messageRecieverId).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        loadingBar.dismiss();
                                                        Toast.makeText(ChatActivity.this,"Message Sent successfully",Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        loadingBar.dismiss();
                                                        Toast.makeText(ChatActivity.this,"Error",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                        else
                                        {
                                            loadingBar.dismiss();
                                            Toast.makeText(ChatActivity.this,"Error",Toast.LENGTH_SHORT).show();
                                        }
                                        MessageInputText.setText("");
                                    }
                                });



                            }
                        });
                    }
                });



            }
            else if(checker.equals("image"))
            {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
                final String[] fileUrl = new String[1];
                final String messageSenderRef = "Messages/"+messageSenderId+"/"+messageRecieverId;
                final String messageReceiverRef = "Messages/"+messageRecieverId+"/"+messageSenderId;

                final DatabaseReference userMessageKeyRef = RootRef.child("Messages").child(messageSenderId).child(messageRecieverId).push();

                final String messagePushId = userMessageKeyRef.getKey();

                final StorageReference filePath = storageReference.child(messagePushId +"."+"jpg");

                //final UploadTask uploadTask = filePath.putFile(fileUri);

                UploadTask uploadTask1 = (UploadTask) filePath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Uri downloadUrl=uri;
                                fileUrl[0] =downloadUrl.toString();

                                Map messageTextBody = new HashMap();
                                messageTextBody.put("message",fileUrl[0] );
                                messageTextBody.put("name",fileUri.getLastPathSegment());
                                messageTextBody.put("type",checker);
                                messageTextBody.put("from",messageSenderId);
                                messageTextBody.put("to",messageRecieverId);
                                messageTextBody.put("messageId",messagePushId);
                                messageTextBody.put("time",saveCurrentTime);
                                messageTextBody.put("date",saveCurrentDate);


                                Map messageBodyDetails = new HashMap();
                                messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageTextBody);
                                messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageTextBody);

                                RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful())
                                        {

                                            Map map = new HashMap();
                                            map.put("new","yes");
                                            NotificationRef.child(messageRecieverId).child(messageRecieverId).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        loadingBar.dismiss();
                                                        Toast.makeText(ChatActivity.this,"Message Sent successfully",Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        loadingBar.dismiss();
                                                        Toast.makeText(ChatActivity.this,"Error",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                        else
                                        {
                                            loadingBar.dismiss();
                                            Toast.makeText(ChatActivity.this,"Error",Toast.LENGTH_SHORT).show();
                                        }
                                        MessageInputText.setText("");
                                    }
                                });



                            }
                        });
                    }
                });



            }
            else
            {
                loadingBar.dismiss();
                Toast.makeText(this,"Nothing selected",Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();


    }

    private void SendMessage() {

        String messageText = MessageInputText.getText().toString();
        if(TextUtils.isEmpty(messageText))
        {
            Toast.makeText(this,"first write your message ..",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String messageSenderRef = "Messages/"+messageSenderId+"/"+messageRecieverId;
            String messageReceiverRef = "Messages/"+messageRecieverId+"/"+messageSenderId;

            DatabaseReference userMessageKeyRef = RootRef.child("Messages").child(messageSenderId).child(messageRecieverId).push();

            String messagePushId = userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message",messageText);
            messageTextBody.put("type","text");
            messageTextBody.put("from",messageSenderId);

            final HashMap<String,String> chatNotificationMap = new HashMap<>();
            chatNotificationMap.put("from",messageSenderId);
            chatNotificationMap.put("type","request");



            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageTextBody);
            messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageTextBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful())
                    {
                        SendNotification();

                        Map map = new HashMap();
                        map.put("new","yes");
                        NotificationRef.child(messageRecieverId).child(messageSenderId).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful())
                                {

                                }
                                else
                                {

                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(ChatActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                    MessageInputText.setText("");
                }
            });




        }
    }

    private void SendNotification() {

        TOPIC = "/topics/"+messageRecieverId; //topic has to match what the receiver subscribed to
        NOTIFICATION_TITLE = "New Message";
        NOTIFICATION_MESSAGE = "You have a New Message";

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChatActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }



    private void InitializeControllers() {


        ChatToolBar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(ChatToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_chat_bar,null);
        actionBar.setCustomView(actionBarView);


        userImage = (CircleImageView) findViewById(R.id.custom_profile_image);
        userName = (TextView) findViewById(R.id.custom_profile_name);
        userLastSeen = (TextView) findViewById(R.id.custom_user_last_seen);

        SendMessageButton = (ImageButton) findViewById(R.id.send_message_btn);
        SendFilesButton = (ImageButton) findViewById(R.id.send_files_btn);
        MessageInputText = (EditText) findViewById(R.id.input_message);


        userMessageList = (RecyclerView) findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
        messageAdapter = new MessageAdapter(messageList);
        userMessageList.setLayoutManager(linearLayoutManager);
        userMessageList.setAdapter(messageAdapter);

        loadingBar = new ProgressDialog(this);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM  dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());




    }

    private void DisplayLastSeen()
    {
        RootRef.child("Users").child(messageRecieverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("userState").hasChild("state"))
                {
                    String state = snapshot.child("userState").child("state").getValue().toString();
                    String date = snapshot.child("userState").child("date").getValue().toString();
                    String time = snapshot.child("userState").child("time").getValue().toString();

                    if(state.equals("online"))
                    {
                        userLastSeen.setText("online");
                    }
                    else if(state.equals("offline"))
                    {
                        userLastSeen.setText("Last Seen: "+ date +" "+time);
                    }
                }
                else
                {
                    userLastSeen.setText("offline");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

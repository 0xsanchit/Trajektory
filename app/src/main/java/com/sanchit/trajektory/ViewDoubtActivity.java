package com.sanchit.trajektory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ViewDoubtActivity extends AppCompatActivity {

    private TextView doubtTitle,doubtDescription,doubtStatus;
    private RecyclerView recyclerView;
    private Button button,button1;
    private String doubtId,askedBy;
    private DatabaseReference databaseReference,databaseReference1;
    private String key2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doubt);

        doubtTitle = findViewById(R.id.view_doubt_title);
        doubtDescription = findViewById(R.id.view_doubt_description);
        doubtStatus = findViewById(R.id.view_doubt_status);
        button = findViewById(R.id.view_doubt_takeup);
        button1 = findViewById(R.id.view_doubt_followup);

        recyclerView = findViewById(R.id.view_doubt_images);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        doubtId = getIntent().getExtras().get("doubtId").toString();
        askedBy = getIntent().getExtras().get("askedBy").toString();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Doubts").child(askedBy).child(doubtId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    try {
                        doubtTitle.setText(dataSnapshot.child("Title").getValue().toString());
                        doubtDescription.setText(dataSnapshot.child("Description").getValue().toString());
                        doubtStatus.setText(dataSnapshot.child("Status").getValue().toString());
                        if(!dataSnapshot.child("Status").getValue().toString().equals("Pending"))
                        {
                            button.setVisibility(View.INVISIBLE);
                        }
                        if(dataSnapshot.child("Status").getValue().toString().equals("Solved"))
                        {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Users").child(askedBy).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Intent intent = new Intent(ViewDoubtActivity.this, DoubtChatActivity.class);
                            intent.putExtra("visit_doubt_id", doubtId);
                            intent.putExtra("visit_user_id", askedBy);
                            intent.putExtra("visit_user_name", dataSnapshot.child("name").getValue().toString());
                            intent.putExtra("visit_image", dataSnapshot.child("image").getValue().toString());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().child("PendingDoubts").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(doubtId))
                        {

                            FirebaseDatabase.getInstance().getReference().child("Users").child(askedBy).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                                    FirebaseDatabase.getInstance().getReference().child("Doubts").child(askedBy).child(doubtId).child("Status").setValue("Solving");
                                    FirebaseDatabase.getInstance().getReference().child("PendingDoubts").child(doubtId).child("Status").setValue("Solving");
                                    final Map<String,Object> map = new HashMap<String,Object>();
                                    map.put("AskedBy",askedBy);
                                    map.put("doubtId",doubtId);
                                    map.put("SolvedBy",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    databaseReference1=FirebaseDatabase.getInstance().getReference().child("SolvingDoubts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(doubtId);
                                    FirebaseDatabase.getInstance().getReference().child("Doubts").child(askedBy).child(doubtId).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                            if(!dataSnapshot1.child("Status").getValue().toString().equals("Pending"))
                                            {
                                                button.setVisibility(View.INVISIBLE);
                                            }
                                            map.put("Subject",dataSnapshot1.child("Subject").getValue().toString());
                                            map.put("Status",dataSnapshot1.child("Status").getValue().toString());
                                            map.put("Title",dataSnapshot1.child("Title").getValue().toString());
                                            map.put("Description",dataSnapshot1.child("Description").getValue().toString());
                                            map.put("Images",dataSnapshot1.child("Images").getValue());

                                            databaseReference1.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    FirebaseDatabase.getInstance().getReference().child("Doubts").child(askedBy).child(doubtId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseDatabase.getInstance().getReference().child("PendingDoubts").child(doubtId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Intent intent = new Intent(ViewDoubtActivity.this,DoubtChatActivity.class);
                                                                    intent.putExtra("visit_doubt_id",doubtId);
                                                                    intent.putExtra("visit_user_id",askedBy);
                                                                    intent.putExtra("visit_user_name",dataSnapshot.child("name").getValue().toString());
                                                                    intent.putExtra("visit_image",dataSnapshot.child("image").getValue().toString());
                                                                    startActivity(intent);
                                                                }
                                                            });
                                                        }
                                                    });


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
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

        databaseReference=databaseReference.child("Images");

        FirebaseRecyclerOptions<Books> options =
                new FirebaseRecyclerOptions.Builder<Books>()
                        .setQuery(databaseReference, Books.class)
                        .build();

        FirebaseRecyclerAdapter<Books, PhotoViewHolder> adapter =
                new FirebaseRecyclerAdapter<Books, PhotoViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final PhotoViewHolder productViewHolder, final int i, @NonNull final Books model) {


                        key2=getRef(i).getKey();
                        String url;
                        if(key2!=null) {
                            databaseReference.child(key2).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Imgs img = dataSnapshot.getValue(Imgs.class);
                                    if(img!=null)
                                    {
                                        Glide.with(ViewDoubtActivity.this).load(img.getImg()).into(productViewHolder.imageView);}
                                    // productViewHolder.photoid.setText(key2);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }


                        //productViewHolder.txtProductDescription.setText(model.getDescription());
                        //productViewHolder.txtProductPrice.setText("Price = Rs. " + model.getPrice());
                        //Picasso.get().load(model.getUrl()).into(productViewHolder.imageView);

                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseReference.child(getRef(i).getKey()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Imgs img = dataSnapshot.getValue(Imgs.class);
                                        if(img!=null)
                                        {
                                            //Glide.with(ViewDoubtActivity.this).load(img.getImg()).into(productViewHolder.imageView);
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(img.getImg()));
                                            productViewHolder.itemView.getContext().startActivity(intent);
                                        }
                                        // productViewHolder.photoid.setText(key2);


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });

                        //    Intent intent = new Intent(RentHomeActivity.this,ProductDetailsActivity.class);
                        //   intent.putExtra("pId",id);
                        //  startActivity(intent);
                        // Fragment newFragment = new EditBookFragment();

                        //Bundle bundle = new Bundle();
                        //bundle.putString("key", id);  // Key, value
                        //newFragment.setArguments(bundle);
                        //FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                        //transaction.replace(R.id.fragment_container, newFragment);
                        //  transaction.addToBackStack(null);

// Commit the transaction
                        //transaction.commit();



                        //   }
                        // });

                    }

                    @NonNull
                    @Override
                    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_layout,parent,false);
                        PhotoViewHolder holder = new PhotoViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }
}
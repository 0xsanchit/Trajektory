package com.sanchit.trajektory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DoubtAdapter1 extends RecyclerView.Adapter<DoubtAdapter1.DoubtViewHolder> {

    private List<Doubts> userdoubts;
    private DatabaseReference databaseReference;
     String msgreceiverId;
     String msgreceiverName;
     String msgreceiverImage;

    public DoubtAdapter1(List<Doubts> userdoubts) {
        this.userdoubts = userdoubts;
    }

    public class DoubtViewHolder extends RecyclerView.ViewHolder {

        public TextView titledoubt, description,status,subject;
        public ImageView imageView;
        //public ItemClassListener listener;

        public DoubtViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.doubt_image);
            titledoubt = (TextView) itemView.findViewById(R.id.doubt_title);
            description = (TextView)itemView.findViewById(R.id.doubt_description);
            status = (TextView) itemView.findViewById(R.id.doubt_status);
            subject = (TextView) itemView.findViewById(R.id.doubt_subject);
        }
    }

    @NonNull
    @Override
    public DoubtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doubt_layout,parent,false);

        return new DoubtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DoubtViewHolder holder, int position) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final Doubts doubts = userdoubts.get(position);
        holder.titledoubt.setText(doubts.getTitle());
        holder.description.setText(doubts.getDescription());
        holder.status.setText(doubts.getStatus());
        holder.subject.setText(doubts.getSubject());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(doubts.getStatus().equals("Pending"))
                {
                    Toast.makeText(holder.itemView.getContext(),"The Problem is yet to be solved",Toast.LENGTH_SHORT).show();
                }
                else*/
                {
                   /* msgreceiverId=doubts.getAskedBy();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(msgreceiverId);


                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            msgreceiverName =dataSnapshot.child("name").getValue().toString();
                            msgreceiverImage =dataSnapshot.child("image").getValue().toString();
                            Intent intent = new Intent(holder.itemView.getContext(),DoubtChatActivity.class);
                            intent.putExtra("visit_user_id",msgreceiverId);
                            intent.putExtra("visit_user_name", msgreceiverName);
                            intent.putExtra("visit_image", msgreceiverImage);
                            intent.putExtra("visit_doubt_id",doubts.getDoubtId());
                            holder.itemView.getContext().startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/
                    Intent intent = new Intent(holder.itemView.getContext(),ViewDoubtActivity.class);
                    intent.putExtra("doubtId",doubts.getDoubtId());
                    intent.putExtra("askedBy",doubts.getAskedBy());
                    holder.itemView.getContext().startActivity(intent);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return userdoubts.size();
    }


}

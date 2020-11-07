package com.sanchit.trajektory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import static android.app.Activity.RESULT_OK;


public class AskDoubtFragment extends Fragment {

    Button choose,upload;
    TextView alert;
    EditText title,description;
    private AdView mAdView;


    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private static final int PICK_IMAGE=1   ;
    private Uri ImageUri;
    private ProgressDialog progressDialog;
    private int upload_count=0;
    private RadioButton p,c,m,o;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,databaseReference1;
    String titleofdoubt,descriptionofdoubt,subjectofdoubt,key;
    private InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_doubt, container, false);

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-4682278563427277/1780286340");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mAdView = view.findViewById(R.id.adViewAskDoubt);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        choose = (Button) view.findViewById(R.id.select_images_to_ask);
        upload = (Button) view.findViewById(R.id.submit_doubt_button);
        title = (EditText)view.findViewById(R.id.title_doubt);
        description = (EditText)view.findViewById(R.id.description_doubt);
        p = (RadioButton) view.findViewById(R.id.PhysicsDoubt);
        c = (RadioButton) view.findViewById(R.id.ChemistryDoubt);
        m = (RadioButton) view.findViewById(R.id.MathsDoubt);
        o = (RadioButton) view.findViewById(R.id.OtherDoubts);
        upload.setVisibility(View.VISIBLE);

        //alert = (TextView) view.findViewById(R.id.alert);
        //bookname=(EditText) view.findViewById(R.id.book_name);
        //progressDialog= new  ProgressDialog(getActivity());
        //progressDialog.setMessage("Image Uploading Please Wait ....");
        firebaseAuth=FirebaseAuth.getInstance();
        //bname = bookname.getText().toString();

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    upload.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(intent, PICK_IMAGE);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upload.setVisibility(View.INVISIBLE);
                // bname = bookname.getText().toString();
                titleofdoubt=title.getText().toString();
                descriptionofdoubt=description.getText().toString();

                if(p.isChecked())
                {
                    subjectofdoubt = "Physics";
                }
                else if(c.isChecked())
                {
                    subjectofdoubt = "Chemistry";
                }
                else if(m.isChecked())
                {
                    subjectofdoubt = "Maths";
                }
                else
                {
                    subjectofdoubt = "Others";
                }



                if (titleofdoubt.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter a Title For Your Doubt", Toast.LENGTH_LONG).show();
                    upload.setVisibility(View.VISIBLE);
                }
                else if(descriptionofdoubt.isEmpty())
                {
                    Toast.makeText(getActivity(), "Please Enter a Doubt Description", Toast.LENGTH_LONG).show();
                    upload.setVisibility(View.VISIBLE);
                }
                else
                {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Doubts").child(firebaseAuth.getCurrentUser().getUid());
                    key=databaseReference.push().getKey();
                    Map map = new HashMap<>();
                    map.put("Title",titleofdoubt);
                    map.put("Description",descriptionofdoubt);
                    map.put("Status","Pending");
                    map.put("Subject",subjectofdoubt);
                    map.put("AskedBy",FirebaseAuth.getInstance().getCurrentUser().getUid());
                    map.put("doubtId",key);

                    databaseReference.child(key).updateChildren(map);
                    title.setText("");
                    description.setText("");
                    databaseReference = databaseReference.child(key).child("Images");

                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("PendingDoubts").child(key);
                    databaseReference1.updateChildren(map);
                    databaseReference1 = databaseReference1.child("Images");

                    //progressDialog.show();
                    //key = databaseReference.push().getKey();
                    //databaseReference = databaseReference.child(bname);
                    StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder").child(firebaseAuth.getCurrentUser().getUid()).child(key);
                    for (upload_count = 0; upload_count < ImageList.size(); upload_count++) {
                        Toast.makeText(getActivity(), "Uploading " + upload_count + " / " + ImageList.size(), Toast.LENGTH_SHORT).show();
                        //progressDialog.show();
                        Uri IndividualImage = ImageList.get(upload_count);
                        final StorageReference ImageName = ImageFolder.child("Image" + IndividualImage.getLastPathSegment());

                        ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String url = String.valueOf(uri);
                                        StoreLink(url, upload_count);

                                    }
                                });
                            }
                        });

                        if(upload_count==ImageList.size()-2)
                        {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    }
                    //progressDialog.dismiss();
                    //Log.d("MyTag","Hello");
                }


            }
        });
        return view;
    }
    private void StoreLink(String url,int x) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Img",url);
        databaseReference.push().setValue(hashMap);
        databaseReference1.push().setValue(hashMap);
        //progressDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE)
        {
            if(resultCode==RESULT_OK)
            {
                if(data.getClipData()!=null)
                {
                    int countClipData = data.getClipData().getItemCount();
                    int currentImageselect=0;
                    while(currentImageselect<countClipData)
                    {
                        ImageUri=data.getClipData().getItemAt(currentImageselect).getUri();
                        /*String filePath = SiliCompressor.with(getContext()).compress(ImageUri.getPath(), new File(ImageUri.getPath()));

                            Uri finalUri = Uri.fromFile(new File(filePath));*/
                        /*Bitmap bmp = null;
                        try {
                            bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),ImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                        byte[] data1 = baos.toByteArray();

                        String s = null;
                        try {
                            s = new String(data1, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Uri uri = Uri.parse(s);*/


                        ImageList.add(ImageUri);

                        currentImageselect++;
                    }
                    //alert.setVisibility(View.VISIBLE);
                    //alert.setText("You Have Selected " + ImageList.size() + "Images . Please keep the app open for some time after clicking the upload button");
                    //choose.setVisibility(View.GONE);
                }
                else if(data.getData()!=null)
                {
                    Uri imgUri = data.getData();

                        ImageList.add(imgUri);


                }
                else
                {
                    Toast.makeText(getActivity(),"Please select Multiple Images",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        upload.setVisibility(View.VISIBLE);
    }
}
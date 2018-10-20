package com.blazingapps.asus.radiomanger;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int STARTUPTIMEDIFF = 30;
    ListView listView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        listView = findViewById(R.id.list_view);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("songreq");


        final ArrayList<Song> arrayList = new ArrayList<>();
        /*arrayList.add(new Song("1001","Dole Dole","SomeSong"));
        arrayList.add(new Song("1002","ma tuje salam","Patriotic"));
        arrayList.add(new Song("1003","Dhoom",""));*/

        final MyAdapter adapter = new MyAdapter(this,R.layout.list_item,arrayList);
        listView.setAdapter(adapter);

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please wait...");
        alertDialogBuilder.setCancelable(false);

        alertDialog = alertDialogBuilder.create();
        if (!alertDialog.isShowing())
        {
            alertDialog.show();
        }

        //final Long startuptime = System.currentTimeMillis()/1000;

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String key = dataSnapshot.getKey();
                String id = dataSnapshot.child("id").getValue().toString();
                String title = dataSnapshot.child("title").getValue().toString();
                String moreinfo = dataSnapshot.child("moreinfo").getValue().toString();
                String used=null;
                try {
                    used = dataSnapshot.child("used").getValue().toString();
                    if (used.equals("false")) {

                        adapter.add(new Song(id, title, moreinfo, key));
                        listView.setSelection(adapter.getCount() - 1);
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                    }

                }catch (Exception e){

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Authenticated",Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }
    }
}

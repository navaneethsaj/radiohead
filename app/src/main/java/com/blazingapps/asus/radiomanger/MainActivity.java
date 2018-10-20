package com.blazingapps.asus.radiomanger;

import android.app.AlertDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int STARTUPTIMEDIFF = 30;
    ListView listView;
    private DatabaseReference mDatabase;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("songreq");


        final ArrayList<Song> arrayList = new ArrayList<>();
        /*arrayList.add(new Song("1001","Dole Dole","SomeSong"));
        arrayList.add(new Song("1002","ma tuje salam","Patriotic"));
        arrayList.add(new Song("1003","Dhoom",""));*/

        final MyAdapter adapter = new MyAdapter(this,R.layout.list_item,arrayList);
        listView.setAdapter(adapter);

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("        Please wait...");
        alertDialogBuilder.setCancelable(false);

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        final Long startuptime = System.currentTimeMillis()/1000;

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Long diff = (System.currentTimeMillis()/1000) - startuptime;
                Log.d("diff", String.valueOf(diff));
                if (diff > STARTUPTIMEDIFF){
                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String id = dataSnapshot.child("id").getValue().toString();
                String title = dataSnapshot.child("title").getValue().toString();
                String moreinfo = dataSnapshot.child("moreinfo").getValue().toString();
                adapter.add(new Song(id,title,moreinfo));
                listView.setSelection(adapter.getCount() - 1);
                alertDialog.dismiss();
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
}

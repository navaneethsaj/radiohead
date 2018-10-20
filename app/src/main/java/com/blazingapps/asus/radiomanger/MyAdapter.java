package com.blazingapps.asus.radiomanger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Song> {
    ArrayList<Song> arrayList;
    Context context;
    FirebaseDatabase database;
    DatabaseReference myRef ;
    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Song> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;

        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("songreq");
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item, null);
        TextView id = (TextView) v.findViewById(R.id.id);
        TextView title = v.findViewById(R.id.title);
        TextView moreinfo = v.findViewById(R.id.moreinfo);
        Button removebutton = v.findViewById(R.id.removebutton);

        final String key = arrayList.get(position).getKey();

        id.setText(arrayList.get(position).getId());
        title.setText(arrayList.get(position).getTitle());
        moreinfo.setText(arrayList.get(position).getMoreinfo());

        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.child(key).child("used").setValue(true);

                arrayList.remove(position);
                MyAdapter.this.notifyDataSetChanged();
            }
        });

        return v;
    }
}

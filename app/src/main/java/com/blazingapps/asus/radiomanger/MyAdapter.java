package com.blazingapps.asus.radiomanger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Song> {
    ArrayList<Song> arrayList;
    Context context;
    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Song> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item, null);
        TextView id = (TextView) v.findViewById(R.id.id);
        TextView title = v.findViewById(R.id.title);
        TextView moreinfo = v.findViewById(R.id.moreinfo);

        id.setText(arrayList.get(position).getId());
        title.setText(arrayList.get(position).getTitle());
        moreinfo.setText(arrayList.get(position).getMoreinfo());

        return v;
    }
}

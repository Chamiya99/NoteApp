package com.example.noteappv4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<NoteObj> {

    private Context context;
    private int resource;
    List<NoteObj> todos;

    NoteAdapter(Context context, int resource, List<NoteObj> todos){
        super(context,resource,todos);
        this.context = context;
        this.resource = resource;
        this.todos = todos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        TextView title = row.findViewById(R.id.title);
        TextView description = row.findViewById(R.id.description);


        // todos [obj1,obj2,obj3]
        NoteObj noteObj = todos.get(position);
        title.setText(noteObj.getTitle());
        description.setText(noteObj.getDescription());

        return row;
    }
}

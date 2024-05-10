package com.example.noteappv4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNotes extends AppCompatActivity {

    private EditText title,des;
    private Button edit;
    private NoteDB noteDB;
    private Context context;
    private Long updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);

        context = this;
        noteDB = new NoteDB(context);

        title = findViewById(R.id.editToDoTextTitle);
        des = findViewById(R.id.editToDoTextDescription);
        edit = findViewById(R.id.buttonEdit);

        final String id = getIntent().getStringExtra("id");
        NoteObj todo = noteDB.getSingleToDo(Integer.parseInt(id));

        title.setText(todo.getTitle());
        des.setText(todo.getDescription());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = title.getText().toString();
                String decText = des.getText().toString();
                updateDate = System.currentTimeMillis();

                NoteObj noteObj = new NoteObj(Integer.parseInt(id),titleText,decText,updateDate,0);
                int state = noteDB.updateSingleToDo(noteObj);
                System.out.println(state);
                startActivity(new Intent(context,MainActivity.class));
            }
        });
    }
}

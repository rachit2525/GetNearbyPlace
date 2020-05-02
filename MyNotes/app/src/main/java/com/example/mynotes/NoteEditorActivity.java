package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import static android.media.CamcorderProfile.get;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId;
    EditText titleEditText;
    //ArrayList<String> titles=new ArrayList<>();
    //ArrayAdapter titleArrayAdapter;
    public void searchNearby(View view) {
        String titleString = titleEditText.getText().toString();
        if(titleString.length()==0)
        {
            Toast.makeText(this,"Give some title related to items you need to look for in the note to search it nearby you!",Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
            intent.putExtra("title",titleString);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText=findViewById(R.id.editText);
        titleEditText=findViewById(R.id.titleEditText);
        Button button=findViewById(R.id.button);
        Intent intent = getIntent();
        noteId=intent.getIntExtra("noteId",-1);

        if(noteId!=-1)
        {
            editText.setText(MainActivity.notes.get(noteId));
        }
        else {
            MainActivity.notes.add("");
            noteId=MainActivity.notes.size()-1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MainActivity.notes.set(noteId,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);

                sharedPreferences.edit().putStringSet("notes",set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    // instantiate member variables
    EditText editItem;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // define each member variable
        editItem = findViewById(R.id.editItem); // text in edit box
        buttonSave = findViewById(R.id.buttonSave); // save button

        getSupportActionBar().setTitle("Edit To Do List Item");

        // sets edit text box to clicked text
        // get handle on data that we passed in with getIntent()
        editItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        // saves edited text when button is clicked
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // want to return back to MainActivity
                Intent intent = new Intent(); // intent holding results of what was modified
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString()); // pass data
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                setResult(RESULT_OK, intent); // set result of intent
                finish(); // finish activity, close screen, go back to main
            }
        });
    }
}
package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText editItem;
    Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editItem = findViewById(R.id.editItem);
        buttonSave = findViewById(R.id.buttonSave);

        getSupportActionBar().setTitle("Edit To Do List Item");

        // sets edit text box to clicked text
        editItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        // saves edited text when button is clicked
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // want to return back to MainActivity

                Intent intent = new Intent();
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                setResult(RESULT_OK, intent);
                finish(); // finish activity, close screen, go back to main
            }
        });
    }
}
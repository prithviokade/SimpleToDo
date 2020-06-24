package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20; // this value is arbitrary because there's only one activity

    // will hold all todolist items
    List<String> items;

    // instantiate member variables for each item (get reference of each view)
    Button buttonAdd;
    EditText textAdd;
    RecyclerView todoItems;

    // Recycler View's Adapter to handle the data collection and bind it to the view
    ItemsAdapter itemsAdapter;

    // main activity has been created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // displays layout from activity_main.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // defines each of the member variables; each of these has methods that we can use to modify
        buttonAdd = findViewById(R.id.buttonAdd);
        textAdd = findViewById(R.id.textAdd);
        todoItems = findViewById(R.id.todoItems); // recycler view items

        loadItems(); // loads all todolist items into items

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                // Log.d("MainActivity", "Single click at position" + position);

                // create new activity --> create new intent (look at this more)
                Intent i = new Intent(MainActivity.this, EditActivity.class);

                // pass data into intent with putExtra(key, value)
                i.putExtra(KEY_ITEM_TEXT, items.get(position)); // pass the data being edited
                i.putExtra(KEY_ITEM_POSITION, position); // pass the position being edited to know what has been updated

                // display activity; we expect result (updated item)
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) { // position is the list item that's been long pressed
                items.remove(position); // remove item at that position
                itemsAdapter.notifyItemRemoved(position); // notify adapter
                Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        // construct adapter
        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        todoItems.setAdapter(itemsAdapter); // sets the adapter on the recycler view
        todoItems.setLayoutManager(new LinearLayoutManager(this)); // set layout managed on the recycler view (makes it vertical)

        // check if someone tapped on add button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = textAdd.getText().toString(); // gets the text in the edit box
                items.add(todoItem); // adds the text to the todolist items
                itemsAdapter.notifyItemInserted(items.size() - 1); // notifies adapter of change
                textAdd.setText(""); // clears the text in edit box
                Toast.makeText(getApplicationContext(),"Item added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    // handle result of edit activity; how do you know we want to override onActivityResult?
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // handle result of edit
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            String itemText = data.getStringExtra(KEY_ITEM_TEXT); // edited text
            int position = data.getExtras().getInt(KEY_ITEM_POSITION); // clicked list position
            items.set(position, itemText); // set clicked list position to edited text
            itemsAdapter.notifyItemChanged(position);
            saveItems(); // save to data (whenever removing, inserting, changing)
            Toast.makeText(getApplicationContext(), "Item updated", Toast.LENGTH_SHORT).show();

        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult()");
        }
    }

    // data file holds todo list items
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // read data file, load contents to items
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // write data file, save items to file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}

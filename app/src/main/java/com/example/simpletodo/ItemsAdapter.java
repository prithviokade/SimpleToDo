package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// need to define view holder first
// displays data from model into a row of recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnClickListener {
        void onItemClicked(int position); // tells MainActivity which list item was pressed
    }

    // why? define interface in adapter that MainActivity will implement
    public interface OnLongClickListener {
        void onItemLongClicked(int position); // tells MainActivity which list item was long pressed
    }

    // member variables
    List <String> items;
    OnClickListener clickListener;
    OnLongClickListener longClickListener;

    // constructor; need data about the model (todolist items) so first parameter is list of strings
    public ItemsAdapter(List <String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    // creates each view and wrap it inside a view holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // uses layout inflater to inflate view
        // simple_list_item_1 is a builtin for the xml of view we're creating
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,false);
        // wraps the created view inside the view holder
        return new ViewHolder(todoView);
    }

    // takes data at position and puts it into (binds it to) view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position); // get data/item at position
        holder.bind(item); // bind item to view holder
    }

    // items available in the data
    @Override
    public int getItemCount() {
        return items.size();
    }

    // holds onto to text view, populate data from our model into the view
    // container to provide access to views representing each row/element of todolist
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview = itemView.findViewById(android.R.id.text1); // type and id of this is based on sample_list_item_1
        }

        // update the view inside of the view holder
        public void bind(String item) {
            textview.setText(item);

            // for editing items in list if clicked
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // notify listener what position was clicked
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });

            // for removing items in list if long clicked
            textview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // notify listener what was long clicked (can't directly remove)
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    // signal that the callback is consuming the long click, when is this bool used?
                    return true;
                }
            });
        }
    }

}






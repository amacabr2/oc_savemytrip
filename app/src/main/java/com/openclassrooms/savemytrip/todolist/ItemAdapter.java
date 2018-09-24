package com.openclassrooms.savemytrip.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.savemytrip.R;
import com.openclassrooms.savemytrip.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    public interface Listener {
        void onClickDeleteButton(int position);
    }

    private final Listener callback;
    private List<Item> items;

    public ItemAdapter(Listener callback) {
        this.items = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_todo_list_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int position) {
        viewHolder.updateWithItem(this.items.get(position), this.callback);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public Item getItem(int position){
        return this.items.get(position);
    }

    public void updateData(List<Item> items){
        this.items = items;
        this.notifyDataSetChanged();
    }
}

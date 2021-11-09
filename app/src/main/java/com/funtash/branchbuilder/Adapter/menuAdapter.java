package com.funtash.branchbuilder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.funtash.branchbuilder.R;

import java.util.ArrayList;

public class menuAdapter extends RecyclerView.Adapter<menuAdapter.ViewMenu> {
    public menuAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    Context context;
    ArrayList<String>  arrayList;

    @NonNull
    @Override
    public ViewMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.menu_adapter_layout,parent,false);
        return new ViewMenu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMenu holder, int position) {
        holder.category.setText(arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        try {
            return arrayList.size();
        } catch (NullPointerException e) {

        }
        return arrayList.size()+1;

    }

    public class ViewMenu extends RecyclerView.ViewHolder{
        AppCompatTextView category;
        AppCompatCheckBox checkBox;
        public ViewMenu(@NonNull View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.category);
            checkBox=itemView.findViewById(R.id.checkbox);
        }
    }
}

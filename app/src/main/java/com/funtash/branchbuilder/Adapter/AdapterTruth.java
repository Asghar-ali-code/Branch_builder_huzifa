package com.funtash.branchbuilder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.funtash.branchbuilder.Model.Branches;
import com.funtash.branchbuilder.R;

public class AdapterTruth extends RecyclerView.Adapter<AdapterTruth.TruthLay> {

    public AdapterTruth(Branches branches, Context context) {
        this.branches = branches;
        this.context = context;
    }

    Branches branches;
    Context context;

    @NonNull
    @Override
    public TruthLay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.truth_adpter_layout, parent, false);

        return new TruthLay(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruthLay holder, int position) {
        Branches.Truth truthObj = branches.truths.get(position);
        holder.title.setText(truthObj.title);


    }

    @Override
    public int getItemCount() {
        return branches.truths.size();
    }

    public class TruthLay extends RecyclerView.ViewHolder {
        TextView title;

        public TruthLay(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}

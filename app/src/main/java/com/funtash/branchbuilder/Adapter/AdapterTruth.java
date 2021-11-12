package com.funtash.branchbuilder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.funtash.branchbuilder.Activities.TruthDeatilsActivity;
import com.funtash.branchbuilder.Model.AppValues;
import com.funtash.branchbuilder.Model.Branches;
import com.funtash.branchbuilder.Model.DeleteTruth;
import com.funtash.branchbuilder.R;
import com.funtash.branchbuilder.Response.ResponseApis;

import java.util.ArrayList;
import java.util.List;

import it.mike5v.viewmoretextview.ViewMoreTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTruth extends RecyclerView.Adapter<AdapterTruth.TruthLay> {

    public AdapterTruth(List<Branches.Truth> branches, Context context, String auth) {
        this.branches = branches;
        this.context = context;
        this.auth = auth;
    }

    public AdapterTruth() {
    }

    List<Branches.Truth> branches;
    Context context;
    String auth;

    @NonNull
    @Override
    public TruthLay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.truth_adpter_layout, parent, false);

        return new TruthLay(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruthLay holder, @SuppressLint("RecyclerView") int position) {
        Branches.Truth truthObj = branches.get(position);
        holder.title.setText(truthObj.title);
        holder.viewMore.setText(truthObj.body);
        holder.viewMore.setEllipsizedText("view more")
                .setIsExpanded(false)
                .setVisibleLines(2)
                .setEllipsizedTextColor(ContextCompat
                        .getColor(context, R.color.btnBgColor));
        holder.title.setOnClickListener(view -> {
            Intent intent = new Intent(context, TruthDeatilsActivity.class);
            intent.putExtra("scenario", 1);
            intent.putExtra("truth", truthObj.category);
            Log.d("dhdhhdhd", "onBindViewHolder: "+truthObj.id);
            intent.putExtra("id",truthObj.id);
            AppValues.setTruthTitle(truthObj.title);
            AppValues.setTruthBody(truthObj.body);
            context.startActivity(intent);


        });
        holder.viewMore.setOnClickListener(view -> {
            Intent intent = new Intent(context, TruthDeatilsActivity.class);
            intent.putExtra("scenario", 1);
            intent.putExtra("truth", truthObj.category);
            intent.putExtra("id",truthObj.id);
            AppValues.setTruthTitle(truthObj.title);
            AppValues.setTruthBody(truthObj.body);
            context.startActivity(intent);

        });
        /*holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.TimePicker)
                        .setTitle("Remove Truth")
                        .setMessage("Are you sure you wan to Remove this Truth")
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeTruth(auth,truthObj.id+"",position);
                            }
                        })
                        .setNegativeButton("CANCEL", null);
                builder.show();


            }
        });*/


    }

    @Override
    public int getItemCount() {
        if(branches!=null)
        {
            return branches.size();
        }
        return 0;
    }

    public class TruthLay extends RecyclerView.ViewHolder {
        TextView title;
        ConstraintLayout con;
        ViewMoreTextView viewMore;


        public TruthLay(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            con = itemView.findViewById(R.id.con);

            viewMore = itemView.findViewById(R.id.viewMore);

        }
    }

    public void filterlist (List<Branches.Truth> filterlist)
    {
        branches=filterlist;
        notifyDataSetChanged();
    }
}

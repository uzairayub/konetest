package com.tsl.elevator.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tsl.elevator.R;
import com.tsl.elevator.models.resources.BuildingsItem;
import com.tsl.elevator.models.resources.GroupsItem;
import com.tsl.elevator.ui.GroupsDialog;

import java.util.List;

public class KoneResAdapters extends RecyclerView.Adapter<KoneResAdapters.MyViewHolder> {
    private List<BuildingsItem> resList;
    private KoneResCallback callback;

    public KoneResAdapters(List<BuildingsItem> resList, KoneResCallback callback) {
        this.resList = resList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kone_res, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvBuildingId.setText(resList.get(position).getId());
        holder.tvTotalGroups.setText(String.valueOf(resList.get(position).getGroups().size()));
        holder.tvName.setText(resList.get(position).getName());

        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.parseColor("#e5e5e5"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onResourceClicked(resList.get(position).getGroups());
            }
        });
    }

    @Override
    public int getItemCount() {
        return resList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBuildingId, tvTotalGroups, tvName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBuildingId = itemView.findViewById(R.id.tvBuildingId);
            tvTotalGroups = itemView.findViewById(R.id.tvTotalGroups);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }

    public interface KoneResCallback {
        void onResourceClicked(List<GroupsItem> groupsItem);
    }

}

package com.tsl.elevator.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tsl.elevator.R;
import com.tsl.elevator.models.resources.GroupsItem;

import java.util.List;

public class KoneGroupsAdapters extends RecyclerView.Adapter<KoneGroupsAdapters.MyViewHolder> {
    private List<GroupsItem> groupList;
    private KoneGroupCallback callback;

    public KoneGroupsAdapters(List<GroupsItem> resList, KoneGroupCallback callback) {
        this.groupList = resList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kone_group, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvName.setText(groupList.get(position).getName());
        holder.tvGroupID.setText(groupList.get(position).getId());

        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.parseColor("#e5e5e5"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onGroupSelected(groupList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvGroupID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvGroupID = itemView.findViewById(R.id.tvGroupID);
        }
    }

    public interface KoneGroupCallback {
        void onGroupSelected(String buildAndGroupId);
    }

}

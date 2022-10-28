package com.tsl.elevator.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tsl.elevator.adapters.KoneGroupsAdapters;
import com.tsl.elevator.databinding.DialogGroupsBinding;
import com.tsl.elevator.managers.CommonCommandsDataManager;
import com.tsl.elevator.models.resources.GroupsItem;
import com.tsl.elevator.utils.GsonUtil;
import com.tsl.elevator.utils.SPConstants;
import com.tsl.elevator.utils.SPUtils;

import java.util.Arrays;
import java.util.List;

public class GroupsDialog extends DialogFragment {
    DialogGroupsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogGroupsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<GroupsItem> groupsItemList = Arrays.asList(GsonUtil.Companion.ins().getGson().fromJson(getArguments().getString("groupsList"), GroupsItem[].class));
        binding.rvGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvGroups.setAdapter(new KoneGroupsAdapters(groupsItemList, new KoneGroupsAdapters.KoneGroupCallback() {
            @Override
            public void onGroupSelected(String buildAndGroupId) {
                CommonCommandsDataManager.getInstance().setBuildingId(buildAndGroupId);
                Toast.makeText(getContext(), "Building " + buildAndGroupId + " selected", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}

package com.zelda.botwapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.zelda.botwapp.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnCreatures).setOnClickListener(v -> navigateTo(view, "creatures"));
        view.findViewById(R.id.btnMonsters).setOnClickListener(v -> navigateTo(view, "monsters"));
        view.findViewById(R.id.btnMaterials).setOnClickListener(v -> navigateTo(view, "materials"));
        view.findViewById(R.id.btnEquipment).setOnClickListener(v -> navigateTo(view, "equipment"));
        view.findViewById(R.id.btnTreasure).setOnClickListener(v -> navigateTo(view, "treasure"));
    }

    private void navigateTo(View view, String category) {
        Bundle args = new Bundle();
        args.putString("categoryName", category);
        Navigation.findNavController(view).navigate(R.id.action_home_to_category, args);
    }
}
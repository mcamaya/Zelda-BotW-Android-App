package com.zelda.botwapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zelda.botwapp.R;
import com.zelda.botwapp.model.CategoryResponse;
import com.zelda.botwapp.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String category = getArguments() != null ? getArguments().getString("categoryName") : "creatures";

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar.setVisibility(View.VISIBLE);

        Call<CategoryResponse> call = getCallForCategory(category);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                progressBar.setVisibility(View.GONE);
                android.util.Log.d("API_RESPONSE", "Code: " + response.code());
                android.util.Log.d("API_RESPONSE", "Body: " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    android.util.Log.d("API_RESPONSE", "Items: " + response.body().getData().size());
                    EntryAdapter adapter = new EntryAdapter(response.body().getData(), id -> {
                        Bundle args = new Bundle();
                        args.putInt("entryId", id);
                        Navigation.findNavController(view).navigate(R.id.action_category_to_detail, args);
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                android.util.Log.e("API_ERROR", "Error: " + t.getMessage());
            }
        });
    }

    private Call<CategoryResponse> getCallForCategory(String category) {
        switch (category) {
            case "monsters": return RetrofitClient.getInstance().getApi().getMonsters();
            case "materials": return RetrofitClient.getInstance().getApi().getMaterials();
            case "equipment": return RetrofitClient.getInstance().getApi().getEquipment();
            case "treasure": return RetrofitClient.getInstance().getApi().getTreasure();
            default: return RetrofitClient.getInstance().getApi().getCreatures();
        }
    }

}
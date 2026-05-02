package com.zelda.botwapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import coil.Coil;
import coil.request.ImageRequest;
import com.zelda.botwapp.R;
import com.zelda.botwapp.model.CompendiumEntry;
import com.zelda.botwapp.model.SingleEntryResponse;
import com.zelda.botwapp.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int entryId = getArguments() != null ? getArguments().getInt("entryId") : 1;

        RetrofitClient.getInstance().getApi().getEntry(entryId).enqueue(new Callback<SingleEntryResponse>() {
            @Override
            public void onResponse(@NonNull Call<SingleEntryResponse> call, @NonNull Response<SingleEntryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CompendiumEntry entry = response.body().getData();
                    bindData(view, entry);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SingleEntryResponse> call, @NonNull Throwable t) {
                android.util.Log.e("DETAIL_ERROR", t.getMessage());
            }
        });
    }

    private void bindData(View view, CompendiumEntry entry) {
        ImageView imgDetail = view.findViewById(R.id.imgDetail);
        TextView txtName = view.findViewById(R.id.txtDetailName);
        TextView txtCategory = view.findViewById(R.id.txtDetailCategory);
        TextView txtDescription = view.findViewById(R.id.txtDetailDescription);
        TextView txtLocations = view.findViewById(R.id.txtDetailLocations);
        TextView txtDrops = view.findViewById(R.id.txtDetailDrops);

        txtName.setText(capitalize(entry.getName()));

        android.util.Log.d("DETAIL", "Setting title: " + capitalize(entry.getName()));
        requireActivity().setTitle(capitalize(entry.getName()));

        txtCategory.setText(capitalize(entry.getCategory()));
        txtDescription.setText(entry.getDescription());

        if (entry.getImage() != null) {
            ImageRequest request = new ImageRequest.Builder(requireContext())
                    .data(entry.getImage())
                    .target(imgDetail)
                    .build();
            Coil.imageLoader(requireContext()).enqueue(request);
        }

        if (entry.getCommonLocations() != null && !entry.getCommonLocations().isEmpty()) {
            txtLocations.setText("📍 " + join(entry.getCommonLocations()));
        } else {
            txtLocations.setText("📍 Ubicación desconocida");
        }

        if (entry.getDrops() != null && !entry.getDrops().isEmpty()) {
            txtDrops.setText("💧 Drops: " + join(entry.getDrops()));
        } else {
            txtDrops.setVisibility(View.GONE);
        }
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        String[] words = text.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    private String join(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }
}
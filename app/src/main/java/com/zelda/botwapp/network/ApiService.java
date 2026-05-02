package com.zelda.botwapp.network;

import com.zelda.botwapp.model.CategoryResponse;
import com.zelda.botwapp.model.SingleEntryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("category/creatures")
    Call<CategoryResponse> getCreatures();

    @GET("category/monsters")
    Call<CategoryResponse> getMonsters();

    @GET("category/materials")
    Call<CategoryResponse> getMaterials();

    @GET("category/equipment")
    Call<CategoryResponse> getEquipment();

    @GET("category/treasure")
    Call<CategoryResponse> getTreasure();

    @GET("entry/{id}")
    Call<SingleEntryResponse> getEntry(@Path("id") int id);
}
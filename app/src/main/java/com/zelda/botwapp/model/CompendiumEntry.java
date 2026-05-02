package com.zelda.botwapp.model;

import java.util.List;

public class CompendiumEntry {
    private int id;
    private String name;
    private String description;
    private String category;
    private String image;
    private List<String> common_locations;
    private List<String> drops;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImage() { return image; }
    public List<String> getCommonLocations() { return common_locations; }
    public List<String> getDrops() { return drops; }
}
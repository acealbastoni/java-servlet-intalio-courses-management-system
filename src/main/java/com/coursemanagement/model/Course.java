package com.coursemanagement.model;

public class Course {
    private int id;
    private String name;
    private String description;

    public Course() {
        // Default constructor
    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

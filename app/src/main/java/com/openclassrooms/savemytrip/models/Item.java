package com.openclassrooms.savemytrip.models;

public class Item {

    private long id;
    private String text;
    private int category;
    private boolean isSelected;
    private long userId;

    public Item() {
    }

    public Item(long id, String text, int category, long userId) {
        this.id = id;
        this.text = text;
        this.category = category;
        this.isSelected = false;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

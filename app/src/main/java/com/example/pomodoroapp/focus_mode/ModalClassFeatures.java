package com.example.pomodoroapp.focus_mode;

public class ModalClassFeatures {

    private int img_resource;
    private String itemName;

    public ModalClassFeatures(int img_resource, String itemName) {
        this.img_resource = img_resource;
        this.itemName = itemName;
    }

    public int getImg_resource() {
        return img_resource;
    }

    public String getItemName() {
        return itemName;
    }

}

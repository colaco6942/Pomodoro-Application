package com.example.pomodoroapp;

public class ModalClass {

    private int img_resource;
    private String itemName;

    public ModalClass(int img_resource, String itemName) {
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

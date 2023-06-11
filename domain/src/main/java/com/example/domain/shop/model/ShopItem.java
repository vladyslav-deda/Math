package com.example.domain.shop.model;

public class ShopItem {

    private int id;
    private int price;
    private boolean isBought;
    private boolean isSelected;

    public ShopItem(int id, int price, boolean isBought, boolean isSelected) {
        this.id = id;
        this.price = price;
        this.isBought = isBought;
        this.isSelected = isSelected;
    }

    public ShopItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

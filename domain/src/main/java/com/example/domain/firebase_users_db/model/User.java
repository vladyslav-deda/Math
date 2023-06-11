package com.example.domain.firebase_users_db.model;

import com.example.domain.shop.model.ShopItem;

import java.util.List;

public class User {

    private String userName;
    private String password;
    private int moneyBalance;
    private String level;
    private List<ShopItem> shopItems;

    public User() {
    }

    public User(String userName, String password, int moneyBalance, String level, List<ShopItem> shopItems) {
        this.userName = userName;
        this.password = password;
        this.moneyBalance = moneyBalance;
        this.level = level;
        this.shopItems = shopItems;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMoneyBalance() {
        return moneyBalance;
    }

    public void setMoneyBalance(int moneyBalance) {
        this.moneyBalance = moneyBalance;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<ShopItem> getShopItems() {
        return shopItems;
    }

    public void setShopItems(List<ShopItem> shopItems) {
        this.shopItems = shopItems;
    }
}

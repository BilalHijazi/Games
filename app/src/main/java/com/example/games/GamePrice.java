package com.example.games;

import java.io.Serializable;

public class GamePrice implements Serializable {
    private String StoreName,StoreURL;
    private double price;

    public GamePrice(String storeName, String storeURL, double price) {
        StoreName = storeName;
        StoreURL = storeURL;
        this.price = price;
    }
    public GamePrice(){}

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreURL() {
        return StoreURL;
    }

    public void setStoreURL(String storeURL) {
        StoreURL = storeURL;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GamePrice{" +
                "StoreName='" + StoreName + '\'' +
                ", StoreURL='" + StoreURL + '\'' +
                ", price=" + price +
                '}';
    }
}

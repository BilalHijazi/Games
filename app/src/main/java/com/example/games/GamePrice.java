package com.example.games;

import java.io.Serializable;

public class GamePrice implements Serializable {
    private String StoreName,StoreURL,Platform;
    private double price;

    public GamePrice(String storeName, String storeURL, double price,String platform) {
        StoreName = storeName;
        StoreURL = storeURL;
        this.price = price;
        Platform=platform;
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

    public String getPlatform() {
        return Platform;
    }


    public void setPlatform(String platform) {
        Platform = platform;
    }

    @Override
    public String toString() {
        return "GamePrice{" +
                "StoreName='" + StoreName + '\'' +
                ", StoreURL='" + StoreURL + '\'' +
                ", Platform='" + Platform + '\'' +
                ", price=" + price +
                '}';
    }
}

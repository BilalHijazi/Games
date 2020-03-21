package com.example.games;

public class GamePrice {
    private String StoreName,StoreURL;
    private Game game;
    private double price;

    public GamePrice(String storeName, String storeURL, Game game, double price) {
        StoreName = storeName;
        StoreURL = storeURL;
        this.game = game;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
                ", game=" + game +
                ", price=" + price +
                '}';
    }
}

package com.example.hikeplanner;

public class HikeItem {
    private String id;
    private String name;
    private String creatorName;
    private String info;
    private String price;
    private float ratedInfo;
    private int cartedCount;

    public HikeItem() {

    }

    public HikeItem(String name, String creatorName, String info, String price, float ratedInfo, int cartedCount) {
        this.name = name;
        this.creatorName = creatorName;
        this.info = info;
        this.price = price;
        this.ratedInfo = ratedInfo;
        this.cartedCount = cartedCount;
    }


    public String getName() {
        return name;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getInfo() {
        return info;
    }

    public String getPrice() {
        return price;
    }

    public float getRatedInfo() {
        return ratedInfo;
    }

    public int getCartedCount() { return cartedCount; }

    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

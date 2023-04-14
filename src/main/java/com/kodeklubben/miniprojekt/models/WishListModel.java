package com.kodeklubben.miniprojekt.models;

import com.kodeklubben.miniprojekt.repositories.WishListRepository;

import java.util.ArrayList;
import java.util.List;

public class WishListModel {
    String listName;
    ArrayList<WishModel> wishModelList;
    int wishListID;

    public WishListModel(String listName, ArrayList<WishModel> wishes) {
        this.listName = listName;
        this.wishModelList = wishes;
    }

    public WishListModel(String listName, ArrayList<WishModel> wishes, int wishListID) {
        this.listName = listName;
        this.wishModelList = wishes;
        this.wishListID = wishListID;
    }

    public WishListModel() {
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<WishModel> getWishModelList() {
        return wishModelList;
    }

    public void setWishModelList(ArrayList<WishModel> wishModelList) {
        this.wishModelList = wishModelList;
    }

    public int getWishListID() {
        return wishListID;
    }

    public void setWishListID(int wishListID) {
        this.wishListID = wishListID;
    }
}

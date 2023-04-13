package com.kodeklubben.miniprojekt.models;

import java.util.ArrayList;
import java.util.List;

public class WishListModel {
    String listName;
    ArrayList<WishModel> wishModelList;

    public WishListModel(String listName, ArrayList<WishModel> wishes) {
        this.listName = listName;
        this.wishModelList = wishes;
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
}

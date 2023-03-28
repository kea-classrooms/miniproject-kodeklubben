package com.kodeklubben.miniprojekt.models;

import java.util.ArrayList;
import java.util.List;

public class WishListModel {
    private String listName;
    private ArrayList<WishModel> wishModelList;

    public WishListModel(String listName, ArrayList<WishModel> wishes) {
        this.listName = listName;
        this.wishModelList = wishes;
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

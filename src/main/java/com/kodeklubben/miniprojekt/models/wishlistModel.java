package com.kodeklubben.miniprojekt.models;

import java.util.ArrayList;
import java.util.List;

public class wishlistModel {
    private String listName;
    private List<wishModel> wishModelList = new ArrayList<>();

    public wishlistModel(String listName, List<wishModel> wishModelList) {
        this.listName = listName;
        this.wishModelList = wishModelList;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<wishModel> getWishModelList() {
        return wishModelList;
    }

    public void setWishModelList(List<wishModel> wishModelList) {
        this.wishModelList = wishModelList;
    }
}

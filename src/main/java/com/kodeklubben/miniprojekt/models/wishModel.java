package com.kodeklubben.miniprojekt.models;

public class wishModel {
    private int ID;
    private String name;
    private String link;

    public wishModel(int ID, String name, String link) {
        this.ID = ID;
        this.name = name;
        this.link = link;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

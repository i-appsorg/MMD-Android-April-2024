package com.MamaDevalayam.Model;

import org.json.JSONArray;

public class Category_new {
    String category_id;
    String category_code;
    String category_name;
    String slected;
    private boolean isSelected;
    JSONArray subcategory;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSlected() {
        return slected;
    }

    public void setSlected(String slected) {
        this.slected = slected;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public JSONArray getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(JSONArray subcategory) {
        this.subcategory = subcategory;
    }

}

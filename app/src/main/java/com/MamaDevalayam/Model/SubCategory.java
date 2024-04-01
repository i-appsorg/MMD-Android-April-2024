package com.MamaDevalayam.Model;

import java.util.List;

public class SubCategory {

    private String categoryId;
    private String categoryName;
    private String isChecked = "NO";
    private List<ChildCategory> childCategories;

    public SubCategory() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public List<ChildCategory> getSubCategory() {
        return childCategories;
    }

    public void setSubCategory(List<ChildCategory> subCategory) {
        this.childCategories = subCategory;
    }
}

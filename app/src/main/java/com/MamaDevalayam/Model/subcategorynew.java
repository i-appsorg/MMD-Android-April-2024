package com.MamaDevalayam.Model;

import java.util.List;

public class subcategorynew {

    String sub_category_id;
    String sub_category_code;
    String sub_category_name;
    String datafind;
    List<child_categorynew>child_category_news;
    private String isChecked;

    public String getDatafind() {
        return datafind;
    }

    public void setDatafind(String datafind) {
        this.datafind = datafind;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getSub_category_code() {
        return sub_category_code;
    }

    public void setSub_category_code(String sub_category_code) {
        this.sub_category_code = sub_category_code;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public List<child_categorynew> getChild_category_news() {
        return child_category_news;
    }

    public void setChild_category_news(List<child_categorynew> child_category_news) {
        this.child_category_news = child_category_news;
    }
}

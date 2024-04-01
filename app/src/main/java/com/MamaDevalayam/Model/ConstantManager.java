package com.MamaDevalayam.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class ConstantManager {

    public static final String CHECK_BOX_CHECKED_TRUE = "YES";
    public static final String CHECK_BOX_CHECKED_FALSE = "NO";

    public static ArrayList<ArrayList<HashMap<String, String>>>  parentItems = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> grandItems = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> parentItems1 = new ArrayList<>();
    public static ArrayList<ArrayList<HashMap<String, String>>> childItems = new ArrayList<>();

    public class Parameter {
        public static final String IS_CHECKED = "is_checked";
        public static final String SUB_CATEGORY_NAME = "sub_category_name";
        public static final String SUB_CATEGORY_CODE = "sub_category_code";
        public static final String CHILD_CATEGORY_NAME = "child_category_name";
        public static final String CHILD_CATEGORY_CODE = "child_category_code";
        public static final String CATEGORY_NAME = "category_name";
        public static final String CATEGORY_ID = "category_id";
        public static final String SUB_ID = "sub_id";
        public static final String CHILD_ID = "child_id";
        public static final String NO_CHILD = "no_child";
    }
}

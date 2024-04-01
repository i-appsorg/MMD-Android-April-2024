package com.MamaDevalayam.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.Adapter.ThreeLevelListAdapter;
import com.MamaDevalayam.Model.Category_new;
import com.MamaDevalayam.Model.child_categorynew;
import com.MamaDevalayam.Model.subcategorynew;
import com.MamaDevalayam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdavanceSearchUIDesign extends AppCompatActivity {

    TextView exempt_tv_deselect, exempt_tv_select, non_exempt_deselect_tv, non_exempt_select_tv;
    ExpandableListView expandable_listview;
    static ArrayList<Category_new> category_newArrayList = new ArrayList<>();
    static ArrayList<subcategorynew> sub_category_newArrayList = new ArrayList<>();
    static ArrayList<child_categorynew> child_category_newArrayList = new ArrayList<>();
    private ArrayList<HashMap<String, String>> GrandItems= new ArrayList<>();
    private ArrayList<HashMap<String, String>> parentItems1= new ArrayList<>();
    private ArrayList<ArrayList<HashMap<String, String>>> parentItems= new ArrayList<>();
    private ArrayList<ArrayList<HashMap<String, String>>> childItems= new ArrayList<>();
    ThreeLevelListAdapter advanceSearchnew;
    LinearLayout linear1,linear2;
    RelativeLayout expandable_relative;
    String TAG = AdavanceSearchUIDesign.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adavance_search_uidesign);
        init();
        listener();
    }


    private void init() {
        exempt_tv_deselect = (TextView) findViewById(R.id.exempt_tv_deselect);
        exempt_tv_select = (TextView) findViewById(R.id.exempt_tv_select);
        non_exempt_deselect_tv = (TextView) findViewById(R.id.non_exempt_deselect_tv);
        non_exempt_select_tv = (TextView) findViewById(R.id.non_exempt_select_tv);
        expandable_listview = (ExpandableListView) findViewById(R.id.expandable_listview);
        expandable_listview.setGroupIndicator(null);
        linear1=(LinearLayout)findViewById(R.id.linear1);
        linear2=(LinearLayout)findViewById(R.id.linear2);
        expandable_relative=(RelativeLayout)findViewById(R.id.expandable_relative);
        data();

    }

    private void listener() {
        exempt_tv_deselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                non_exempt_select_tv.setVisibility(View.GONE);
                non_exempt_deselect_tv.setVisibility(View.VISIBLE);
                exempt_tv_deselect.setVisibility(View.GONE);
                exempt_tv_select.setVisibility(View.VISIBLE);
            }
        });
        exempt_tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exempt_tv_select.setVisibility(View.GONE);
                exempt_tv_deselect.setVisibility(View.VISIBLE);
            }
        });
        non_exempt_deselect_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exempt_tv_select.setVisibility(View.GONE);
                exempt_tv_deselect.setVisibility(View.VISIBLE);
                non_exempt_deselect_tv.setVisibility(View.GONE);
                non_exempt_select_tv.setVisibility(View.VISIBLE);
            }
        });
        non_exempt_select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                non_exempt_select_tv.setVisibility(View.GONE);
                non_exempt_deselect_tv.setVisibility(View.VISIBLE);
            }
        });
        expandable_listview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandable_listview.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        /*linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandable_relative.setVisibility(View.VISIBLE);
                advanceSearchnew = new ThreeLevelListAdapter(AdavanceSearchUIDesign.this, parentItems1, childItems, GrandItems);
                expandable_listview.setAdapter(advanceSearchnew);
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandable_relative.setVisibility(View.VISIBLE);
                advanceSearchnew = new ThreeLevelListAdapter(AdavanceSearchUIDesign.this, parentItems1, childItems, GrandItems);
                expandable_listview.setAdapter(advanceSearchnew);
            }
        });*/

    }

    private void data() {
        category_newArrayList.clear();
        sub_category_newArrayList.clear();
        child_category_newArrayList.clear();
        GrandItems.clear();
        parentItems1.clear();
        childItems.clear();
        String reponse="{\n" +
                "    \"status\": 1,\n" +
                "    \"message\": \"Records Found\",\n" +
                "    \"token_status\": 1,\n" +
                "    \"token_message\": \"Valid token\",\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"category_id\": \"1\",\n" +
                "            \"category_code\": \"A\",\n" +
                "            \"category_name\": \"Arts, culture & humanities\",\n" +
                "            \"subcategory\": [\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"1\",\n" +
                "                    \"sub_category_code\": \"A01\",\n" +
                "                    \"sub_category_name\": \"Alliances & advocacy\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"2\",\n" +
                "                    \"sub_category_code\": \"A02\",\n" +
                "                    \"sub_category_name\": \"Management & technical assistance\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"3\",\n" +
                "                    \"sub_category_code\": \"A03\",\n" +
                "                    \"sub_category_name\": \"Professional societies & associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"4\",\n" +
                "                    \"sub_category_code\": \"A05\",\n" +
                "                    \"sub_category_name\": \"Research institutes & public policy analysis\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"5\",\n" +
                "                    \"sub_category_code\": \"A11\",\n" +
                "                    \"sub_category_name\": \"Single organization support\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"6\",\n" +
                "                    \"sub_category_code\": \"A12\",\n" +
                "                    \"sub_category_name\": \"Fund raising & fund distribution\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"7\",\n" +
                "                    \"sub_category_code\": \"A19\",\n" +
                "                    \"sub_category_name\": \"Support nec\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"8\",\n" +
                "                    \"sub_category_code\": \"A20\",\n" +
                "                    \"sub_category_name\": \"Arts & culture\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"1\",\n" +
                "                            \"child_category_code\": \"A23\",\n" +
                "                            \"child_category_name\": \"Cultural & ethnic awareness\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"2\",\n" +
                "                            \"child_category_code\": \"A24\",\n" +
                "                            \"child_category_name\": \"Folk arts\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"3\",\n" +
                "                            \"child_category_code\": \"A25\",\n" +
                "                            \"child_category_name\": \"Arts education\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"4\",\n" +
                "                            \"child_category_code\": \"A26\",\n" +
                "                            \"child_category_name\": \"Arts & humanities councils & agencies\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"5\",\n" +
                "                            \"child_category_code\": \"A27\",\n" +
                "                            \"child_category_name\": \"Community celebrations\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"9\",\n" +
                "                    \"sub_category_code\": \"A40\",\n" +
                "                    \"sub_category_name\": \"Visual arts\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"10\",\n" +
                "                    \"sub_category_code\": \"A50\",\n" +
                "                    \"sub_category_name\": \"Museums\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"6\",\n" +
                "                            \"child_category_code\": \"A51\",\n" +
                "                            \"child_category_name\": \"Art museums\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"7\",\n" +
                "                            \"child_category_code\": \"A52\",\n" +
                "                            \"child_category_name\": \"Children’s museums\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"8\",\n" +
                "                            \"child_category_code\": \"A54\",\n" +
                "                            \"child_category_name\": \"History museums\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"9\",\n" +
                "                            \"child_category_code\": \"A56\",\n" +
                "                            \"child_category_name\": \"Natural history & natural science museums\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"10\",\n" +
                "                            \"child_category_code\": \"A57\",\n" +
                "                            \"child_category_name\": \"Science & technology museums\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"11\",\n" +
                "                    \"sub_category_code\": \"A60\",\n" +
                "                    \"sub_category_name\": \"Performing arts\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"11\",\n" +
                "                            \"child_category_code\": \"A61\",\n" +
                "                            \"child_category_name\": \"Performing arts centers\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"12\",\n" +
                "                            \"child_category_code\": \"A62\",\n" +
                "                            \"child_category_name\": \"Dance\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"13\",\n" +
                "                            \"child_category_code\": \"A63\",\n" +
                "                            \"child_category_name\": \"Ballet\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"14\",\n" +
                "                            \"child_category_code\": \"A65\",\n" +
                "                            \"child_category_name\": \"Theater\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"15\",\n" +
                "                            \"child_category_code\": \"A68\",\n" +
                "                            \"child_category_name\": \"Music\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"16\",\n" +
                "                            \"child_category_code\": \"A69\",\n" +
                "                            \"child_category_name\": \"Symphony orchestras\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"17\",\n" +
                "                            \"child_category_code\": \"A6a\",\n" +
                "                            \"child_category_name\": \"Opera\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"18\",\n" +
                "                            \"child_category_code\": \"A6b\",\n" +
                "                            \"child_category_name\": \"Singing & choral groups\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"19\",\n" +
                "                            \"child_category_code\": \"A6c\",\n" +
                "                            \"child_category_name\": \"Bands & ensembles\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"20\",\n" +
                "                            \"child_category_code\": \"A6e\",\n" +
                "                            \"child_category_name\": \"Performing arts schools\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"12\",\n" +
                "                    \"sub_category_code\": \"A70\",\n" +
                "                    \"sub_category_name\": \"Humanities\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"13\",\n" +
                "                    \"sub_category_code\": \"A80\",\n" +
                "                    \"sub_category_name\": \"Historical organizations\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"21\",\n" +
                "                            \"child_category_code\": \"A82\",\n" +
                "                            \"child_category_name\": \"Historical societies & historic preservation\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"22\",\n" +
                "                            \"child_category_code\": \"A84\",\n" +
                "                            \"child_category_name\": \"Commemorative events\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"14\",\n" +
                "                    \"sub_category_code\": \"A90\",\n" +
                "                    \"sub_category_name\": \"Arts services\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"15\",\n" +
                "                    \"sub_category_code\": \"A99\",\n" +
                "                    \"sub_category_name\": \"Arts, culture & humanities nec\",\n" +
                "                    \"child_category\": []\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"category_id\": \"2\",\n" +
                "            \"category_code\": \"B\",\n" +
                "            \"category_name\": \"Education\",\n" +
                "            \"subcategory\": [\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"16\",\n" +
                "                    \"sub_category_code\": \"B01\",\n" +
                "                    \"sub_category_name\": \"Alliances & advocacy\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"17\",\n" +
                "                    \"sub_category_code\": \"B02\",\n" +
                "                    \"sub_category_name\": \"Management & technical assistance\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"18\",\n" +
                "                    \"sub_category_code\": \"B03\",\n" +
                "                    \"sub_category_name\": \"Professional societies & associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"19\",\n" +
                "                    \"sub_category_code\": \"B05\",\n" +
                "                    \"sub_category_name\": \"Research institutes & public policy analysis\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"20\",\n" +
                "                    \"sub_category_code\": \"B11\",\n" +
                "                    \"sub_category_name\": \"Single organization support\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"21\",\n" +
                "                    \"sub_category_code\": \"B12\",\n" +
                "                    \"sub_category_name\": \"Fund raising & fund distribution\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"22\",\n" +
                "                    \"sub_category_code\": \"B19\",\n" +
                "                    \"sub_category_name\": \"Support nec\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"23\",\n" +
                "                    \"sub_category_code\": \"B20\",\n" +
                "                    \"sub_category_name\": \"Elementary & secondary schools\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"23\",\n" +
                "                            \"child_category_code\": \"B21\",\n" +
                "                            \"child_category_name\": \"Preschools\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"24\",\n" +
                "                            \"child_category_code\": \"B24\",\n" +
                "                            \"child_category_name\": \"Primary & elementary schools\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"25\",\n" +
                "                            \"child_category_code\": \"B25\",\n" +
                "                            \"child_category_name\": \"Secondary & high schools\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"26\",\n" +
                "                            \"child_category_code\": \"B28\",\n" +
                "                            \"child_category_name\": \"Special education\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"27\",\n" +
                "                            \"child_category_code\": \"B29\",\n" +
                "                            \"child_category_name\": \"Charter schools\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"24\",\n" +
                "                    \"sub_category_code\": \"B30\",\n" +
                "                    \"sub_category_name\": \"Vocational & technical schools\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"25\",\n" +
                "                    \"sub_category_code\": \"B40\",\n" +
                "                    \"sub_category_name\": \"Higher education\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"28\",\n" +
                "                            \"child_category_code\": \"B41\",\n" +
                "                            \"child_category_name\": \"Two-year colleges\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"29\",\n" +
                "                            \"child_category_code\": \"B42\",\n" +
                "                            \"child_category_name\": \"Undergraduate colleges\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"30\",\n" +
                "                            \"child_category_code\": \"B43\",\n" +
                "                            \"child_category_name\": \"Universities\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"26\",\n" +
                "                    \"sub_category_code\": \"B50\",\n" +
                "                    \"sub_category_name\": \"Graduate & professional schools\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"27\",\n" +
                "                    \"sub_category_code\": \"B60\",\n" +
                "                    \"sub_category_name\": \"Adult education\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"28\",\n" +
                "                    \"sub_category_code\": \"B70\",\n" +
                "                    \"sub_category_name\": \"Libraries\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"29\",\n" +
                "                    \"sub_category_code\": \"B80\",\n" +
                "                    \"sub_category_name\": \"Student services\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"31\",\n" +
                "                            \"child_category_code\": \"B82\",\n" +
                "                            \"child_category_name\": \"Scholarships & student financial aid\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"32\",\n" +
                "                            \"child_category_code\": \"B83\",\n" +
                "                            \"child_category_name\": \"Student sororities & fraternities\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"33\",\n" +
                "                            \"child_category_code\": \"B84\",\n" +
                "                            \"child_category_name\": \"Alumni associations\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"30\",\n" +
                "                    \"sub_category_code\": \"B90\",\n" +
                "                    \"sub_category_name\": \"Educational services\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"34\",\n" +
                "                            \"child_category_code\": \"B92\",\n" +
                "                            \"child_category_name\": \"Remedial reading & encouragement\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"35\",\n" +
                "                            \"child_category_code\": \"B94\",\n" +
                "                            \"child_category_name\": \"Parent & teacher groups\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"31\",\n" +
                "                    \"sub_category_code\": \"B99\",\n" +
                "                    \"sub_category_name\": \"Education nec\",\n" +
                "                    \"child_category\": []\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"category_id\": \"3\",\n" +
                "            \"category_code\": \"C\",\n" +
                "            \"category_name\": \"Environment\",\n" +
                "            \"subcategory\": [\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"32\",\n" +
                "                    \"sub_category_code\": \"C01\",\n" +
                "                    \"sub_category_name\": \"Alliances & advocacy\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"33\",\n" +
                "                    \"sub_category_code\": \"C02\",\n" +
                "                    \"sub_category_name\": \"Management & technical assistance\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"34\",\n" +
                "                    \"sub_category_code\": \"C03\",\n" +
                "                    \"sub_category_name\": \"Professional societies & associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"35\",\n" +
                "                    \"sub_category_code\": \"C05\",\n" +
                "                    \"sub_category_name\": \"Research institutes & public policy analysis\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"36\",\n" +
                "                    \"sub_category_code\": \"C11\",\n" +
                "                    \"sub_category_name\": \"Single organization support\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"37\",\n" +
                "                    \"sub_category_code\": \"C12\",\n" +
                "                    \"sub_category_name\": \"Fund raising & fund distribution\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"38\",\n" +
                "                    \"sub_category_code\": \"C19\",\n" +
                "                    \"sub_category_name\": \"Support nec\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"39\",\n" +
                "                    \"sub_category_code\": \"C20\",\n" +
                "                    \"sub_category_name\": \"Pollution abatement & control\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"36\",\n" +
                "                            \"child_category_code\": \"C27\",\n" +
                "                            \"child_category_name\": \"Recycling\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"40\",\n" +
                "                    \"sub_category_code\": \"C30\",\n" +
                "                    \"sub_category_name\": \"Natural resources conservation & protection\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"37\",\n" +
                "                            \"child_category_code\": \"C32\",\n" +
                "                            \"child_category_name\": \"Water resources, wetlands conservation & managemen\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"38\",\n" +
                "                            \"child_category_code\": \"C34\",\n" +
                "                            \"child_category_name\": \"Land resources conservation\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"39\",\n" +
                "                            \"child_category_code\": \"C35\",\n" +
                "                            \"child_category_name\": \"Energy resources conservation & development\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"40\",\n" +
                "                            \"child_category_code\": \"C36\",\n" +
                "                            \"child_category_name\": \"Forest conservation\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"41\",\n" +
                "                    \"sub_category_code\": \"C40\",\n" +
                "                    \"sub_category_name\": \"Botanical, horticultural & landscape services\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"41\",\n" +
                "                            \"child_category_code\": \"C41\",\n" +
                "                            \"child_category_name\": \"Botanical gardens & arboreta\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"42\",\n" +
                "                            \"child_category_code\": \"C42\",\n" +
                "                            \"child_category_name\": \"Garden clubs\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"42\",\n" +
                "                    \"sub_category_code\": \"C50\",\n" +
                "                    \"sub_category_name\": \"Environmental beautification\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"43\",\n" +
                "                    \"sub_category_code\": \"C60\",\n" +
                "                    \"sub_category_name\": \"Environmental education\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"44\",\n" +
                "                    \"sub_category_code\": \"C99\",\n" +
                "                    \"sub_category_name\": \"Environment nec\",\n" +
                "                    \"child_category\": []\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"category_id\": \"4\",\n" +
                "            \"category_code\": \"D\",\n" +
                "            \"category_name\": \"Animal-related\",\n" +
                "            \"subcategory\": [\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"45\",\n" +
                "                    \"sub_category_code\": \"D01\",\n" +
                "                    \"sub_category_name\": \"Alliances & advocacy\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"46\",\n" +
                "                    \"sub_category_code\": \"D02\",\n" +
                "                    \"sub_category_name\": \"Management & technical assistance\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"47\",\n" +
                "                    \"sub_category_code\": \"D03\",\n" +
                "                    \"sub_category_name\": \"Professional societies & associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"48\",\n" +
                "                    \"sub_category_code\": \"D05\",\n" +
                "                    \"sub_category_name\": \"Research institutes & public policy analysis\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"49\",\n" +
                "                    \"sub_category_code\": \"D11\",\n" +
                "                    \"sub_category_name\": \"Single organization support\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"50\",\n" +
                "                    \"sub_category_code\": \"D12\",\n" +
                "                    \"sub_category_name\": \"Fund raising & fund distribution\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"51\",\n" +
                "                    \"sub_category_code\": \"D19\",\n" +
                "                    \"sub_category_name\": \"Support nec\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"52\",\n" +
                "                    \"sub_category_code\": \"D20\",\n" +
                "                    \"sub_category_name\": \"Animal protection & welfare\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"53\",\n" +
                "                    \"sub_category_code\": \"D30\",\n" +
                "                    \"sub_category_name\": \"Wildlife preservation & protection\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"43\",\n" +
                "                            \"child_category_code\": \"D31\",\n" +
                "                            \"child_category_name\": \"Protection of endangered species\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"44\",\n" +
                "                            \"child_category_code\": \"D32\",\n" +
                "                            \"child_category_name\": \"Bird sanctuaries\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"45\",\n" +
                "                            \"child_category_code\": \"D33\",\n" +
                "                            \"child_category_name\": \"Fisheries resources\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"46\",\n" +
                "                            \"child_category_code\": \"D34\",\n" +
                "                            \"child_category_name\": \"Wildlife sanctuaries\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"54\",\n" +
                "                    \"sub_category_code\": \"D40\",\n" +
                "                    \"sub_category_name\": \"Veterinary services\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"55\",\n" +
                "                    \"sub_category_code\": \"D50\",\n" +
                "                    \"sub_category_name\": \"Zoos & aquariums\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"56\",\n" +
                "                    \"sub_category_code\": \"D60\",\n" +
                "                    \"sub_category_name\": \"Animal services nec\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"47\",\n" +
                "                            \"child_category_code\": \"D61\",\n" +
                "                            \"child_category_name\": \"Animal training\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"57\",\n" +
                "                    \"sub_category_code\": \"D99\",\n" +
                "                    \"sub_category_name\": \"Animal-related nec\",\n" +
                "                    \"child_category\": []\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"category_id\": \"5\",\n" +
                "            \"category_code\": \"E\",\n" +
                "            \"category_name\": \"Health care\",\n" +
                "            \"subcategory\": [\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"58\",\n" +
                "                    \"sub_category_code\": \"E01\",\n" +
                "                    \"sub_category_name\": \"Alliances & advocacy\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"59\",\n" +
                "                    \"sub_category_code\": \"E02\",\n" +
                "                    \"sub_category_name\": \"Management & technical assistance\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"60\",\n" +
                "                    \"sub_category_code\": \"E03\",\n" +
                "                    \"sub_category_name\": \"Professional societies & associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"61\",\n" +
                "                    \"sub_category_code\": \"E05\",\n" +
                "                    \"sub_category_name\": \"Research institutes & public policy analysis\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"62\",\n" +
                "                    \"sub_category_code\": \"E11\",\n" +
                "                    \"sub_category_name\": \"Single organization support\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"63\",\n" +
                "                    \"sub_category_code\": \"E12\",\n" +
                "                    \"sub_category_name\": \"Fund raising & fund distribution\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"64\",\n" +
                "                    \"sub_category_code\": \"E19\",\n" +
                "                    \"sub_category_name\": \"Support nec\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"65\",\n" +
                "                    \"sub_category_code\": \"E20\",\n" +
                "                    \"sub_category_name\": \"Hospitals\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"48\",\n" +
                "                            \"child_category_code\": \"E21\",\n" +
                "                            \"child_category_name\": \"Community health systems\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"49\",\n" +
                "                            \"child_category_code\": \"E22\",\n" +
                "                            \"child_category_name\": \"General hospitals\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"50\",\n" +
                "                            \"child_category_code\": \"E24\",\n" +
                "                            \"child_category_name\": \"Specialty hospitals\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"66\",\n" +
                "                    \"sub_category_code\": \"E30\",\n" +
                "                    \"sub_category_name\": \"Ambulatory & primary health care\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"51\",\n" +
                "                            \"child_category_code\": \"E31\",\n" +
                "                            \"child_category_name\": \"Group health practices\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"52\",\n" +
                "                            \"child_category_code\": \"E32\",\n" +
                "                            \"child_category_name\": \"Community clinics\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"67\",\n" +
                "                    \"sub_category_code\": \"E40\",\n" +
                "                    \"sub_category_name\": \"Reproductive health care\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"53\",\n" +
                "                            \"child_category_code\": \"E42\",\n" +
                "                            \"child_category_name\": \"Family planning\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"68\",\n" +
                "                    \"sub_category_code\": \"E50\",\n" +
                "                    \"sub_category_name\": \"Rehabilitative care\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"69\",\n" +
                "                    \"sub_category_code\": \"E60\",\n" +
                "                    \"sub_category_name\": \"Health support\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"54\",\n" +
                "                            \"child_category_code\": \"E61\",\n" +
                "                            \"child_category_name\": \"Blood banks\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"55\",\n" +
                "                            \"child_category_code\": \"E62\",\n" +
                "                            \"child_category_name\": \"Emergency medical services & transport\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"56\",\n" +
                "                            \"child_category_code\": \"E65\",\n" +
                "                            \"child_category_name\": \"Organ & tissue banks\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"70\",\n" +
                "                    \"sub_category_code\": \"E70\",\n" +
                "                    \"sub_category_name\": \"Public health\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"71\",\n" +
                "                    \"sub_category_code\": \"E80\",\n" +
                "                    \"sub_category_name\": \"Health (general & financing)\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"57\",\n" +
                "                            \"child_category_code\": \"E86\",\n" +
                "                            \"child_category_name\": \"Patient & family support\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"72\",\n" +
                "                    \"sub_category_code\": \"E90\",\n" +
                "                    \"sub_category_name\": \"Nursing\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"58\",\n" +
                "                            \"child_category_code\": \"E91\",\n" +
                "                            \"child_category_name\": \"Nursing facilities\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"59\",\n" +
                "                            \"child_category_code\": \"E92\",\n" +
                "                            \"child_category_name\": \"Home health care\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"73\",\n" +
                "                    \"sub_category_code\": \"E99\",\n" +
                "                    \"sub_category_name\": \"Health care nec\",\n" +
                "                    \"child_category\": []\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"category_id\": \"6\",\n" +
                "            \"category_code\": \"F\",\n" +
                "            \"category_name\": \"Mental health & crisis intervention\",\n" +
                "            \"subcategory\": [\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"74\",\n" +
                "                    \"sub_category_code\": \"F01\",\n" +
                "                    \"sub_category_name\": \"Alliances & advocacy\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"75\",\n" +
                "                    \"sub_category_code\": \"F02\",\n" +
                "                    \"sub_category_name\": \"Management & technical assistance\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"76\",\n" +
                "                    \"sub_category_code\": \"F03\",\n" +
                "                    \"sub_category_name\": \"Professional societies & associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"77\",\n" +
                "                    \"sub_category_code\": \"F05\",\n" +
                "                    \"sub_category_name\": \"Research institutes & public policy analysis\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"78\",\n" +
                "                    \"sub_category_code\": \"F11\",\n" +
                "                    \"sub_category_name\": \"Single organization support\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"79\",\n" +
                "                    \"sub_category_code\": \"F12\",\n" +
                "                    \"sub_category_name\": \"Fund raising & fund distribution\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"80\",\n" +
                "                    \"sub_category_code\": \"F19\",\n" +
                "                    \"sub_category_name\": \"Support nec\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"81\",\n" +
                "                    \"sub_category_code\": \"F20\",\n" +
                "                    \"sub_category_name\": \"Substance abuse dependency, prevention & treatment\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"60\",\n" +
                "                            \"child_category_code\": \"F21\",\n" +
                "                            \"child_category_name\": \"Substance abuse prevention\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"61\",\n" +
                "                            \"child_category_code\": \"F22\",\n" +
                "                            \"child_category_name\": \"Substance abuse treatment\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"82\",\n" +
                "                    \"sub_category_code\": \"F30\",\n" +
                "                    \"sub_category_name\": \"Mental health treatment\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"62\",\n" +
                "                            \"child_category_code\": \"F31\",\n" +
                "                            \"child_category_name\": \"Psychiatric hospitals\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"63\",\n" +
                "                            \"child_category_code\": \"F32\",\n" +
                "                            \"child_category_name\": \"Community mental health centers\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"64\",\n" +
                "                            \"child_category_code\": \"F33\",\n" +
                "                            \"child_category_name\": \"Residential mental health treatment\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"83\",\n" +
                "                    \"sub_category_code\": \"F40\",\n" +
                "                    \"sub_category_name\": \"Hot lines & crisis intervention\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"65\",\n" +
                "                            \"child_category_code\": \"F42\",\n" +
                "                            \"child_category_name\": \"Sexual assault services\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"84\",\n" +
                "                    \"sub_category_code\": \"F50\",\n" +
                "                    \"sub_category_name\": \"Addictive disorders nec\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"66\",\n" +
                "                            \"child_category_code\": \"F52\",\n" +
                "                            \"child_category_name\": \"Smoking addiction\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"67\",\n" +
                "                            \"child_category_code\": \"F53\",\n" +
                "                            \"child_category_name\": \"Eating disorders & addictions\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"68\",\n" +
                "                            \"child_category_code\": \"F54\",\n" +
                "                            \"child_category_name\": \"Gambling addiction\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"85\",\n" +
                "                    \"sub_category_code\": \"F60\",\n" +
                "                    \"sub_category_name\": \"Counseling\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"86\",\n" +
                "                    \"sub_category_code\": \"F70\",\n" +
                "                    \"sub_category_name\": \"Mental health disorders\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"87\",\n" +
                "                    \"sub_category_code\": \"F80\",\n" +
                "                    \"sub_category_name\": \"Mental health associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"88\",\n" +
                "                    \"sub_category_code\": \"F99\",\n" +
                "                    \"sub_category_name\": \"Mental health & crisis intervention nec\",\n" +
                "                    \"child_category\": []\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"category_id\": \"7\",\n" +
                "            \"category_code\": \"G\",\n" +
                "            \"category_name\": \"Diseases, disorders & medical disciplines\",\n" +
                "            \"subcategory\": [\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"89\",\n" +
                "                    \"sub_category_code\": \"G01\",\n" +
                "                    \"sub_category_name\": \"Alliances & advocacy\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"90\",\n" +
                "                    \"sub_category_code\": \"G02\",\n" +
                "                    \"sub_category_name\": \"Management & technical assistance\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"91\",\n" +
                "                    \"sub_category_code\": \"G03\",\n" +
                "                    \"sub_category_name\": \"Professional societies & associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"92\",\n" +
                "                    \"sub_category_code\": \"G05\",\n" +
                "                    \"sub_category_name\": \"Research institutes & public policy analysis\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"93\",\n" +
                "                    \"sub_category_code\": \"G11\",\n" +
                "                    \"sub_category_name\": \"Single organization support\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"94\",\n" +
                "                    \"sub_category_code\": \"G12\",\n" +
                "                    \"sub_category_name\": \"Fund raising & fund distribution\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"95\",\n" +
                "                    \"sub_category_code\": \"G19\",\n" +
                "                    \"sub_category_name\": \"Support nec\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"96\",\n" +
                "                    \"sub_category_code\": \"G20\",\n" +
                "                    \"sub_category_name\": \"Birth defects & genetic diseases\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"69\",\n" +
                "                            \"child_category_code\": \"G25\",\n" +
                "                            \"child_category_name\": \"Down syndrome\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"97\",\n" +
                "                    \"sub_category_code\": \"G30\",\n" +
                "                    \"sub_category_name\": \"Cancer\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"70\",\n" +
                "                            \"child_category_code\": \"G32\",\n" +
                "                            \"child_category_name\": \"Breast cancer\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"98\",\n" +
                "                    \"sub_category_code\": \"G40\",\n" +
                "                    \"sub_category_name\": \"Diseases of specific organs\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"71\",\n" +
                "                            \"child_category_code\": \"G41\",\n" +
                "                            \"child_category_name\": \"Eye diseases, blindness & vision impairments\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"72\",\n" +
                "                            \"child_category_code\": \"G42\",\n" +
                "                            \"child_category_name\": \"Ear & throat diseases\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"73\",\n" +
                "                            \"child_category_code\": \"G43\",\n" +
                "                            \"child_category_name\": \"Heart & circulatory system diseases & disorders\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"74\",\n" +
                "                            \"child_category_code\": \"G44\",\n" +
                "                            \"child_category_name\": \"Kidney diseases\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"75\",\n" +
                "                            \"child_category_code\": \"G45\",\n" +
                "                            \"child_category_name\": \"Lung diseases\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"76\",\n" +
                "                            \"child_category_code\": \"G48\",\n" +
                "                            \"child_category_name\": \"Brain disorders\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"99\",\n" +
                "                    \"sub_category_code\": \"G50\",\n" +
                "                    \"sub_category_name\": \"Nerve, muscle & bone diseases\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"77\",\n" +
                "                            \"child_category_code\": \"G51\",\n" +
                "                            \"child_category_name\": \"Arthritis\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"78\",\n" +
                "                            \"child_category_code\": \"G54\",\n" +
                "                            \"child_category_name\": \"Epilepsy\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"100\",\n" +
                "                    \"sub_category_code\": \"G60\",\n" +
                "                    \"sub_category_name\": \"Allergy-related diseases\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"79\",\n" +
                "                            \"child_category_code\": \"G61\",\n" +
                "                            \"child_category_name\": \"Asthma\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"101\",\n" +
                "                    \"sub_category_code\": \"G70\",\n" +
                "                    \"sub_category_name\": \"Digestive diseases & disorders\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"102\",\n" +
                "                    \"sub_category_code\": \"G80\",\n" +
                "                    \"sub_category_name\": \"Specifically named diseases\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"80\",\n" +
                "                            \"child_category_code\": \"G81\",\n" +
                "                            \"child_category_name\": \"Aids\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"81\",\n" +
                "                            \"child_category_code\": \"G83\",\n" +
                "                            \"child_category_name\": \"Alzheimer’s disease\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"82\",\n" +
                "                            \"child_category_code\": \"G84\",\n" +
                "                            \"child_category_name\": \"Autism\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"103\",\n" +
                "                    \"sub_category_code\": \"G90\",\n" +
                "                    \"sub_category_name\": \"Medical disciplines\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"83\",\n" +
                "                            \"child_category_code\": \"G92\",\n" +
                "                            \"child_category_name\": \"Biomedicine & bioengineering\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"84\",\n" +
                "                            \"child_category_code\": \"G94\",\n" +
                "                            \"child_category_name\": \"Geriatrics\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"85\",\n" +
                "                            \"child_category_code\": \"G96\",\n" +
                "                            \"child_category_name\": \"Neurology & neuroscience\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"86\",\n" +
                "                            \"child_category_code\": \"G98\",\n" +
                "                            \"child_category_name\": \"Pediatrics\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"104\",\n" +
                "                    \"sub_category_code\": \"G9b\",\n" +
                "                    \"sub_category_name\": \"Surgical specialties\",\n" +
                "                    \"child_category\": []\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"category_id\": \"8\",\n" +
                "            \"category_code\": \"F\",\n" +
                "            \"category_name\": \"Mental health & crisis intervention\",\n" +
                "            \"subcategory\": [\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"105\",\n" +
                "                    \"sub_category_code\": \"F01\",\n" +
                "                    \"sub_category_name\": \"Alliances & advocacy\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"106\",\n" +
                "                    \"sub_category_code\": \"F02\",\n" +
                "                    \"sub_category_name\": \"Management & technical assistance\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"107\",\n" +
                "                    \"sub_category_code\": \"F03\",\n" +
                "                    \"sub_category_name\": \"Professional societies & associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"108\",\n" +
                "                    \"sub_category_code\": \"F05\",\n" +
                "                    \"sub_category_name\": \"Research institutes & public policy analysis\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"109\",\n" +
                "                    \"sub_category_code\": \"F11\",\n" +
                "                    \"sub_category_name\": \"Single organization support\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"110\",\n" +
                "                    \"sub_category_code\": \"F12\",\n" +
                "                    \"sub_category_name\": \"Fund raising & fund distribution\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"111\",\n" +
                "                    \"sub_category_code\": \"F19\",\n" +
                "                    \"sub_category_name\": \"Support nec\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"112\",\n" +
                "                    \"sub_category_code\": \"F20\",\n" +
                "                    \"sub_category_name\": \"Substance abuse dependency, prevention & treatment\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"87\",\n" +
                "                            \"child_category_code\": \"F21\",\n" +
                "                            \"child_category_name\": \"Substance abuse prevention\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"88\",\n" +
                "                            \"child_category_code\": \"F22\",\n" +
                "                            \"child_category_name\": \"Substance abuse treatment\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"113\",\n" +
                "                    \"sub_category_code\": \"F30\",\n" +
                "                    \"sub_category_name\": \"Mental health treatment\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"89\",\n" +
                "                            \"child_category_code\": \"F31\",\n" +
                "                            \"child_category_name\": \"Psychiatric hospitals\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"90\",\n" +
                "                            \"child_category_code\": \"F32\",\n" +
                "                            \"child_category_name\": \"Community mental health centers\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"91\",\n" +
                "                            \"child_category_code\": \"F33\",\n" +
                "                            \"child_category_name\": \"Residential mental health treatment\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"114\",\n" +
                "                    \"sub_category_code\": \"F40\",\n" +
                "                    \"sub_category_name\": \"Hot lines & crisis intervention\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"92\",\n" +
                "                            \"child_category_code\": \"F42\",\n" +
                "                            \"child_category_name\": \"Sexual assault services\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"115\",\n" +
                "                    \"sub_category_code\": \"F50\",\n" +
                "                    \"sub_category_name\": \"Addictive disorders nec\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"93\",\n" +
                "                            \"child_category_code\": \"F52\",\n" +
                "                            \"child_category_name\": \"Smoking addiction\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"94\",\n" +
                "                            \"child_category_code\": \"F53\",\n" +
                "                            \"child_category_name\": \"Eating disorders & addictions\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"95\",\n" +
                "                            \"child_category_code\": \"F54\",\n" +
                "                            \"child_category_name\": \"Gambling addiction\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"116\",\n" +
                "                    \"sub_category_code\": \"F60\",\n" +
                "                    \"sub_category_name\": \"Counseling\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"117\",\n" +
                "                    \"sub_category_code\": \"F70\",\n" +
                "                    \"sub_category_name\": \"Mental health disorders\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"118\",\n" +
                "                    \"sub_category_code\": \"F80\",\n" +
                "                    \"sub_category_name\": \"Mental health associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"119\",\n" +
                "                    \"sub_category_code\": \"F99\",\n" +
                "                    \"sub_category_name\": \"Mental health & crisis intervention nec\",\n" +
                "                    \"child_category\": []\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"category_id\": \"9\",\n" +
                "            \"category_code\": \"G\",\n" +
                "            \"category_name\": \"Diseases, disorders & medical disciplines\",\n" +
                "            \"subcategory\": [\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"120\",\n" +
                "                    \"sub_category_code\": \"G01\",\n" +
                "                    \"sub_category_name\": \"Alliances & advocacy\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"121\",\n" +
                "                    \"sub_category_code\": \"G02\",\n" +
                "                    \"sub_category_name\": \"Management & technical assistance\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"122\",\n" +
                "                    \"sub_category_code\": \"G03\",\n" +
                "                    \"sub_category_name\": \"Professional societies & associations\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"123\",\n" +
                "                    \"sub_category_code\": \"G05\",\n" +
                "                    \"sub_category_name\": \"Research institutes & public policy analysis\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"124\",\n" +
                "                    \"sub_category_code\": \"G11\",\n" +
                "                    \"sub_category_name\": \"Single organization support\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"125\",\n" +
                "                    \"sub_category_code\": \"G12\",\n" +
                "                    \"sub_category_name\": \"Fund raising & fund distribution\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"126\",\n" +
                "                    \"sub_category_code\": \"G19\",\n" +
                "                    \"sub_category_name\": \"Support nec\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"127\",\n" +
                "                    \"sub_category_code\": \"G20\",\n" +
                "                    \"sub_category_name\": \"Birth defects & genetic diseases\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"96\",\n" +
                "                            \"child_category_code\": \"G25\",\n" +
                "                            \"child_category_name\": \"Down syndrome\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"128\",\n" +
                "                    \"sub_category_code\": \"G30\",\n" +
                "                    \"sub_category_name\": \"Cancer\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"97\",\n" +
                "                            \"child_category_code\": \"G32\",\n" +
                "                            \"child_category_name\": \"Breast cancer\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"129\",\n" +
                "                    \"sub_category_code\": \"G40\",\n" +
                "                    \"sub_category_name\": \"Diseases of specific organs\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"98\",\n" +
                "                            \"child_category_code\": \"G41\",\n" +
                "                            \"child_category_name\": \"Eye diseases, blindness & vision impairments\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"99\",\n" +
                "                            \"child_category_code\": \"G42\",\n" +
                "                            \"child_category_name\": \"Ear & throat diseases\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"100\",\n" +
                "                            \"child_category_code\": \"G43\",\n" +
                "                            \"child_category_name\": \"Heart & circulatory system diseases & disorders\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"101\",\n" +
                "                            \"child_category_code\": \"G44\",\n" +
                "                            \"child_category_name\": \"Kidney diseases\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"102\",\n" +
                "                            \"child_category_code\": \"G45\",\n" +
                "                            \"child_category_name\": \"Lung diseases\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"103\",\n" +
                "                            \"child_category_code\": \"G48\",\n" +
                "                            \"child_category_name\": \"Brain disorders\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"130\",\n" +
                "                    \"sub_category_code\": \"G50\",\n" +
                "                    \"sub_category_name\": \"Nerve, muscle & bone diseases\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"104\",\n" +
                "                            \"child_category_code\": \"G51\",\n" +
                "                            \"child_category_name\": \"Arthritis\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"105\",\n" +
                "                            \"child_category_code\": \"G54\",\n" +
                "                            \"child_category_name\": \"Epilepsy\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"131\",\n" +
                "                    \"sub_category_code\": \"G60\",\n" +
                "                    \"sub_category_name\": \"Allergy-related diseases\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"106\",\n" +
                "                            \"child_category_code\": \"G61\",\n" +
                "                            \"child_category_name\": \"Asthma\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"132\",\n" +
                "                    \"sub_category_code\": \"G70\",\n" +
                "                    \"sub_category_name\": \"Digestive diseases & disorders\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"133\",\n" +
                "                    \"sub_category_code\": \"G80\",\n" +
                "                    \"sub_category_name\": \"Specifically named diseases\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"107\",\n" +
                "                            \"child_category_code\": \"G81\",\n" +
                "                            \"child_category_name\": \"Aids\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"108\",\n" +
                "                            \"child_category_code\": \"G83\",\n" +
                "                            \"child_category_name\": \"Alzheimer’s disease\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"109\",\n" +
                "                            \"child_category_code\": \"G84\",\n" +
                "                            \"child_category_name\": \"Autism\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"134\",\n" +
                "                    \"sub_category_code\": \"G90\",\n" +
                "                    \"sub_category_name\": \"Medical disciplines\",\n" +
                "                    \"child_category\": [\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"110\",\n" +
                "                            \"child_category_code\": \"G92\",\n" +
                "                            \"child_category_name\": \"Biomedicine & bioengineering\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"111\",\n" +
                "                            \"child_category_code\": \"G94\",\n" +
                "                            \"child_category_name\": \"Geriatrics\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"112\",\n" +
                "                            \"child_category_code\": \"G96\",\n" +
                "                            \"child_category_name\": \"Neurology & neuroscience\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child_category_id\": \"113\",\n" +
                "                            \"child_category_code\": \"G98\",\n" +
                "                            \"child_category_name\": \"Pediatrics\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"135\",\n" +
                "                    \"sub_category_code\": \"G9b\",\n" +
                "                    \"sub_category_name\": \"Surgical specialties\",\n" +
                "                    \"child_category\": []\n" +
                "                },\n" +
                "                {\n" +
                "                    \"sub_category_id\": \"136\",\n" +
                "                    \"sub_category_code\": \"G99\",\n" +
                "                    \"sub_category_name\": \"Diseases, disorders & medical disciplines nec\",\n" +
                "                    \"child_category\": []\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";


        try {

            JSONObject jsonObject = new JSONObject(reponse);
            Log.e(TAG, " jsonObject - " + jsonObject);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            String data = jsonObject.getString("data");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = new JSONArray(data);
                Log.e("1232", "" + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Category_new category_new = new Category_new();
                    category_new.setCategory_id(object.getString("category_id"));
                    category_new.setCategory_code(object.getString("category_code"));
                    category_new.setCategory_name(object.getString("category_name"));
                    String subcategory = object.getString("subcategory");
                    Log.e("subcategory", "" + subcategory);
                    JSONArray subcatearray = new JSONArray(subcategory);
                    for (int j = 0; j < subcatearray.length(); j++) {
                        JSONObject subObject = subcatearray.getJSONObject(j);
                        subcategorynew subcategorynew = new subcategorynew();
                        subcategorynew.setSub_category_id(subObject.getString("sub_category_id"));
                        subcategorynew.setSub_category_code(subObject.getString("sub_category_code"));
                        subcategorynew.setSub_category_name(subObject.getString("sub_category_name"));
                        String childcategory = subObject.getString("child_category");
                        Log.e("childcategory", "" + childcategory);
                        JSONArray childcatarrray = new JSONArray(childcategory);

                        for (int k = 0; k < childcatarrray.length(); k++) {
                            JSONObject childObject = childcatarrray.getJSONObject(k);
                            child_categorynew child_categorynew = new child_categorynew();
                            child_categorynew.setChild_category_id(childObject.getString("child_category_id"));
                            child_categorynew.setChild_category_code(childObject.getString("child_category_code"));
                            child_categorynew.setChild_category_name(childObject.getString("child_category_name"));
                            Log.e("suss", "" + childObject.getString("child_category_name"));
                            child_category_newArrayList.add(child_categorynew);
                            Log.e("suss", "" + child_category_newArrayList);
                        }
                        subcategorynew.setChild_category_news(child_category_newArrayList);
                        sub_category_newArrayList.add(subcategorynew);

                    }

                  //  category_new.setSubcategory(sub_category_newArrayList);
                    Log.e("test12", "test");
                    category_newArrayList.add(category_new);
                    Log.e("category_newArrayList",""+category_newArrayList);
                    // Log.e("sub_newArrayList",""+sub_category_newArrayList.get(j).getSub_category_name());


                }
                //datade();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   /* private void datade() {
        Log.e("Exception", "12");
        for (Category_new category_new : category_newArrayList) {
            Log.e("Exception", "12");
            ArrayList<HashMap<String, String>> childArrayList = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String, String>> parentArrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> mapParent = new HashMap<String, String>();
            HashMap<String, String> mapGrand = new HashMap<String, String>();
            mapGrand.put(ConstantManager.Parameter.CATEGORY_ID, category_new.getCategory_id());
            mapGrand.put(ConstantManager.Parameter.CATEGORY_NAME, category_new.getCategory_name());
            Log.e("GrandmapGrand", "" + mapGrand);
            for (subcategorynew subcategorynew : category_new.getSubcategory()) {


                mapParent.put(ConstantManager.Parameter.SUB_CATEGORY_NAME, subcategorynew.getSub_category_name());
                mapParent.put(ConstantManager.Parameter.CATEGORY_ID, subcategorynew.getSub_category_id());

                for (child_categorynew child_categorynew : subcategorynew.getChild_category_news()) {

                    HashMap<String, String> mapChild = new HashMap<String, String>();
                    mapChild.put(ConstantManager.Parameter.CHILD_CATEGORY_NAME, child_categorynew.getChild_category_name());
                    mapChild.put(ConstantManager.Parameter.CATEGORY_ID, child_categorynew.getChild_category_id());
                    childArrayList.add(mapChild);
                }
                parentArrayList.add(mapParent);
                Log.e("par",""+parentArrayList);

            }
            childItems.add(childArrayList);
            parentItems1.add(mapParent);
            GrandItems.add(mapGrand);

        }
        ConstantManager.grandItems = GrandItems;
//        ConstantManager.parentItems1 = parentItems1;
        ConstantManager.childItems = childItems;
        Log.e("Grand", "" + GrandItems);
        Log.e("Parent", "" + parentItems1);
        Log.e("Child", "" + childItems);
        *//*advanceSearchnew = new ThreeLevelListAdapter(AdavanceSearchUIDesign.this, parentItems1, childItems, GrandItems);
        expandable_listview.setAdapter(advanceSearchnew);*//*
    }*/
}

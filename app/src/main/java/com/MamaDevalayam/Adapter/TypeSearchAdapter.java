package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Activity.LoginActivity;
import com.MamaDevalayam.Activity.TypesActivity;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;
import com.google.gson.JsonArray;
import com.MamaDevalayam.Activity.AdvanceCompletedNewActivity;
import com.MamaDevalayam.Model.Category_new;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.Model.Typenamelist;
import com.MamaDevalayam.R;

import java.util.ArrayList;


/**
 * Created by Gowrishankar on 14/05/19.
 */
public class TypeSearchAdapter extends RecyclerView.Adapter<TypeSearchAdapter.MyViewHolder> {
    public Activity mContext;
    ArrayList<String> listOfdate = new ArrayList<String>();
    ArrayList<String> categorylist = new ArrayList<String>();
    private int lastPosition = -1;
    IDonateSharedPreference iDonateSharedPreference;
    ArrayList<Typenamelist> typenamelists;
    private ArrayList<Category_new> category_newArrayList;
    boolean selected;
    SessionManager session;
    int index = 1;

    public TypeSearchAdapter(ArrayList<Typenamelist> typenamelists) {
        this.mContext = mContext;
        this.typenamelists = typenamelists;
        Log.e("listOfdate", "" + typenamelists);
    }

    public TypeSearchAdapter(TypesActivity context, ArrayList<Category_new> category_newArrayList) {
        this.mContext = context;
        this.category_newArrayList = category_newArrayList;
        iDonateSharedPreference = new IDonateSharedPreference();
        session = new SessionManager(mContext);
      /*  if(CategorylistAdapter.categoty_item.size()>0){
            listOfdate = CategorylistAdapter.categoty_item;
            TypesActivity.selecteddata(listOfdate);
        } else if(iDonateSharedPreference.getselectedtypedata(mContext).size() > 0) {
            TypesActivity.selecteddata(iDonateSharedPreference.getselectedtypedata(mContext));
        }*/
        Log.e("newArrayList", "" + category_newArrayList.size());

    }

   /* public TypeSearchAdapter(TypesActivity context, ArrayList<Typenamelist> typenamelists) {
        this.mContext=context;
        this.typenamelists=typenamelists;
        Log.e("newArrayList",""+typenamelists.size());

    }*/


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.type_data_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


       /* holder.type_select_tv1.setText(typenamelists.get(position).getTypename());
        holder.type_select_tvc1.setText(typenamelists.get(position).getTypename());*/
        holder.type_select_tv1.setText((CharSequence) category_newArrayList.get(position).getCategory_name());
        holder.type_select_tvc1.setText((CharSequence) category_newArrayList.get(position).getCategory_name());

       /* if(CategorylistAdapter.categoty_item.size()>0){
            listOfdate = CategorylistAdapter.categoty_item;
            TypesActivity.selecteddata(listOfdate);
        }*/
      /* if(CategorylistAdapter.categoty_item.size() > 0) {
           selected = CategorylistAdapter.categoty_item.contains(category_newArrayList.get(position).getCategory_code());
       } else if(iDonateSharedPreference.getselectedtypedata(mContext).size() > 0){
           selected = (iDonateSharedPreference.getselectedtypedata(mContext)).contains(category_newArrayList.get(position).getCategory_code());
       }

        if(selected){
            holder.linear2.setVisibility(View.VISIBLE);
            holder.linear1.setVisibility(View.GONE);
        } else {
            holder.linear2.setVisibility(View.GONE);
            holder.linear1.setVisibility(View.VISIBLE);
        }*/
      if (category_newArrayList.get(position).isSelected()){
          //listOfdate.add(category_newArrayList.get(position).getCategory_code());
          holder.linear2.setVisibility(View.VISIBLE);
          holder.sublinearLayout.setVisibility(View.VISIBLE);
          holder.linear1.setVisibility(View.GONE);
          Log.e("listOfdate13", "" + listOfdate);
          holder.cbMainCategory2.setChecked(true);
          holder.cbMainCategory1.setChecked(false);
          //  category_Array.add(category_newArrayList.get(position).getCategory_name());
          //    Log.e("linkedList",""+category_Array);
          TypesActivity.selecteddata(listOfdate);
      }else {
          holder.sublinearLayout.setVisibility(View.GONE);
          //listOfdate.add(category_newArrayList.get(position).getCategory_code());
          holder.linear2.setVisibility(View.GONE);
          holder.linear1.setVisibility(View.VISIBLE);
          Log.e("listOfdate", "" + listOfdate);
          TypesActivity.selecteddata(listOfdate);
      }
        final JsonArray category_Array = new JsonArray();
        holder.cbMainCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // model.setSelected(!model.isSelected());
//                   listOfdate.add(typenamelists.get(position).getTypename());
                if (holder.cbMainCategory1.isChecked()){
                    category_newArrayList.get(position).setSelected(true);
                    listOfdate.add(category_newArrayList.get(position).getCategory_code());
                    holder.linear2.setVisibility(View.VISIBLE);
                    holder.sublinearLayout.setVisibility(View.VISIBLE);
                    holder.linear1.setVisibility(View.GONE);
                    Log.e("listOfdate", "" + listOfdate);
                    holder.cbMainCategory2.setChecked(true);
                    holder.cbMainCategory1.setChecked(false);
                  //  category_Array.add(category_newArrayList.get(position).getCategory_name());
                    //    Log.e("linkedList",""+category_Array);
                    TypesActivity.selecteddata(listOfdate);
                }else {
                    if ( holder.cbMainCategory2.isChecked()){
                        holder.sublinearLayout.setVisibility(View.VISIBLE);
                        holder.cbMainCategory1.setChecked(false);
                        holder.cbMainCategory2.setChecked(true);
                        listOfdate.add(category_newArrayList.get(position).getCategory_code());
                        holder.linear2.setVisibility(View.GONE);
                        holder.linear1.setVisibility(View.VISIBLE);
                        Log.e("listOfdate", "" + listOfdate);
                        // category_Array.remove(category_newArrayList.get(position).getCategory_name());
                        //    Log.e("linkedList",""+category_Array);
                        TypesActivity.selecteddata(listOfdate);
                    }else {
                        holder.sublinearLayout.setVisibility(View.GONE);
                        holder.cbMainCategory1.setChecked(false);
                        holder.cbMainCategory2.setChecked(true);
                        listOfdate.remove(category_newArrayList.get(position).getCategory_code());
                        holder.linear2.setVisibility(View.GONE);
                        holder.linear1.setVisibility(View.VISIBLE);
                        Log.e("listOfdate", "" + listOfdate);
                        // category_Array.remove(category_newArrayList.get(position).getCategory_name());
                        //    Log.e("linkedList",""+category_Array);
                        TypesActivity.selecteddata(listOfdate);
                    }

                }

            }
        });
        holder.sublinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo login
                if (session.isLoggedIn()) {
                    ChangeActivity.changeActivity(mContext, AdvanceCompletedNewActivity.class);
                    // finish();
                } else {

                    ChangeActivity.changeActivity(mContext, LoginActivity.class);
                    //  finish();
                }
            }
        });
        holder.cbMainCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 listOfdate.remove(typenamelists.get(position).getTypename());

                if (holder.cbMainCategory2.isChecked()){
                    category_newArrayList.get(position).setSelected(false);
                    holder.sublinearLayout.setVisibility(View.VISIBLE);
                    listOfdate.remove(category_newArrayList.get(position).getCategory_code());
                    holder.linear2.setVisibility(View.GONE);
                    holder.linear1.setVisibility(View.VISIBLE);
                    Log.e("listOfdate", "" + listOfdate);
                    TypesActivity.selecteddata(listOfdate);
                }else {
                    if (!holder.cbMainCategory1.isChecked()){
                        category_newArrayList.get(position).setSelected(false);
                        holder.sublinearLayout.setVisibility(View.GONE);
                        holder.cbMainCategory1.setChecked(false);
                        listOfdate.remove(category_newArrayList.get(position).getCategory_code());
                        holder.linear2.setVisibility(View.GONE);
                        holder.linear1.setVisibility(View.VISIBLE);
                        Log.e("listOfdate", "" + listOfdate);
                        TypesActivity.selecteddata(listOfdate);
                    }else {
                        holder.sublinearLayout.setVisibility(View.GONE);
                        holder.cbMainCategory1.setChecked(false);
                        listOfdate.remove(category_newArrayList.get(position).getCategory_code());
                        holder.linear2.setVisibility(View.GONE);
                        holder.linear1.setVisibility(View.VISIBLE);
                        Log.e("listOfdate", "" + listOfdate);
                        TypesActivity.selecteddata(listOfdate);
                    }

                }

            }
        });

       /* holder.type_select_tvc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("type1", ""+typenamelists.get(position).getTypename());

                if (typenamelists.get(position).isSelected()) {
                    TypesActivity.showbottombottm();
                    holder.type_select_tvc1.setVisibility(View.VISIBLE);
                    holder.type_select_tv1.setVisibility(View.GONE);
                    typenamelists.get(position).setSelected(false);


                }else {
                    holder.type_select_tvc1.setVisibility(View.GONE);
                    holder.type_select_tv1.setVisibility(View.VISIBLE);
                    typenamelists.get(position).setSelected(true);
                }

            }type_select_tvc1
        });*/


    }


    @Override
    public int getItemCount() {
//        return typenamelists.size();
        return category_newArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type_select_tv1, type_select_tvc1;
        public LinearLayout linear2, linear1,sublinearLayout;
        public CheckBox cbMainCategory1, cbMainCategory2;

        public MyViewHolder(View view) {
            super(view);
            type_select_tv1 = (TextView) view.findViewById(R.id.type_select_tv1);
            type_select_tvc1 = (TextView) view.findViewById(R.id.type_select_tvc1);
            linear1 = (LinearLayout) view.findViewById(R.id.linear1);
            linear2 = (LinearLayout) view.findViewById(R.id.linear2);
            sublinearLayout = (LinearLayout) view.findViewById(R.id.sublinearLayout);
            cbMainCategory1 = (CheckBox) view.findViewById(R.id.cbMainCategory1);
            cbMainCategory2 = (CheckBox) view.findViewById(R.id.cbMainCategory2);
        }
    }
}
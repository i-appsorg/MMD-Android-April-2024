package com.MamaDevalayam.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.R;

import java.util.List;
import java.util.Map;


/**
 * Created by Gowrishankar on 14/05/19.
 */
public class MyspaceAdapterList extends RecyclerView.Adapter<MyspaceAdapterList.MyViewHolder> {

    private Context mContext;
    List<Map<String, String>> listOfdate;
    private int lastPosition = -1;

    public MyspaceAdapterList(List<Map<String, String>> listOfdate) {
        this.mContext = mContext;
        this.listOfdate=listOfdate;
        Log.e("listOfdate",""+listOfdate);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myspace_adapter_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.title_name_tv.setText(listOfdate.get(position).get("date"));




    }




    @Override
    public int getItemCount() {
        return listOfdate.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title_name_tv;

        public MyViewHolder(View view) {
            super(view);
            title_name_tv = (TextView) view.findViewById(R.id.title_name_tv);
        }
    }
}
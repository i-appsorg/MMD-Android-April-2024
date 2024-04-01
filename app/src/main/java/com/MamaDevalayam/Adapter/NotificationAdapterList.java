package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Activity.NotificationActivity;
import com.MamaDevalayam.Model.Notification;
import com.MamaDevalayam.R;

import java.util.ArrayList;


/**
 * Created by Gowrishankar on 14/05/19.
 */
public class NotificationAdapterList extends RecyclerView.Adapter<NotificationAdapterList.MyViewHolder> {

    private Activity mContext;
    ArrayList<Notification> notificationlist;



    public NotificationAdapterList(NotificationActivity notificationActivity, ArrayList<Notification> notificationlist) {
        this.mContext = notificationActivity;
        this.notificationlist = notificationlist;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_adapter_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
         holder.notification_tv.setText(notificationlist.get(position).getMessage()+" "+notificationlist.get(position).getDate());


    }


    @Override
    public int getItemCount() {
        return notificationlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView notification_tv;

        public MyViewHolder(View view) {
            super(view);
            notification_tv = (TextView) view.findViewById(R.id.notification_tv);
        }
    }
}
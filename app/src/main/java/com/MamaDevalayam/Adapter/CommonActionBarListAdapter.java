package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MamaDevalayam.Session.SessionManager;
import com.MamaDevalayam.R;

/**
 * Created by GowriShankar on 8/05/19.
 */
public class CommonActionBarListAdapter extends BaseAdapter {
    private Context context;
    private int lastPosition = -1;
    SessionManager sessionManager;
    private String[] idonate_listName = {
            "My Notifications",
            "My Settings",
            "About Mama Devalayam",
            "Help/Support",
            "Logout",};

    private int[] idonate_listImage = {
            R.drawable.notification_bell,
            R.drawable.settings,
            R.drawable.about,
            R.drawable.help_support,
            R.drawable.logout,
    };
    private LayoutInflater inflater;

    public CommonActionBarListAdapter(Activity context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sessionManager = new SessionManager(context);
    }

    @Override
    public int getCount() {
        return idonate_listName.length;
    }

    @Override
    public Object getItem(int position) {
        return idonate_listName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.common_navigation_item, parent, false);
        }
        TextView nameTextView = (TextView) convertView.findViewById(R.id.commonNavigationItemTextView);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.commonNavigationItemImageView);

        if (sessionManager.isLoggedIn()) {
            Log.e("login", "login");
            nameTextView.setText(idonate_listName[position]);
            if (position == 4) {
                Log.e("login", "login");
                nameTextView.setText("Logout");
            }


        } else {
            Log.e("login", "login");
            nameTextView.setText(idonate_listName[position]);
            if (position == 4) {
                Log.e("login", "login");
                nameTextView.setText("Login");
            }
        }


        imageView.setImageResource(idonate_listImage[position]);
        Animation slide_down = AnimationUtils.loadAnimation(context,
                R.anim.slide_down);


// Start animation
        convertView.startAnimation(slide_down);
        return convertView;
    }

}

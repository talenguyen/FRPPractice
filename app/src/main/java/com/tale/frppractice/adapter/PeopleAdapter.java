package com.tale.frppractice.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tale.frppractice.R;
import com.tale.frppractice.data.pojo.People;

/**
 * Created by TALE on 10/9/2014.
 */
public class PeopleAdapter extends CustomAdapter<People> {

    final Picasso picasso;
    final Application application;
    private final LayoutInflater layoutInflater;

    public PeopleAdapter(Application application, Picasso picasso) {
        this.picasso = picasso;
        this.application = application;
        layoutInflater = LayoutInflater.from(application);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_people, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final People people = getItem(position);
        picasso.load(people.avatar_url).into(viewHolder.ivAvatar);
        viewHolder.tvUserName.setText(people.login);
        return convertView;
    }

    static class ViewHolder {
        final ImageView ivAvatar;
        final TextView tvUserName;

        ViewHolder(View view) {
            this.ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
            this.tvUserName = (TextView) view.findViewById(R.id.tvName);
            view.setTag(this);
        }
    }
}

package com.borketey.bk.recipe;

import android.support.v7.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SearchAdapter extends ArrayAdapter<Recipe> {
    public SearchAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView descriptin = (TextView) convertView.findViewById(R.id.nameTextView);

        Recipe recipe = getItem(position);
        {
            title.setText(recipe.getTitle().toString());
            descriptin.setText(recipe.getTitle().toString());


            return convertView;
        }
    }
}

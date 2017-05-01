package com.example.android.newsart;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rmhuneineh on 20/03/2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(Activity context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, View counterView, ViewGroup parent){
        if(counterView == null){
            counterView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Article currentArticle = getItem(position);

        ViewHolder holder = new ViewHolder();

        holder.sectionTextView = (TextView) counterView.findViewById(R.id.section);
        holder.sectionTextView.setText(currentArticle.getSection());

        holder.titleTextView = (TextView) counterView.findViewById((R.id.title));
        holder.titleTextView.setText(currentArticle.getTitle());

        return counterView;
    }

    static class ViewHolder{
        TextView sectionTextView;
        TextView titleTextView;
    }
}

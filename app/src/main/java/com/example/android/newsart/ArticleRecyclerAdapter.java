package com.example.android.newsart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rmhuneineh on 08/05/2017.
 */

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder> {

    List<Article> mArticles;
    MainActivity mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView section;
        protected TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            section = (TextView) itemView.findViewById(R.id.section);

        }
    }

    public ArticleRecyclerAdapter(MainActivity context, List<Article> articles) {
        this.mArticles = articles;
        this.mContext = context;
    }

    @Override
    public ArticleRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        ViewHolder vh = new ViewHolder(listItem);
        return vh;
    }

    @Override
    public void onBindViewHolder(ArticleRecyclerAdapter.ViewHolder holder, int position) {
        final Article currentArticle = mArticles.get(position);

        holder.section.setText(currentArticle.getSection());
        holder.title.setText(currentArticle.getTitle());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startWebView(currentArticle.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}

package com.yusufbek.newsfeedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsHolder> {
    private List<NewsItem> mNewsItems;
    private Context mContext;

    NewsRecyclerAdapter(Context context, List<NewsItem> newsItems) {
        mNewsItems = newsItems;
        mContext = context;
    }

    void addAll(List<NewsItem> newsItems) {
        mNewsItems = newsItems;
    }

    void clear() {
        mNewsItems.clear();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_main_recycler_item, parent, false);

        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.bind(mNewsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        private TextView publishedDateTextView;
        private TextView sectionTextView;
        private TextView titleTextView;

        NewsHolder(@NonNull View itemView) {
            super(itemView);

            publishedDateTextView = itemView.findViewById(R.id.recycler_item_date);
            sectionTextView = itemView.findViewById(R.id.recycler_item_section);
            titleTextView = itemView.findViewById(R.id.recycler_item_title);
        }

        void bind(NewsItem newsItem) {
            publishedDateTextView.setText(newsItem.getPublishedDate());
            sectionTextView.setText(newsItem.getSection());
            titleTextView.setText(newsItem.getTitle());
        }
    }
}
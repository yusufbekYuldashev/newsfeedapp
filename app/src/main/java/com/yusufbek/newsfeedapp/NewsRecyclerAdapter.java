package com.yusufbek.newsfeedapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsHolder> {
    private List<NewsItem> mNewsItems;
    private Context mContext;
    private ClickCallback mCallback;

    interface ClickCallback {
        void onCLick(String itemUrl);
    }

    NewsRecyclerAdapter(Context context, List<NewsItem> newsItems, ClickCallback callback) {
        mNewsItems = newsItems;
        mContext = context;
        mCallback = callback;
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

    class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);
            publishedDateTextView.setText(formatDate(newsItem.getPublishedDate()));
            sectionTextView.setText(newsItem.getSection());
            titleTextView.setText(String.format("\t\t%s", newsItem.getTitle()));
        }

        private String formatDate(String dateString) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date;
            try {
                date = dateFormat.parse(dateString);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd, yyyy h:mm a", Locale.getDefault());
                return simpleDateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onClick(View v) {
            mCallback.onCLick(mNewsItems.get(getAdapterPosition()).getUrl());
        }
    }
}
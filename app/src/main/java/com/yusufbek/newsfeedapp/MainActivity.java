package com.yusufbek.newsfeedapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search?";
    private static final int NEWS_LOADER_ID = 1;

    private NewsRecyclerAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private RecyclerView newsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView = findViewById(R.id.activity_main_recycler_view);
        mAdapter = new NewsRecyclerAdapter(this, new ArrayList<NewsItem>());
        newsRecyclerView.setAdapter(mAdapter);

        mEmptyStateTextView = findViewById(R.id.no_data_found_text_view);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = LoaderManager.getInstance(this);

            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @NonNull
    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("from-date", "2020-01-01");
        uriBuilder.appendQueryParameter("api-key", "e9ba95fd-05ac-436c-a151-0ea936ed3a0f");
        Log.d("TAGGED", uriBuilder.toString() + " is url");
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsItem>> loader, List<NewsItem> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
            mAdapter.notifyDataSetChanged();
        } else {
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_news_data_available);
            newsRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsItem>> loader) {
        mAdapter.clear();
    }
}
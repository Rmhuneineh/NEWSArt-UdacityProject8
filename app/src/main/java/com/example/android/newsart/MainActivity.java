package com.example.android.newsart;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {


    private static final String link = "http://content.guardianapis.com/search";

    private static final String apiKey = "f0315fe2-f964-49fe-9424-3c85c2936a9e";


    private TextView myEmptyState;

    private RecyclerView mRecyclerView;
    private ArticleRecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myEmptyState = (TextView) findViewById(R.id.error_text_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean  isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            myEmptyState.setVisibility(View.GONE);

            mRecyclerAdapter = new ArticleRecyclerAdapter(MainActivity.this, new ArrayList<Article>());
            mRecyclerView.setAdapter(mRecyclerAdapter);
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, MainActivity.this);

        } else {
            ProgressBar loadingSpinner = (ProgressBar) findViewById(R.id.progress_bar);
            loadingSpinner.setVisibility(GONE);

            myEmptyState.setText("No internet connection.");
            myEmptyState.setVisibility(View.VISIBLE);
        }
    }

    public static class ArticleLoader extends AsyncTaskLoader<List<Article>> {

        /** Tag for log messages */
        private final String LOG_TAG = ArticleLoader.class.getName();

        /** Query URL */
        private String mUrl;

        /**
         * Constructs a new {@link ArticleLoader}.
         *
         * @param context of the activity
         * @param url to load data from
         */
        public ArticleLoader(Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        /**
         * This is on a background thread.
         */
        @Override
        public List<Article> loadInBackground() {
            if (mUrl == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of articles.
            List<Article> articles = QueryUtils.fetchNewsData(mUrl);
            return articles;
        }
    }


    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String section = sharedPrefs.getString(getString(R.string.section), getString(R.string.section));

        String listSize = sharedPrefs.getString(getString(R.string.listSize), getString(R.string.listSizeDefault));

        Uri baseUri = Uri.parse(link);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", section);
        uriBuilder.appendQueryParameter("page-size", listSize);
        uriBuilder.appendQueryParameter("api-key", apiKey);

        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        ProgressBar loadingSpinner = (ProgressBar) findViewById(R.id.progress_bar);
        loadingSpinner.setVisibility(GONE);


        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerAdapter = new ArticleRecyclerAdapter(MainActivity.this, new ArrayList<Article>());

        if(articles != null && !articles.isEmpty()){

            mRecyclerAdapter = new ArticleRecyclerAdapter(MainActivity.this, articles);
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        
        mRecyclerAdapter = new ArticleRecyclerAdapter(MainActivity.this, new ArrayList<Article>());
    }
}

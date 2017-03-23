package ru.bda.listimages;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mImageUrls = new ArrayList<>();
    private RecyclerView mRecView;
    private RecyclerAdapter mAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContent(App.isPhone(this) ? 2 : 3);
        new GetImagesUrlTask().execute();
    }

    private void initContent(int count) {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);
        mRecView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new StaggeredGridLayoutManager(count, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new RecyclerAdapter(this, mImageUrls, count);
        mRecView.setAdapter(mAdapter);
        mRecView.setLayoutManager(mLayoutManager);
    }

    private class GetImagesUrlTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mImageUrls = App.getController().getListImagesUrl();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
            if (mImageUrls != null && mImageUrls.size() > 0) {
                mAdapter.setImagesList(mImageUrls);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}

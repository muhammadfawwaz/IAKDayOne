package com.example.muhammadafifaf.iakdayone.main;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.muhammadafifaf.iakdayone.R;
import com.example.muhammadafifaf.iakdayone.data.ApiClient;
import com.example.muhammadafifaf.iakdayone.data.dataDao.MovieResponseDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.muhammadafifaf.iakdayone.data.offlineDB.MovieContract.MovieEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private List<MainDao> mData = new ArrayList<>();
    MainAdapter mainAdapter;
    SwipeRefreshLayout mRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0,null,this);

        mainAdapter = new MainAdapter(mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //ini kalo vertical atau horizontal
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2); //make this view look grid

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        mRecyclerView.setAdapter(mainAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeMain);
        mRefreshLayout.setOnRefreshListener(this);

        getDataMovie();
        //new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mData.add(new MainDao("Pertama","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUntkwAmIg70Df85vzF8YBqXeLVXV_JNwKzPVi8UOgM69t9W-HMA"));
//                mData.add(new MainDao("Kedua","https://i.ytimg.com/vi/7br9-lur6AU/maxresdefault.jpg"));
//                mData.add(new MainDao("Ketiga","https://kompas.web.id/wp-content/uploads/2017/09/gambar-pemandangan-bagus.jpg"));
//                mData.add(new MainDao("Keempat","https://i1.wp.com/www.mediamaya.net/wp-content/uploads/2017/08/Pemandangan-Alam.jpg?resize=800%2C500"));
//                mData.add(new MainDao("Pertama","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUntkwAmIg70Df85vzF8YBqXeLVXV_JNwKzPVi8UOgM69t9W-HMA"));
//                mData.add(new MainDao("Kedua","https://i.ytimg.com/vi/7br9-lur6AU/maxresdefault.jpg"));
//                mData.add(new MainDao("Ketiga","https://kompas.web.id/wp-content/uploads/2017/09/gambar-pemandangan-bagus.jpg"));
//                mData.add(new MainDao("Keempat","https://i1.wp.com/www.mediamaya.net/wp-content/uploads/2017/08/Pemandangan-Alam.jpg?resize=800%2C500"));
//
//                mainAdapter.notifyDataSetChanged();
//            }
//        }, 5000);
//        Toast.makeText(this,"Loading",Toast.LENGTH_LONG).show();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       return new AsyncTaskLoader<Cursor>(this) {
            Cursor mMovieData = null;

            @Override
            public Cursor loadInBackground() {
                try {
                    return this.getContext().getContentResolver().query(MovieEntry.CONTENT_URI,null,null,null,MovieEntry._ID);
                }
                catch (Exception e) {
                    Log.e(MainActivity.class.getName(),"Failed Async load data\n" + e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }

            @Override
            protected void onStartLoading() {
                if(mMovieData != null) {
                    deliverResult(mMovieData);
                }
                else {
                    forceLoad();
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("onLoadFinished: ",String.valueOf(data.getCount()));
        mData.clear();

        for (int i = 0; i < data.getCount(); i++) {
            data.moveToPosition(i);

            mData.add(new MainDao(
                    data.getString(data.getColumnIndex(MovieEntry.COLUMN_TITLE)),
                    "https://image.tmdb.org/t/p/w185/" + data.getString(data.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH)),
                    data.getString(data.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)),
                    "https://image.tmdb.org/t/p/w185/" + data.getString(data.getColumnIndex(MovieEntry.COLUMN_BACKDROP_PATH)),
                    data.getString(data.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE))));
        }

        mainAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void getDataMovie() {
        mRefreshLayout.setRefreshing(true);

        ApiClient.service().getMovieList("3c254f23300c6f359b016526b01092d0").enqueue(new Callback<MovieResponseDao>() {
            @Override
            public void onResponse(Call<MovieResponseDao> call, Response<MovieResponseDao> response) {
                if (response.isSuccessful()) {

                    mRefreshLayout.setRefreshing(false);
                    Uri deleteUri = MovieEntry.CONTENT_URI;
                    getContentResolver().delete(deleteUri,null,null);
                    for (MovieResponseDao.MovieData movieData : response.body().getResults()) {
                        //mData.add(new MainDao(movieData.getTitle(),"https://image.tmdb.org/t/p/w185" + movieData.getPoster_path()));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MovieEntry.COLUMN_FAVORITE_IDS,movieData.getId());
                        contentValues.put(MovieEntry.COLUMN_TITLE,movieData.getTitle());
                        contentValues.put(MovieEntry.COLUMN_ORI_TITLE,movieData.getOriginal_title());
                        contentValues.put(MovieEntry.COLUMN_VOTE_COUNT,movieData.getVote_count());
                        contentValues.put(MovieEntry.COLUMN_VIDEO,movieData.isVideo() ? 1 : 0);
                        contentValues.put(MovieEntry.COLUMN_VOTE_AVG,movieData.getVote_average());
                        contentValues.put(MovieEntry.COLUMN_POPULARITY,movieData.getPopularity());
                        contentValues.put(MovieEntry.COLUMN_POSTER_PATH,movieData.getPoster_path());
                        contentValues.put(MovieEntry.COLUMN_ORIGINAL_LANG,movieData.getOriginal_language());
                        contentValues.put(MovieEntry.COLUMN_GENRE,"");
                        contentValues.put(MovieEntry.COLUMN_BACKDROP_PATH,movieData.getBackdrop_path());
                        contentValues.put(MovieEntry.COLUMN_ADULT,movieData.isAdult() ? 1 : 0);
                        contentValues.put(MovieEntry.COLUMN_OVERVIEW,movieData.getOverview());
                        contentValues.put(MovieEntry.COLUMN_RELEASE_DATE,movieData.getRelease_date());

                        Uri uri = getContentResolver().insert(MovieEntry.CONTENT_URI,contentValues);

                        if(uri != null) {
                            Log.d("onResponse : ","INSERT DATA SUCCESS!");
                        }
                    }
                    getSupportLoaderManager().restartLoader(0,null,MainActivity.this);
                    //mainAdapter.notifyDataSetChanged();
                }
                else {
                    mRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this,response.message(),Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<MovieResponseDao> call, Throwable t) {
                mRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }


        });
    }

    @Override
    public void onRefresh() {
        getDataMovie();
    }
}

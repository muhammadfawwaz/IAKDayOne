package com.example.muhammadafifaf.iakdayone.main;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.muhammadafifaf.iakdayone.R;
import com.example.muhammadafifaf.iakdayone.data.ApiClient;
import com.example.muhammadafifaf.iakdayone.data.dataDao.MovieResponseDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<MainDao> mData = new ArrayList<>();
    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainAdapter = new MainAdapter(mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //ini kalo vertical atau horizontal
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2); //make this view look grid

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        mRecyclerView.setAdapter(mainAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        ApiClient.service().getMovieList("3c254f23300c6f359b016526b01092d0").enqueue(new Callback<MovieResponseDao>() {
            @Override
            public void onResponse(Call<MovieResponseDao> call, Response<MovieResponseDao> response) {
                if (response.isSuccessful()) {
                    for (MovieResponseDao.MovieData movieData : response.body().getResults()) {
                        mData.add(new MainDao(movieData.getTitle(),"https://image.tmdb.org/t/p/w185" + movieData.getPoster_path()));
                    }

                    mainAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResponseDao> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }


        });

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


}

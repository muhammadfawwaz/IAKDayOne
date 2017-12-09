package com.example.muhammadafifaf.iakdayone.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muhammadafifaf.iakdayone.R;
import com.example.muhammadafifaf.iakdayone.main.MainDao;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private MainDao mData;
    private TextView textTitle, textReleaseDate, textDescription;
    private ImageView imagePoster, imageBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mData = getIntent().getParcelableExtra("dataMovie");

        textDescription = (TextView) findViewById(R.id.deskripsi);
        textReleaseDate = (TextView) findViewById(R.id.releaseDate);
        textTitle = (TextView) findViewById(R.id.titlePoster);
        imageBackground = (ImageView) findViewById(R.id.imageToolbar);
        imagePoster = (ImageView) findViewById(R.id.imagePoster);

        textDescription.setText(mData.getDescription());
        textReleaseDate.setText("Release Date: " + mData.getReleaseDate());
        textTitle.setText(mData.getTitle());

        Picasso.with(this).load(mData.getImageUrl()).into(imagePoster);
        Picasso.with(this).load(mData.getImageBackground()).into(imageBackground);
    }
}
